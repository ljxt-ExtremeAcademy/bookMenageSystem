package com.xuetang9.kenny.bookmanage.dao;

import com.xuetang9.kenny.bookmanage.entity.BookCate;
import java.util.List;

/**
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public interface BookCateDAO extends DAO<BookCate> {
    /**
     * 获取所有的顶级分类（一级分类，parentId=0的分类）
     * @return
     */
    public List<BookCate> getAllParents();

    /**
     * 根据传入的分类代码返回对应的下级分类列表
     * @return
     */
    public List<BookCate> getChilds(String bookCateCode);
}
