package aspects;

import loggers.LOG;
import org.aspectj.apache.bcel.generic.ObjectType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by arpi on 09.11.2016.
 */

@Aspect
public class LoggingAspect {
/*
    @Pointcut("execution(* *.logEvent(..))")
    public void allLogEventMethods(){}

    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    public void logEventInsideFileLoggers () {}

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint){
        LOG.info("BEFORE : " +
        joinPoint.getTarget().getClass().getSimpleName() +
        " " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "logEventInsideFileLoggers()", returning = "retVal")
    public void afterReturning(Object retVal){
        LOG.info("Returned value: " + retVal);
    }

    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "ex")
    public void afterThrow(Throwable ex){
        LOG.warn("Thrown: " + ex);
    }*/
}
