package aspects;

import beans.Event;
import com.sun.org.apache.bcel.internal.generic.IFEQ;
import loggers.FileEventLogger;
import loggers.IEventLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by arpi on 09.11.2016.
 */
@Aspect
public class StatisticsAspect {
    private static Map<Class, Integer> statistic = new HashMap<Class, Integer>();
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

    @AfterReturning("allLogEventMethods()")
    public void beforeConsoleLogStat(JoinPoint joinPoint) {
        IEventLogger logger = (IEventLogger) joinPoint.getTarget();
        addEvent(logger);
    }

    private static void addEvent(IEventLogger logger) {
        Integer count = statistic.get(logger.getClass());
        if (count == null) {
            count = 0;
        }
        statistic.put(logger.getClass(), ++count);
    }

    @Around("consoleLoggersMethods() && args(event)")
    public void aroundLogEvent(ProceedingJoinPoint joinPoint, Object event) throws Throwable {
        Integer MAX_CONS_EVENTS = 15;
        IEventLogger target = (IEventLogger) joinPoint.getTarget();
        Integer counter = statistic.get(target.getClass());
        if ((counter!=null) && (counter < MAX_CONS_EVENTS)) {
            joinPoint.proceed(new Object[]{event});
        } else {
            otherLogger.logEvent((Event) event);
        }
    }

    public static void print() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(statistic.toString());
    }

}
