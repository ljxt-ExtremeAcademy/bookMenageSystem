package com.xuetang9.kenny.bookmanage.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 为使用java8中的时间类定义的工具方法
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class DateUtil {
    /**
     * 获取java.time.LocalDateTime对象的默认字符串格式形式
     * @param localDateTime
     * @return
     */
    public static String GetLocalDateTimeFromDefaultPattern(LocalDateTime localDateTime){
        //设置字符串形式时间的默认格式 - 字符串格式可以定义在常量类中
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        return localDateTime.format(formatter);
    }

    /**
     * 将传入的长整型时间(1970-1-1到当前为止的毫秒数)，转型成java.time.LocalDateTime类型返回
     * @param time
     * @return
     */
    public static LocalDateTime TimeToLocalDateTime(long time){
        //根据传入的毫秒数，获得毫秒数所表示的一个时刻
        Instant instant = Instant.ofEpochMilli(time);
        //获取系统的默认时区
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return localDateTime;
    }

    /**
     * 将传入的长整型时间(1970-1-1到当前为止的毫秒数)，转型成java.time.LocalDate类型返回
     * @param time
     * @return
     */
    public static LocalDate TimeToLocalDate(long time){
        LocalDateTime localDateTime = TimeToLocalDateTime(time);
        return localDateTime.toLocalDate();
    }

    /**
     * 将传入的java.util.Date类型转换为java.time.LocalDateTime类型返回
     * @param date
     * @return
     */
    public static LocalDateTime DateToLocalDateTime(Date date){
        return TimeToLocalDateTime(date.getTime());
    }

    /**
     * 将传入的java.util.Date类型转换为java.time.LocalDate类型返回
     * @param date
     * @return
     */
    public static LocalDate DateToLocalDate(Date date){
        return DateToLocalDateTime(date).toLocalDate();
    }
}
