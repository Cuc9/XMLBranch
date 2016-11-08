package loggers;

import beans.Event;

/**
 * Created by arpi on 30.10.2016.
 */
public class ConsoleEventLogger implements IEventLogger {
    public ConsoleEventLogger() {    }
    public void logEvent(Event event){
        System.out.println(event);
    }
}
