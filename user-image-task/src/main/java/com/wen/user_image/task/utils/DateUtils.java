package com.wen.user_image.task.utils;

import java.util.Calendar;

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

    public static void main(String[] args) {
        System.out.println(getYearTypeByAge(2020));
    }
}
