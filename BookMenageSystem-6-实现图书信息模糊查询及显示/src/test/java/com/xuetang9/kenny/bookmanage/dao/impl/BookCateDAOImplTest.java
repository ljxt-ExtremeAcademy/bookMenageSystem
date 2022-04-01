package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BookCateDAO;
import com.xuetang9.kenny.bookmanage.dao.DAOFactory;
import com.xuetang9.kenny.bookmanage.entity.BookCate;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookCateDAOImplTest {

    @Test
    public void getAllParents() {
        BookCateDAO bookCateDAO = (BookCateDAO) DAOFactory.GetDAO("bookcatedao");
        List<BookCate> cateList = bookCateDAO.getAllParents();
        cateList.forEach(System.out::println);
    }
}