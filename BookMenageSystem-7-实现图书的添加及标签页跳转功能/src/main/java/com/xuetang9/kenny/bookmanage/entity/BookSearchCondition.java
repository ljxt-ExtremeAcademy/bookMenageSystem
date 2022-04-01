package com.xuetang9.kenny.bookmanage.entity;

import java.time.LocalDate;

/**
 * 图书搜索条件类 - 将常用的模糊查询条件也封装成对象，方便操作
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookSearchCondition {
    private String isbn;
    private String bookName;
    private String cateCode;
    private String keyWords;
    private LocalDate fromDate;
    private LocalDate toDate;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
