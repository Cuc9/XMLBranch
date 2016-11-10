package aspects;

import beans.Event;
import com.sun.org.apache.bcel.internal.generic.IFEQ;
import loggers.FileEventLogger;
import loggers.IEventLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by arpi on 09.11.2016.
 */
@Aspect
public class StatisticsAspect {
    private Map<IEventLogger, Integer> statistic = new HashMap<IEventLogger, Integer>();
    private IEventLogger otherLogger;

    public StatisticsAspect(IEventLogger otherLogger) {
        this.otherLogger = otherLogger;
    }

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {
    }

    @Pointcut("execution(* loggers.ConsoleEventLogger.logEvent(..))")
    private void consoleLoggersMethods() {
    }

    @AfterReturning(pointcut = "allLogEventMethods()")
    public void afterReturn(JoinPoint joinPoint) {
        IEventLogger logger = (IEventLogger) joinPoint.getTarget();
        this.addEvent(logger);
    }

    private void addEvent(IEventLogger logger) {
        Integer count = this.statistic.get(logger);
        if (count == null) {
            count = 0;
        }
        this.statistic.put(logger, ++count);
    }

    @Around("consoleLoggersMethods() && args(event)")
    public void aroundLogEvent(ProceedingJoinPoint joinPoint, Object event) throws Throwable {
        Integer MAX_CONS_EVENTS = 5;
        IEventLogger target = (IEventLogger) joinPoint.getTarget();
        Integer counter = statistic.get(target);
        if (counter < MAX_CONS_EVENTS) {
            joinPoint.proceed(new Object[]{event});
        } else {
            otherLogger.logEvent((Event) event);
        }
    }

    @PreDestroy
    public void print() {
        System.out.println(this.statistic.toString());
    }

}
