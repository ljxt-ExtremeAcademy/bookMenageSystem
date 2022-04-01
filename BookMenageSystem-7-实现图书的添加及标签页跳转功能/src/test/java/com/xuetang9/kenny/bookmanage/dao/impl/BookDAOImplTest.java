package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BookDAO;
import com.xuetang9.kenny.bookmanage.dao.DAOFactory;
import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.entity.BookSearchCondition;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class BookDAOImplTest {
    BookDAO bookDAO = (BookDAO)DAOFactory.GetDAO("bookdao");
    //@Test
    public void search() {

        List<BookInfo> bookList = bookDAO.search();
        bookList.forEach(System.out::println);
    }

    @Test
    public void searchByCondition(){
        BookSearchCondition condition = new BookSearchCondition();
        condition.setIsbn("abc");
        condition.setBookName("测试图书");
        condition.setKeyWords("关键字");
        condition.setCateCode("bookCateCode");
        condition.setFromDate(LocalDate.now().plusYears(40));
        condition.setToDate(LocalDate.now().plusYears(40).plusMonths(5));
        List<BookInfo> bookList = bookDAO.search(condition);
    }
}