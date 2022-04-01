package com.xuetang9.kenny.bookmanage.service;

import com.xuetang9.kenny.bookmanage.entity.BookCate;

import java.util.List;

/**
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public interface BookCateService {
    /**
     * 获取数据库中的所有父分类，以集合的方式返回
     * @return
     */
    public List<BookCate> getAllParentCate();
}
