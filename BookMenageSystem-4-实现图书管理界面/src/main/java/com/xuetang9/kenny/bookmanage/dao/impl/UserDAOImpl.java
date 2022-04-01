package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BaseDAO;
import com.xuetang9.kenny.bookmanage.dao.UserDAO;
import com.xuetang9.kenny.bookmanage.entity.UserInfo;
import com.xuetang9.kenny.bookmanage.util.SysConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * UserDAO的mysql实现版本
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {
    /**
     * 用户表的表名
     */
    private String table_user = SysConstants.TABLE_USER;

    /**
     * @see com.xuetang9.kenny.bookmanage.dao.UserDAO#search(String)
     * @param loginCode
     * @return
     */
    @Override
    public UserInfo search(String loginCode) {
        String sql = String.format("select * from %s where loginCode=?;", table_user);
        List<UserInfo> userList = (List<UserInfo>)findBySQL(sql, table_user, ArrayList.class, loginCode);
        //loginCode需要唯一约束，这里返回集合中的第一个对象即可
        if(Objects.isNull(userList) || userList.size() == 0) return null;
        return userList.get(0);
    }

    @Override
    public void save(UserInfo userInfo) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(UserInfo userInfo) {

    }

    @Override
    public UserInfo search(int id) {
        return null;
    }

    @Override
    public List<UserInfo> search() {
        return null;
    }
}
