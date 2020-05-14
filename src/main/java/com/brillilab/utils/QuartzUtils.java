package com.brillilab.utils;

import java.util.Calendar;
import java.util.Date;

public class QuartzUtils {
    private QuartzUtils() {
    }


    /**
     * 获取Cron表达式
     * @param date
     * @return
     */
    public static String getCronExpr(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);

        return new StringBuilder().append(0).append(" ")
                .append(minute).append(" ")
                .append(hour).append(" ")
                .append(day).append(" ")
                .append(month).append(" ")
                .append("?").append(" ")
                .append(year).toString();
    }

    public static String getJobDataMap(Object... datas){
        return null;
    }
}
