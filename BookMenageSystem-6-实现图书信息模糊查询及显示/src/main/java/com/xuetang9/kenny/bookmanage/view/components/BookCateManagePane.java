package com.xuetang9.kenny.bookmanage.view.components;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * 图书分类面板
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookCateManagePane extends StackPane {
    public BookCateManagePane(){
        getChildren().add(new Button("图书分类管理面板"));
    }
}
