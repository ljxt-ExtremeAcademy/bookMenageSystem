package com.xuetang9.kenny.bookmanage.dao;

import java.util.List;

/**
 * 定义了所有DAO的通用方法
 */
public interface DAO<T> {
    /**
     * 将传入的对象保存到数据表中
     * @param t
     */
    void save(T t);

    /**
     * 根据主键id删除指定的数据
     * @param id
     */
    void delete(int id);

    /**
     * 修改指定的数据
     * @param t
     */
    void update(T t);

    /**
     * 根据主键id返回对应的对象数据
     * @param id
     * @return
     */
    T search(int id);

    /**
     * 返回数据表中的所有数据 - 如果数据量比较多，就默认返回1000条数据
     * @return
     */
    List<T> search();
}
