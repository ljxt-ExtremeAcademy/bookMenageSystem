package com.xuetang9.kenny.bookmanage.service.impl;

import com.xuetang9.kenny.bookmanage.dao.BookDAO;
import com.xuetang9.kenny.bookmanage.dao.DAOFactory;
import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.entity.BookSearchCondition;
import com.xuetang9.kenny.bookmanage.service.BookService;

import java.util.List;

/**
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookServiceImpl implements BookService {
    BookDAO bookDAO = null;
    public BookServiceImpl(){
        bookDAO = (BookDAO)DAOFactory.GetDAO("bookdao");
    }

    @Override
    public List<BookInfo> findAll() {
        return bookDAO.search();
    }

    /**
     * 根据条件进行模糊搜索
     *
     * @param condition
     * @return
     */
    @Override
    public List<BookInfo> findByCondition(BookSearchCondition condition) {
        return bookDAO.search(condition);
    }
}
