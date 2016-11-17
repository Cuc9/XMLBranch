package loggers;

/**
 * Created by arpi on 09.11.2016.
 * console output of "info" or "warning" messages
 */
public class LOG {

    public static void info(String msg){
        System.out.println(msg);
    }

    public static void warn(String msg){
        System.out.println("WARNING! " + msg);
    }
}
