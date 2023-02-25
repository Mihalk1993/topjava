package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetweenHalfOpen(T current, T start, T end) {
        if (current instanceof LocalTime) {
            return ((LocalTime) current).compareTo((LocalTime) start) >= 0
                    && ((LocalTime) current).compareTo((LocalTime) end) < 0;
        } else if (current instanceof LocalDateTime) {
            return ((LocalDateTime) current).compareTo((LocalDateTime) start) >= 0
                    && ((LocalDateTime) current).compareTo((LocalDateTime) end) < 0;
        } else {
            return false;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

