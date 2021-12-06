package com.yunna.cc.reweeed.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class WeekUtil {
    @SuppressLint("SimpleDateFormat")
    public static String getWeek(String JsonDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(JsonDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(calendar.DATE, 1);

            Calendar calendarTom = new GregorianCalendar();
            calendarTom.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(JsonDate));

            if (JsonDate.equals((new SimpleDateFormat("yyyy-MM-dd").format(new Date()))))
                return "今天";

            if ((new SimpleDateFormat("yyyy-MM-dd").format(calendarTom.getTime())).equals(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())))
                return "明天";

            return getWeekOfDate(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);

        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

//    public static void main(String[] args) {
//       System.out.println(getWeek());
//    }
}
