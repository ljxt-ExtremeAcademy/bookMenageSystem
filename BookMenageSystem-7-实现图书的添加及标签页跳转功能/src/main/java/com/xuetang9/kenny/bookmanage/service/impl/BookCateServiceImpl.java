package com.xuetang9.kenny.bookmanage.service.impl;

import com.xuetang9.kenny.bookmanage.dao.BookCateDAO;
import com.xuetang9.kenny.bookmanage.dao.DAOFactory;
import com.xuetang9.kenny.bookmanage.dao.impl.BookCateDAOImpl;
import com.xuetang9.kenny.bookmanage.entity.BookCate;
import com.xuetang9.kenny.bookmanage.service.BookCateService;

import java.util.List;

/**
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookCateServiceImpl implements BookCateService {
    private BookCateDAO bookCateDAO = null;

    public BookCateServiceImpl(){
        bookCateDAO = (BookCateDAO) DAOFactory.GetDAO("bookcatedao");
    }

    /**
     * 获取数据库中的所有父分类，以集合的方式返回
     *
     * @return
     */
    @Override
    public List<BookCate> getAllParentCate() {
        return bookCateDAO.getAllParents();
    }
}
