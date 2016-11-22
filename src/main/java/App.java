import aspects.StatisticsAspect;
import beans.Client;
import beans.Event;
import beans.EventType;
import loggers.IEventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by arpi on 30.10.2016.
 */
public class App {
    private Client client;
    private IEventLogger defaultEventLogger;
    private Map<EventType, IEventLogger> loggers;
    private static EventType type;

    public App(Client client, IEventLogger defaultEventLogger, Map<EventType, IEventLogger> loggers) {
        this.client = client;
        this.defaultEventLogger = defaultEventLogger;
        this.loggers = loggers;
    }

    public void setType(EventType type) {
        App.type = type;
    }

    /**
     * Depending on Event type choose logger.
     * Replace Client's ID in message with Client's Name
     * Invoke logEvent method on choosen logger
     */
    private void logEvent(String msg, EventType type, Event event) {
        IEventLogger logger = loggers.get(type);
        if (logger == null) {
            logger = defaultEventLogger;
        }
        String message = msg.replaceAll(client.getId(), client.getFullName() + ' ' + client.getGreeting());
        event.setMsg(message);
        logger.logEvent(event);
    }

    /**
     * Creating context using spring.xml
     * External sickle makes INFO Event.
     * Internal sickle 5 times makes DEFAULT Event (use defaultLogger)
     * External sickle makes ERROR Event
     * Printing Statistics
     * Closing context
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("xml\\spring.xml");
        App app = ctx.getBean("app", App.class);
        /*for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            app.logEvent("INFO event for user 1", EventType.INFO, (Event) ctx.getBean("event"));
            for (int j = 0; j < 5; j++) {
                app.logEvent("DEFAULT/CACHE event for user 1", type, (Event) ctx.getBean("event"));
            }
            app.logEvent("ERROR event for user 1", EventType.ERROR, (Event) ctx.getBean("event"));
        }
        StatisticsAspect.print();*/
        app.logEvent("INFO event for user 1", EventType.INFO, (Event) ctx.getBean("event"));
        ctx.close();
    }
}
