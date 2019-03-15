package com.andi.mycourses.util;


import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author andi
 */

public class DateUtil {
    private static final DateTimeFormatter dateFormatter  =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

//  private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final DateTimeFormatter forShow = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static LocalDate getLocalDate(String s)
    {
        int year = Integer.parseInt(s.substring(0,4));
        int month = Integer.parseInt(s.substring(4,6));
        int day = Integer.parseInt(s.substring(6,8));
        return LocalDate.of(year, month, day);
    }

    public static LocalDateTime getLocalDateTime(String s)
    {
//        s = s.replace("Z"," UTC");
        LocalDateTime time = null;
        try {
            time = LocalDateTime.parse(s, forShow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTimeString(LocalDateTime time)
    {
        return forShow.format(time);
    }

    public static String getTimeStringForShow(LocalDateTime time)
    {
        return forShow.format(time);
    }

    public static String getDateString(LocalDate date){
        return dateFormatter.format(date);
    }

}
