package beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by arpi on 31.10.2016.
 * Some event has ID, message and creation date
 * NOT singletone
 */

public class Event {
    private int id = new Random().nextInt(100);
    private String msg;
    private Date date;
    private DateFormat df;

    public Event(Date date, DateFormat df) {
        this.date = date;
        this.df = df;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id = " + id +
                ", msg = '" + msg + '\'' +
                ", date = " + df.format(date) +
                '}';
    }

    @PreDestroy
    private void destroy(){
        System.out.println("Event bean deleted----");
    }

    /**
     * Checking if it was day-time when Event created
     */
    public static boolean isDay(){
        Calendar calDate = Calendar.getInstance();
        int hour = calDate.get(Calendar.HOUR_OF_DAY);
        if ((hour > 7) && (hour < 17)) {
            return true;
        }
        return false;
    }


}
