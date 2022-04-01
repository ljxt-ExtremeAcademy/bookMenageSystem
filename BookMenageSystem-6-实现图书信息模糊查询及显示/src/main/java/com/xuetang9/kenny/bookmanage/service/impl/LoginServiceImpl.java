package com.xuetang9.kenny.bookmanage.service.impl;

import com.xuetang9.kenny.bookmanage.dao.DAOFactory;
import com.xuetang9.kenny.bookmanage.dao.UserDAO;
import com.xuetang9.kenny.bookmanage.dao.impl.UserDAOImpl;
import com.xuetang9.kenny.bookmanage.entity.UserInfo;
import com.xuetang9.kenny.bookmanage.service.LoginService;
import com.xuetang9.kenny.bookmanage.util.SysConstants;
import javafx.scene.control.Pagination;

/**
 * 登录业务的实现类
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class LoginServiceImpl implements LoginService {
    //登录业务需要使用到dao对象，来操作数据库
    private UserDAO userDAO = null;

    public LoginServiceImpl(){
        //userDAO = new UserDAOImpl(); //Too Low!!
        //通过配置文件注入对应的子类实现
        userDAO = (UserDAO) DAOFactory.GetDAO(SysConstants.DAO_USER);
    }


    /**
     * 通过数据库验证传入的用户名和密码是否合法
     *
     * @param loginCode
     * @param password
     * @return 如果身份合法，返回对应的用户对象
     */
    @Override
    public UserInfo login(String loginCode, String password) {
        UserInfo userInfo = userDAO.search(loginCode);
        if(null == userInfo) return null;
        if(password.equals(userInfo.getLoginPwd())){
            //日志记录

            return userInfo;
        }
        return null;
    }
}
