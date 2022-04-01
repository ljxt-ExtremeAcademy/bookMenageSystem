package com.xuetang9.kenny.bookmanage.dao;

import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.entity.BookSearchCondition;

import java.util.List;

/**
 * t_bookinfo表的数据访问对象
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public interface BookDAO extends DAO<BookInfo> {

    /**
     * 根据传入的查询条件对象进行模糊查询，返回对应的图书对象集合
     * @param condition
     * @return
     */
    public List<BookInfo> search(BookSearchCondition condition);
}
