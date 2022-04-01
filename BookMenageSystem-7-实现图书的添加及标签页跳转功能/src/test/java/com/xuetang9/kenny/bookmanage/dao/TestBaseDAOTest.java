package com.xuetang9.kenny.bookmanage.dao;

import com.xuetang9.kenny.bookmanage.entity.UserInfo;
import com.xuetang9.kenny.bookmanage.util.SysConstants;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestBaseDAOTest {
    @Test
    public void test(){
        BaseDAO dao = new TestBaseDAO();
        //dao.mappingEntity(null, SysConstants.TABLE_USER);
        String sql = "select * from " + SysConstants.TABLE_USER + " where loginCode like ?";
        List<UserInfo> userList = (List<UserInfo>) dao.findBySQL(sql, SysConstants.TABLE_USER, ArrayList.class, "%n%");
        userList.forEach(System.out::println);
    }
}