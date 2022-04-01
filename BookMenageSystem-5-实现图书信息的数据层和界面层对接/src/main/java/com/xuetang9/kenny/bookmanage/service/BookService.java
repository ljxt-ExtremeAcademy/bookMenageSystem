package com.xuetang9.kenny.bookmanage.service;

import com.xuetang9.kenny.bookmanage.entity.BookInfo;

import java.util.List;

/**
 * 封装了图书管理相关的业务方法
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public interface BookService {
    public List<BookInfo> findAll();
}
