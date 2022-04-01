package com.xuetang9.kenny.bookmanage.dao.impl;

import com.xuetang9.kenny.bookmanage.dao.BaseDAO;
import com.xuetang9.kenny.bookmanage.dao.BookDAO;
import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.entity.BookSearchCondition;
import com.xuetang9.kenny.bookmanage.util.SysConstants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookDAOImpl extends BaseDAO implements BookDAO {
    /**
     * 将传入的对象保存到数据表中
     *
     * @param bookInfo
     */
    @Override
    public void save(BookInfo bookInfo) {

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
     * @param bookInfo
     */
    @Override
    public void update(BookInfo bookInfo) {

    }

    /**
     * 根据主键id返回对应的对象数据
     *
     * @param id
     * @return
     */
    @Override
    public BookInfo search(int id) {
        return null;
    }

    /**
     * 返回数据表中的所有数据 - 如果数据量比较多，就默认返回1000条数据
     *
     * @return
     */
    @Override
    public List<BookInfo> search() {
        //如果数据库中的内容比较多，可以在查询时限制返回的条目，条目数可以定义在常量或配置文件中，以适应不同的数据库或系统的性能
        //String sql = "select * from " + SysConstants.TABLE_BOOK + " limit 1000;";
        String sql = "select * from " + SysConstants.TABLE_BOOK + ";";
        List<BookInfo> bookList = (List<BookInfo>)findBySQL(sql, SysConstants.TABLE_BOOK, ArrayList.class);
        return bookList;
    }

    /**
     * 根据传入的查询条件对象进行模糊查询，返回对应的图书对象集合
     *
     * @param condition
     * @return
     */
    @Override
    public List<BookInfo> search(BookSearchCondition condition) {
        if(null == condition) return null;
        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(SysConstants.TABLE_BOOK);
        sql.append(" where ");
        //bookName like '%ddd%' or bookId=2 or regDate between fromDate to toDate
        //以下为装逼代码：使用反射遍历每个条件属性进行sql语句的拼接
        Field[] fields = BookSearchCondition.class.getDeclaredFields();
        try {
            for(int i = 0; i < fields.length; i++){
                //操作某个属性前，需要打开属性的可访问权限
                fields[i].setAccessible(true);
                //System.out.println(fields[i].get(condition));//打印某个属性的取值
                if(null == fields[i].get(condition) || "".equals(fields[i].get(condition))) continue;

                if(fields[i].getType().equals(String.class)){  //如果当前这个属性的类型是字符串类型
                    sql.append(fields[i].getName());
                    sql.append(" like ");
                    sql.append("'%" + fields[i].get(condition) + "%'");
                }else if(fields[i].getName().equals("fromDate")){
                    sql.append(" regDate between '").append(fields[i].get(condition)).append("' and '");
                    continue;
                }else if(fields[i].getName().equals("toDate")){
                    sql.append(fields[i].get(condition)).append("'");
                }else{
                    sql.append(fields[i].getName());
                    sql.append("=");
                    sql.append(fields[i].get(condition));
                }
                if(i != fields.length - 1) sql.append(" or ");
            }
            sql.append(";");
            List<BookInfo> bookList = (List<BookInfo>)findBySQL(sql.toString(), SysConstants.TABLE_BOOK, ArrayList.class);
            System.out.println(sql);
            return bookList;
        } catch (IllegalAccessException e) {
        e.printStackTrace();
    }
        return null;
    }
}
