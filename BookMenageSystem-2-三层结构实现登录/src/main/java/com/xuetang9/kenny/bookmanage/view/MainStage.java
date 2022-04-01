package com.xuetang9.kenny.bookmanage.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 系统主窗体
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class MainStage extends Stage {
    private Scene scene = null;

    public MainStage(){
        setTitle("图书管理系统主窗体 - by 老九学堂·窖头");
        scene = new Scene(new BorderPane(), 1280, 800);
    }
}
