package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BaseDAO;
import com.xuetang9.kenny.bookmanage.dao.BookCateDAO;
import com.xuetang9.kenny.bookmanage.entity.BookCate;
import com.xuetang9.kenny.bookmanage.util.SysConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * BookDAO的实现类
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookCateDAOImpl extends BaseDAO implements BookCateDAO {
    /**
     * 获取所有的顶级分类（一级分类，parentId=0的分类）
     *
     * @return
     */
    @Override
    public List<BookCate> getAllParents() {
        String sql = "select * from " + SysConstants.TABLE_BOOKCATE + " where parentId=0;";
        return (List<BookCate>)findBySQL(sql, SysConstants.TABLE_BOOKCATE, ArrayList.class);
    }

    /**
     * 根据传入的分类代码返回对应的下级分类列表
     *
     * @param bookCateCode
     * @return
     */
    @Override
    public List<BookCate> getChilds(String bookCateCode) {
        return null;
    }

    /**
     * 将传入的对象保存到数据表中
     *
     * @param bookCate
     */
    @Override
    public void save(BookCate bookCate) {

    }

    /**
     * 根据主键id删除指定的数据
     *
     * @param id
     */
    @Override
    public void delete(int id) {

    }

    /**
     * 修改指定的数据
     *
     * @param bookCate
     */
    @Override
    public void update(BookCate bookCate) {

    }

    /**
     * 根据主键id返回对应的对象数据
     *
     * @param id
     * @return
     */
    @Override
    public BookCate search(int id) {
        return null;
    }

    /**
     * 返回数据表中的所有数据 - 如果数据量比较多，就默认返回1000条数据
     *
     * @return
     */
    @Override
    public List<BookCate> search() {
        return null;
    }
}
