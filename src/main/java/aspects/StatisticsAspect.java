package aspects;

import beans.Event;
import loggers.IEventLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arpi on 09.11.2016.
 * Gathers statistic of logEvent method usage
 * statistic - map where key - class on witch logEvent was called
 *                       value - how many times called
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

    /**
     * After each method running updating statistics
     */
    @AfterReturning("allLogEventMethods()")
    public void beforeConsoleLogStat(JoinPoint joinPoint) {
        IEventLogger logger = (IEventLogger) joinPoint.getTarget();
        addEvent(logger);
    }

    /**
     * Increase counter depending on caller Class
     */
    private static void addEvent(IEventLogger logger) {
        Integer count = statistic.get(logger.getClass());
        if (count == null) {
            count = 0;
        }
        statistic.put(logger.getClass(), ++count);
    }

    /**
     * When ConsoleEventLogger.logEvent() runned 15 times replacing ConsoleEventLogger
     * with some otherLogger. See constructor parameter in spring.xml
     */
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

    /**
     * Statistic printing
     */
    public static void print() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(statistic.toString());
    }

}
