import loggers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpi on 05.11.2016.
 */
@Configuration
public class LoggersConfig {
    @Bean
    public ConsoleEventLogger consoleEventLogger(){
        return new ConsoleEventLogger();
    }

    @Bean
    public FileEventLogger fileEventLogger(){
        return new FileEventLogger("");
    }

    @Bean
    public CacheFileEventLogger cacheFileEventLogger(){
        return new CacheFileEventLogger(4,"D:\\JAVA\\LearningSpring\\src\\main\\resources\\CachedLogFile.txt");
    }

    @Bean
    public CombinedEventLogger combinedEventLogger(){
        List<IEventLogger> loggers = new ArrayList<IEventLogger>();
        loggers.add(consoleEventLogger());
        loggers.add(cacheFileEventLogger());
        return new CombinedEventLogger(loggers);
    }
}
