package com.andi.mycourses.util;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;

/**
 * @author andi
 */
public class DateUtil {
    public static LocalDate getLocalDate(String s)
    {
        int year = Integer.parseInt(s.substring(0,4));
        int month = Integer.parseInt(s.substring(4,6));
        int day = Integer.parseInt(s.substring(6,8));
        return LocalDate.of(year, month, day);
    }
}
