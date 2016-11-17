import sun.util.resources.CalendarData;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JUST TEMP CLASS FOR SOME TESTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class Temp {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.getTime());
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.HOUR_OF_DAY));
    }
}
