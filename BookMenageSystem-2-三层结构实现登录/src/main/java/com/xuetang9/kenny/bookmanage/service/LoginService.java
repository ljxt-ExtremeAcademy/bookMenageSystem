package com.xuetang9.kenny.bookmanage.service;

import com.xuetang9.kenny.bookmanage.entity.UserInfo;

/**
 * 登录业务接口
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public interface LoginService {
    /**
     * 通过数据库验证传入的用户名和密码是否合法
     * @param loginCode
     * @param password
     * @return 如果身份合法，返回对应的用户对象
     */
    UserInfo login(String loginCode, String password);
}
