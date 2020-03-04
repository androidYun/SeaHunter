package com.xhs.baselibrary.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author guiyun.li
 * @email xyz_6776.@163.com
 * @date 04/04/2019.
 * description:
 */

public class DateUtils {
    private static DateUtils sInstance;

    public static DateUtils getInstance() {
        if (sInstance == null) {
            sInstance = new DateUtils();
        }
        return sInstance;
    }

    public String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }

    private long getTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(time);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public String getYMD(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }


    public String switchTime(String time) {
        //今天 00:00:00 的时间
        if (time == null || time.isEmpty()) {
               return "";
        }

        long todayTime = getZeroTime();
        Long createTime = getTime(time);
        long intervalTime = todayTime - createTime;
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(createTime));
        int month = instance.get(Calendar.MONTH) + 1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        String hour = instance.get(Calendar.HOUR_OF_DAY) > 9 ? String.valueOf(instance.get(Calendar.HOUR_OF_DAY)) : "0" + String.valueOf(instance.get(Calendar.HOUR_OF_DAY));
        String minute = instance.get(Calendar.MINUTE) > 9 ? String.valueOf(instance.get(Calendar.MINUTE)) : "0" + instance.get(Calendar.MINUTE);
        int week = instance.get(Calendar.DAY_OF_WEEK);
        if (intervalTime < 0) {//今天
            return hour + ":" + minute;
        } else if (intervalTime < getOneDayTime()) {//昨天
            return "昨天";
        } else if (intervalTime > getOneDayTime() && intervalTime <= getOneDayTime() * getCurrentWeek()) {//星期
            switch (week) {
                case 1:
                    return "周日";
                case 2:
                    return "周一";
                case 3:
                    return "周二";
                case 4:
                    return "周三";
                case 5:
                    return "周四";
                case 6:
                    return "周五";
                case 0:
                    return "周六";
            }
        } else if (intervalTime > getOneDayTime() * getCurrentWeek() && intervalTime < getOneDayTime() * getCurrentDayOfYear()) {//这个月
            return month + "月" + day + "日";
        } else {
            return time;
        }
        return "";
    }

    private long getOneDayTime() {
        return 24 * 60 * 60 * 1000;
    }

    private long getZeroTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        try {
            today = format.parse(format.format(new Date()));
            return today.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getCurrentWeek() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.DAY_OF_WEEK) == 0 ? 7 : instance.get(Calendar.DAY_OF_WEEK);
    }

    private int getCurrentDayOfYear() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.DAY_OF_YEAR);
    }
}
