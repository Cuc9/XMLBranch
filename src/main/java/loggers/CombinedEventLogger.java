package loggers;

import beans.Event;

import java.util.Collection;

/**
 * Created by arpi on 02.11.2016.
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
