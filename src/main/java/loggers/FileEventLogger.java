package loggers;

import beans.Event;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * Created by arpi on 01.11.2016.
 */
public class FileEventLogger implements IEventLogger {
    String fileName;
    File logFile;
    private boolean append;

  /*  public FileEventLogger(File logFile) {
        this.logFile = logFile;
        System.out.println("--- Assigned file - " + logFile.getName());
    }*/

  /*  public FileEventLogger() {
    }*/

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(logFile, event.toString() + "\n", "UTF-8", append);
            System.out.println("--- File was appended");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() throws IOException {
        this.logFile = new File(fileName);
        System.out.println("--- Assigned file - " + logFile.getName());
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        this.append = logFile.canWrite();
        System.out.println("We can append - " + append);
        if (!append) {
            throw new IOException("IO in init FileEventLogger method");
        }
    }
}
