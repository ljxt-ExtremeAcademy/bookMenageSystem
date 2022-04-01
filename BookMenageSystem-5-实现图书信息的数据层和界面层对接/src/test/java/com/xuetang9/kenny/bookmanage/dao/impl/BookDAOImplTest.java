package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BookDAO;
import com.xuetang9.kenny.bookmanage.dao.DAOFactory;
import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookDAOImplTest {

    @Test
    public void search() {
        BookDAO bookDAO = (BookDAO)DAOFactory.GetDAO("bookdao");
        List<BookInfo> bookList = bookDAO.search();
        bookList.forEach(System.out::println);
    }
}