package com.xuetang9.kenny.bookmanage.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * 封装了对配置文件的操作
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class Configurations {
    /** 所管理的配置文件数组 */
    private static Properties[] properties = null;

    static {
        String[] propFiles = {
                "DAOConfig.properties",
                "DataBaseConfig.properties",
                "TableConfig.properties"
        };
        properties = new Properties[propFiles.length];
        for(int i = 0; i < properties.length; i++){
            properties[i] = new Properties();
            try {
                properties[i].load(Thread.currentThread().
                        getContextClassLoader().getResourceAsStream("config/" + propFiles[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载完配置文件后，立即加载数据库连接驱动
        try {
            Class.forName(Configurations.get(SysConstants.DATABASE_DRIVER));
            System.out.println("数据库连接驱动已加载！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的键返回配置文件中对应的值
     * @param key
     * @return
     */
    public static String get(String key){
        if(Objects.isNull(key)) return null;
        key = key.toLowerCase();
        for(Properties prop : properties){
            if(prop.containsKey(key)){
                return prop.getProperty(key);
            }
        }
        return null;
    }
}
