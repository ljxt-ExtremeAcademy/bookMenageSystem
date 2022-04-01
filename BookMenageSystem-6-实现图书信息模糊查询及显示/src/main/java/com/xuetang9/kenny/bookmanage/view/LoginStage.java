package com.xuetang9.kenny.bookmanage.view;

import com.xuetang9.kenny.bookmanage.entity.UserInfo;
import com.xuetang9.kenny.bookmanage.service.LoginService;
import com.xuetang9.kenny.bookmanage.service.impl.LoginServiceImpl;
import com.xuetang9.kenny.bookmanage.util.CaptchaUtil;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

/**
 * 登录界面
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class LoginStage extends Stage {
    private LoginService loginService = null;

    /** 当前界面中的场景对象 */
    private Scene scene = null;
    private TextField txtAccount = new TextField();
    private PasswordField txtPassword = new PasswordField();
    private TextField txtVCode = new TextField();
    private ImageView imgVCode = new ImageView("images/code.png");
    private CheckBox chkRemember = new CheckBox("记住密码");
    private Hyperlink txtForget = new Hyperlink("忘记密码");
    private Button btnLogin = new Button("登录");
    //三个社交按钮自行实现 - 本系统暂不支持
    private String capcha = null;       //当前界面生成的验证码字符串

    public LoginStage(){
        //测试数据
        txtAccount.setText("admin");
        txtPassword.setText("admin");

        //这里为了简单起见，不再定义业务工厂方法
        loginService = new LoginServiceImpl();
        setTitle("后台登录界面"); //标题同样可以来自配置文件
        scene = new Scene(new StackPane(), 1280, 800);
        //加载本场景下使用样式文件
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/LoginStage.css").toExternalForm());
        setScene(scene);
        iniComponents();        //封装初始化组件的代码
        initEvents();           //封装事件代码
    }

    private void iniComponents() {
        //获得当前场景中的跟节点
        StackPane root = (StackPane)scene.getRoot();
        //设置跟面板的背景图片
        root.getStyleClass().add("root-background1");
        //设置中间登录面板
        VBox loginPane = new VBox();
        setLoginPane(loginPane);        //调用自定义方法单独设置登录面板上的控件
        //为了实现居中效果，需要一个水平面板和一个垂直面板的配合
        HBox hBox = new HBox();     hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();     vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(loginPane);
        hBox.getChildren().add(vBox);
        root.getChildren().add(hBox);
    }

    private void setLoginPane(VBox loginPane) {
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setPrefSize(632, 544);
        loginPane.getStyleClass().add("root-background2");
        loginPane.setPadding(new Insets(30, 140, 30, 140));
        loginPane.setSpacing(15);   //设置垂直间距

        Text txtFirstTitle = new Text("系统管理员登录");
        txtFirstTitle.getStyleClass().add("firstTitle");
        Label txtSecondTitle = new Label("图书管理系统后台");
        txtSecondTitle.getStyleClass().add("secondTitle");
        VBox.setMargin(txtSecondTitle, new Insets(-20, 0, 0, 0));

        //设置验证码小面板
        BorderPane codePane = new BorderPane();
        codePane.setLeft(txtVCode);
        codePane.setRight(imgVCode);
        imgVCode.getStyleClass().add("hand");
        //生成验证码
        Map<String, Object> capMap = CaptchaUtil.generateCaptcha(120, 35);
        //获得当前生成的验证码 字符串
        this.capcha = capMap.get(CaptchaUtil.CAPTCHA).toString();
        //将生成的验证码图片设置到图片框中
        imgVCode.setImage((Image)capMap.get(CaptchaUtil.IMAGE));


        //设置记住密码和忘记密码的小面板
        BorderPane rfPane = new BorderPane();
        rfPane.setLeft(chkRemember);
        rfPane.setRight(txtForget);

        //登录按钮
        btnLogin.setPrefSize(355,35);
        btnLogin.getStyleClass().addAll("btnLogin", "hand");

        txtAccount.setPromptText("登录账号");
        txtPassword.setPromptText("登录密码");
        txtVCode.setPromptText("验证码");

        txtAccount.setPrefHeight(30);
        txtPassword.setPrefHeight(30);
        txtVCode.setPrefHeight(30);
        imgVCode.setFitHeight(30);

        loginPane.getChildren().addAll(txtFirstTitle, txtSecondTitle, txtAccount, txtPassword, codePane, rfPane, btnLogin);
    }

    private void initEvents() {
        btnLogin.setOnAction(e->{
            //首先验证  验证码 -> 正式发布时再启用
//            if(!this.capcha.equalsIgnoreCase(txtVCode.getText())){
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setContentText("验证输入错误，请重试！");
//                alert.show();
//                txtVCode.requestFocus();    //让文本框获得焦点
//                txtVCode.selectAll();       //文字全选
//                return;
//            }


            //省略对用户名和密码格式的验证
            String loginCode = txtAccount.getText();
            String password  = txtPassword.getText();
            //调用业务逻辑层代码，执行登录业务
            //根据登录业务的返回值判断登录是否成功
            //如果登录成功，关闭本窗体，打开主窗体
            UserInfo loginUser = loginService.login(loginCode, password);
            if(Objects.isNull(loginUser)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("用户名或密码输入错误，请重试！");
                alert.show();
                return;
            }
            new MainStage(loginUser).show();
            LoginStage.this.close();

        });

        //为验证码图片添加事件
        imgVCode.setOnMouseClicked(e->{
            //生成验证码
            Map<String, Object> capMap = CaptchaUtil.generateCaptcha(120, 35);
            //获得当前生成的验证码 字符串
            this.capcha = capMap.get(CaptchaUtil.CAPTCHA).toString();
            //将生成的验证码图片设置到图片框中
            imgVCode.setImage((Image)capMap.get(CaptchaUtil.IMAGE));
        });
    }
}
