import beans.Client;
import beans.Event;
import beans.EventType;
import loggers.CombinedEventLogger;
import loggers.IEventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by arpi on 30.10.2016.
 */
public class App {
    private Client client;
    private IEventLogger eventLogger;
    private Map<EventType, IEventLogger> loggers;
    private static EventType type;

    public App() {
    }

    public App(Client client, IEventLogger eventLogger, Map<EventType, IEventLogger> loggers) {
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    public void setType(EventType type) {
        App.type = type;
    }

    private void logEvent(String msg, EventType type, Event event) {
        IEventLogger logger = loggers.get(type);
        if (logger == null) {
            logger = eventLogger;
        }
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);
        logger.logEvent(event);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("xml\\spring.xml");
        App app = ctx.getBean("app", App.class);
        for (int i = 0; i < 3; i++) {
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
        ctx.close();
    }
}
