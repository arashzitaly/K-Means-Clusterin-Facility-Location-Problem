package main.java.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataTimeUtil {

    public static String gatDifDate(Date date1, Date date2) {
        long difference = Math.abs(date1.getTime() - date2.getTime());
        Date date = new Date(difference);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(date);
        return String.valueOf(dateFormatted) ;
    }

}
