package loggers;

import beans.Event;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpi on 01.11.2016.
 * SAME work AS in FileEventLogger but keeps Events in cache.
 * After cache is full, prints cached Events to file.
 */

public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(int cacheSize, String fileName){
        super(fileName);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<Event>();
    }

    public void logEvent(Event event) {
        cache.add(event);
        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void destroy (){
        if (!cache.isEmpty()){
            writeEventsFromCache();
            System.out.println("Destroy called with some cache info");
        } else {System.out.println("Destroy called with EMPTY cache");}

    }

    /**
     * Writes events from cache to file using parent Class (FileEventLogger)
     */
    private void writeEventsFromCache (){
        for (Event item : cache) {
            super.logEvent(item);
        }
    }
}
