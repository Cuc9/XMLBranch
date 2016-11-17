package loggers;

import beans.Event;

import java.util.Collection;

/**
 * Created by arpi on 02.11.2016.
 * Makes multiply logging. Obtains collection of LoggerClass-Objects
 * Invokes logEvent method on all loggers in collection for each Event
 * For example using ConsoleEventLogger and CashedEventLogger for logging Events with ERROR type
 */
public class CombinedEventLogger implements IEventLogger {

    private Collection<IEventLogger> loggers;

    public CombinedEventLogger(Collection<IEventLogger> loggers) {
        this.loggers = loggers;
    }

    public void logEvent(Event event) {
        for (IEventLogger item : loggers) {
            item.logEvent(event);
        }
    }
}
