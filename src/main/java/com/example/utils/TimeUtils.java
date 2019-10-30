package com.example.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Author by admin, Email xx@xx.com, Date on 2019/10/13.
 * PS: Not easy to write code, please indicate.
 */
public class TimeUtils {

    public static Date formatNow(String pattern) {
        LocalDateTime time = LocalDateTime.now();
        String fixtime = time.format(DateTimeFormatter.ofPattern(pattern));
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date now = null;
        try {
            now = dateFormat.parse(fixtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }
}
