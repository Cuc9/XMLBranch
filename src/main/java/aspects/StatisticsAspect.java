package aspects;

import loggers.IEventLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;

/**
 * Created by arpi on 09.11.2016.
 */
@Aspect
public class StatisticsAspect {
    private Map<IEventLogger,Integer> statistic;

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods(){}

    @AfterReturning(pointcut = "allLogEventMethods()")
    public void afterReturn(JoinPoint joinPoint){
        IEventLogger logger = (IEventLogger) joinPoint.getTarget();
        this.addEvent(logger);
    }

    public void addEvent(IEventLogger logger){
        Integer count = this.statistic.get(logger);
        this.statistic.put(logger,++count);
    }
}
