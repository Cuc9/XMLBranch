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
    /**
     * On all logEvent methods on Classes
     */
    @Pointcut("execution(* *.logEvent(..))")
    public void allLogEventMethods(){}

    /**
     * logEvent methods on FileEventLogger and CachedFileEventLogger
     */
    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    public void logEventInsideFileLoggers () {}

    /**
     * Before method run outputs to console ClassName and method name
     */
    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint){
        LOG.info("BEFORE : " +
        joinPoint.getTarget().getClass().getSimpleName() +
        " " + joinPoint.getSignature().getName());
    }

    /**
     * After method runned outputs to console returned value
     */
    @AfterReturning(pointcut = "logEventInsideFileLoggers()", returning = "retVal")
    public void afterReturning(Object retVal){
        LOG.info("Returned value: " + retVal);
    }

    /**
     * After method threw exception outputs to console exception
     */
    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "ex")
    public void afterThrow(Throwable ex){
        LOG.warn("Thrown: " + ex);
    }
}
