package com.xuetang9.kenny.bookmanage.view;

import com.xuetang9.kenny.bookmanage.view.components.BookCateManagePane;
import com.xuetang9.kenny.bookmanage.view.components.BookManagePane;
import javafx.scene.layout.StackPane;

import java.util.Objects;

/**
 * 为了方便操作，为主舞台界面创建一个面板工厂类
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class MainStagePaneFactory {
    private static BookManagePane bookManagePane = null;
    private static BookCateManagePane bookCateManagePane = null;
    /** 其他面板大家自行补充 */

    /**
     * 根据传入的文本返回对应的面板对象
     * @param text
     * @return
     */
    public static StackPane CreatePaneByText(String text){
        switch (text){
            case "图书管理":
                if(bookManagePane == null){
                    bookManagePane = new BookManagePane();
                }
                return bookManagePane;
            case "分类管理":
                if(bookCateManagePane == null){
                    bookCateManagePane = new BookCateManagePane();
                }
                return bookCateManagePane;
        }
        return null;
    }

}
