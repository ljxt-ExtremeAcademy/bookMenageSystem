package com.xuetang9.kenny.bookmanage.entity;

/**
 * 图书分类
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookCate {
    private int bookCateId;
    private int parentId;
    private String bookCateName;
    private String bookCateCode;

    @Override
    public String toString() {
        return "BookCate{" +
                "bookCateId=" + bookCateId +
                ", parentId=" + parentId +
                ", bookCateName='" + bookCateName + '\'' +
                ", bookCateCode='" + bookCateCode + '\'' +
                '}';
    }

    public int getBookCateId() {
        return bookCateId;
    }

    public void setBookCateId(int bookCateId) {
        this.bookCateId = bookCateId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getBookCateName() {
        return bookCateName;
    }

    public void setBookCateName(String bookCateName) {
        this.bookCateName = bookCateName;
    }

    public String getBookCateCode() {
        return bookCateCode;
    }

    public void setBookCateCode(String bookCateCode) {
        this.bookCateCode = bookCateCode;
    }
}
