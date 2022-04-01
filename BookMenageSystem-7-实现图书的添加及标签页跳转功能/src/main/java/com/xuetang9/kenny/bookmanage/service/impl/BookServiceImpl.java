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
    public void save(BookInfo bookInfo) {
        //保存逻辑中，可以自行设置一个判断或其他分支业务
        //比如说：图书名查重、如果isbn相同，就不保存，而是只添加库存等
        //再比如说，记录当前操作员的操作记录
        bookDAO.save(bookInfo);
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
