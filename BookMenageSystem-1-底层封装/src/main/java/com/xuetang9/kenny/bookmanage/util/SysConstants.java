package com.xuetang9.kenny.bookmanage.util;

/**
 * 1、系统参数
 * 2、数据库的连接参数
 * 3、数据表与实体类的映射
 * 4、dao实现类的映射
 * 5、sql语句的映射 - 可选
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class SysConstants {
    /** 数据库连接参数 */
    public static final String DATABASE_DRIVER = "db.driver";
    public static final String DATABASE_URL = "db.url";
    public static final String DATABASE_USER_NAME = "db.username";
    public static final String DATABSE_USER_PASSWORD = "db.password";

    /** 数据表与实体类之间的映射 */
    public static final String TABLE_USER = "t_userinfo";
    public static final String TABLE_BOOK = "t_bookinfo";


}
