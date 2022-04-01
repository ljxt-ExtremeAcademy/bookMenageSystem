package com.xuetang9.kenny.bookmanage.dao;

import com.xuetang9.kenny.bookmanage.util.Configurations;

/**
 * 负责从配置文件中获取指定的DAO实现类，返回DAO实现对象
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class DAOFactory {
    /**
     * <p>1、根据指定的daoName获取配置文件中对应的dao实现类</p>
     * <p>2、使用反射实例化并返回dao实现类的对象</p>
     * @param daoName
     * @return
     */
    public static DAO GetDAO(String daoName){
        String implClass = Configurations.get(daoName);
        try {
            Class<?> daoClass = Class.forName(implClass);
            return (DAO)daoClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
