package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BaseDAO;
import com.xuetang9.kenny.bookmanage.dao.BookDAO;
import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.util.SysConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookDAOImpl extends BaseDAO implements BookDAO {
    /**
     * 将传入的对象保存到数据表中
     *
     * @param bookInfo
     */
    @Override
    public void save(BookInfo bookInfo) {

    }

    /**
     * 根据主键id删除指定的数据
     *
     * @param id
     */
    @Override
    public void delete(int id) {

    }

    /**
     * 修改指定的数据
     *
     * @param bookInfo
     */
    @Override
    public void update(BookInfo bookInfo) {

    }

    /**
     * 根据主键id返回对应的对象数据
     *
     * @param id
     * @return
     */
    @Override
    public BookInfo search(int id) {
        return null;
    }

    /**
     * 返回数据表中的所有数据 - 如果数据量比较多，就默认返回1000条数据
     *
     * @return
     */
    @Override
    public List<BookInfo> search() {
        //如果数据库中的内容比较多，可以在查询时限制返回的条目，条目数可以定义在常量或配置文件中，以适应不同的数据库或系统的性能
        //String sql = "select * from " + SysConstants.TABLE_BOOK + " limit 1000;";
        String sql = "select * from " + SysConstants.TABLE_BOOK + ";";
        List<BookInfo> bookList = (List<BookInfo>)findBySQL(sql, SysConstants.TABLE_BOOK, ArrayList.class);
        return bookList;
    }
}
