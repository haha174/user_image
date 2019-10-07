package com.wen.user_image.job.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static int getBornYearByAge(String age){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(calendar.YEAR,-Integer.parseInt(age));
        /**
         * 得到用户的出生年龄
         */
        return calendar.getWeekYear();
    }

    public static String getYearTypeByAge(int  bornYear){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentYear=calendar.getWeekYear();
        calendar.add(calendar.YEAR,-90);
        int yearBaseTypeStartYear=  calendar.getWeekYear();
        System.out.println(currentYear);
        System.out.println(yearBaseTypeStartYear);

        if(bornYear>=yearBaseTypeStartYear&&bornYear<=currentYear){
            return (bornYear%100)/10+"0后";
        }
        return "未知";
    }


    /**
     * 获取两个时间相差的天数
     * @param starttime
     * @param endTime
     * @param dateFormatstring
     * @return
     * @throws ParseException
     */
    public static int getDaysBetweenbyStartAndend(String starttime,String endTime,String dateFormatstring) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatstring);
        Date start = dateFormat.parse(starttime);
        Date end = dateFormat.parse(endTime);
        Calendar startcalendar = Calendar.getInstance();
        Calendar endcalendar = Calendar.getInstance();
        startcalendar.setTime(start);
        endcalendar.setTime(end);
        int days = 0;
        while(startcalendar.before(endcalendar)){
            startcalendar.add(Calendar.DAY_OF_YEAR,1);
            days += 1;
        }
        return days;
    }

    public static void main(String[] args) {
        System.out.println(getYearTypeByAge(2020));
    }
}
