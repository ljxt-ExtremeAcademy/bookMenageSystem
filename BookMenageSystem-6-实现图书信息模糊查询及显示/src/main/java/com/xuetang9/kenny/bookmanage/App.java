package com.xuetang9.kenny.bookmanage;

import com.xuetang9.kenny.bookmanage.view.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX应用程序的入口
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage = new LoginStage();
        primaryStage.show();
    }
}
