package com.xuetang9.kenny.bookmanage.dao;

import com.xuetang9.kenny.bookmanage.entity.UserInfo;

/**
 * 封装了对 t_userinfo 表的数据访问操作
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public interface UserDAO extends DAO<UserInfo> {
    /**
     * 为了方便实现登录操作，根据指定的loginCode返回用户对象
     * @param loginCode
     * @return
     */
    UserInfo search(String loginCode);
}
