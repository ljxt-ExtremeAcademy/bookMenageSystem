package com.xuetang9.kenny.bookmanage.dao;

import com.xuetang9.kenny.bookmanage.util.Configurations;
import com.xuetang9.kenny.bookmanage.util.SysConstants;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Collection;
import java.util.Objects;

/**
 * 用来完成对数据库通用操作的封装
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public abstract class BaseDAO {
    /** 通过读取配置文件获得相应的取值 */
    /* private final static String DRIVER = Configurations.get(SysConstants.DATABASE_DRIVER); */
    private final static String URL = Configurations.get(SysConstants.DATABASE_URL);
    private final static String USER_NAME = Configurations.get(SysConstants.DATABASE_USER_NAME);
    private final static String PASSWORD = Configurations.get(SysConstants.DATABSE_USER_PASSWORD);

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    /**
     * 连接到数据库
     * @return
     * @throws SQLException
     */
    public Connection initConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        return connection;
    }

    public void initConnectionAndStatement() throws SQLException {
        initConnection();
        statement = connection.createStatement();
    }

    /**
     * 使用Statement对象执行更新语句(包含增删改操作)
     * @param sql
     * @return 影响的行数，执行失败返回0
     */
    public int executeUpdate(String sql){
        try {
            initConnectionAndStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(null, statement, null, connection);
        }
        return 0;
    }

    /**
     * 使用PreparedStatement对象执行更新操作
     * @param preparedSql 执行的sql语句
     * @param params 所需的参数
     * @return 返回更新的行数
     */
    public int executeUpdate(String preparedSql, Object ... params){
        try {
            this.connection = this.initConnection();
            this.preparedStatement = this.connection.prepareStatement(preparedSql);
            if( params != null && params.length != 0) {
                for( int i = 0; i < params.length; i++ ) {
                    this.preparedStatement.setObject(i+1, params[i]);// 为预编译sql设置参数
                }
            }
            return this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            this.close(null, null, this.preparedStatement, this.connection);
        }
        return 0;
    }

    /**
     * 执行事务
     * @param sqlBatch 事务中的sql语句按数组传入
     * @return
     */
    public boolean executeTransaction(String[] sqlBatch){
        try {
            this.connection = this.initConnection();
            //设置事务的提交方式为非自动提交
            connection.setAutoCommit(false);
            for(String sql : sqlBatch){
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
            //提交事务
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally{
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.close(null, null, this.preparedStatement, this.connection);
        }
        return false;
    }


    /**
     * <p>执行指定的查询语句，返回对象集合</p>
     * <p>该方法负责通用的查询表中所有内容。这个方法使用反射技术来实现</p>
     * <p>这样可以简化客户端代码</p>
     * <p>所有表的通用查询可以输入表名，然后通过Facade对象得到一个实体集合</p>
     * @param sql
     * @param tableName 当前sql语句所查询的表名 会根据这个表名获得对应实体类
     * @param collectionType 指定返回集合的类型（子类型）
     * @param params 对应sql语句中的问号参数
     * @return
     */
    public Collection findBySQL(String sql, String tableName, Class collectionType, Object ... params){
        Collection collection = null;
        try {
            //通过反射实例化指定类型的集合对象
            collection = (Collection)collectionType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            connection = initConnection();
            preparedStatement = connection.prepareStatement(sql);
            if(Objects.nonNull(params)){
                for(int i = 0; i < params.length; i++){
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                //填充一个实体对象
                //将实体对象放置到一个集合对象中
                collection.add(mappingEntity(resultSet, tableName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collection;
    }

    /**
     * 根据结果集中的数据，填充指定的实体对象并返回
     * @param resultSet
     * @param tableName
     * @return
     */
    public Object mappingEntity(ResultSet resultSet, String tableName) {
        //根据传入的表名，从配置文件中获得对应的实体类
        String entityClassName = Configurations.get(tableName);
        //System.out.println(entityClassName);
        Object entityObj = null;
        try {
            Class<?> entityClass = Class.forName(entityClassName);
            entityObj = entityClass.newInstance();
            //Field[] fields = entityClass.getDeclaredFields();
            //调用实体对象的set方法
            Method[] methods = entityClass.getMethods();
            for(Method method : methods){
                String methodName = method.getName();       //获取当前遍历的方法名
                if("set".equals(methodName.substring(0, 3))){
                    //要赋值的属性/列名
                    String filedName = methodName.substring(3).toLowerCase();
                    //获得所赋值列的类型
                    String paramType = method.getParameterTypes()[0].getTypeName();
                    //System.out.println(filedName + " - " + paramType);
                    if("int".equals(paramType) || "java.lang.Integer".equals(paramType)){
                        method.invoke(entityObj, resultSet.getInt(filedName));
                    }else if("long".equals(paramType) || "java.lang.Long".equals(paramType)){
                        method.invoke(entityObj, resultSet.getLong(filedName));
                    }else if("short".equals(paramType) || "java.lang.Short".equals(paramType)){
                        method.invoke(entityObj, resultSet.getShort(filedName));
                    }else if("double".equals(paramType) || "java.lang.Double".equals(paramType)){
                        method.invoke(entityObj, resultSet.getDouble(filedName));
                    }else if("float".equals(paramType) || "java.lang.Float".equals(paramType)){
                        method.invoke(entityObj, resultSet.getFloat(filedName));
                    }else if("boolean".equals(paramType) || "java.lang.Boolean".equals(paramType)){
                        method.invoke(entityObj, resultSet.getBoolean(filedName));
                    }else if("String".equals(paramType) || "java.lang.String".equals(paramType)){
                        method.invoke(entityObj, resultSet.getString(filedName));
                    }else if("Date".equals(paramType) || "java.util.Date".equals(paramType)){
                        method.invoke(entityObj,resultSet.getDate(filedName));
                    }else {
                        //其他情况碰到时再添加，如Java8中的LocalDateTime等
                        method.invoke(entityObj, resultSet.getObject(filedName));
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return entityObj;
    }


    /**
     * 统一关闭传入的资源
     * @param resultSet
     * @param statement
     * @param preparedStatement
     * @param connection
     */
    public void close(ResultSet resultSet, Statement statement, PreparedStatement preparedStatement, Connection connection){
        try {
            free(resultSet);
            free(statement);
            free(preparedStatement);
            free(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void free(AutoCloseable autoCloseable) throws Exception {
        if(Objects.nonNull(autoCloseable)){
            autoCloseable.close();
        }
    }



}




















