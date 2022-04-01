package com.xuetang9.kenny.bookmanage.view;

import com.xuetang9.kenny.bookmanage.entity.UserInfo;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * 系统主窗体
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class MainStage extends Stage {
    /** 主窗体左侧导航面板的宽度设置为固定值 */
    private static final int NAV_WIDTH = 240;
    /** 当前登录的用户对象 - 需要从登录窗体传递过来 */
    private UserInfo loginUser = null;
    private Scene scene = null;
    /** top-center部分的导航按钮面板 */
    private HBox navButtonPane = new HBox();
    /** 为了方便更改中部的右侧(其实还是中部)面板，所以单独定义成contentPane */
    private StackPane contentPane = new StackPane();

    /** 测试时使用 */
    public MainStage(){
        loginUser = new UserInfo();
        loginUser.setLoginCode("默认生成的用户");
        loginUser.setUserName("默认生成的用户-测试专用");
        init();
        setTitle("图书管理系统主窗体 - by 老九学堂·窖头 - 当前登录用户：" + loginUser.getUserName());
    }

    public MainStage(UserInfo loginUser){
        this.loginUser = loginUser;
        setTitle("图书管理系统主窗体 - by 老九学堂·窖头");
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        scene = new Scene(new BorderPane(), 1280, 800);
        scene.getStylesheets().addAll(
                getClass().getClassLoader().getResource("css/Style.css").toExternalForm(),
                getClass().getClassLoader().getResource("css/MainStage.css").toExternalForm()
        );
        setScene(scene);
        initComponents();
        initEvents();
    }

    private void initComponents() {
        BorderPane root = (BorderPane)scene.getRoot();
        root.setTop(createHeader());        //模拟未来的HTML5来进行的命名
        root.setCenter(createBody());
        root.setBottom(createFooter());
    }

    /**
     * 创建主窗体中的头部面板
     * @return
     */
    private Node createHeader() {
        BorderPane headerPane = new BorderPane();
        headerPane.getStyleClass().add("headPane-background");
        headerPane.setPrefHeight(55);

        //设置中间部分 - 导航按钮面板
        //默认放置一个首页按钮
        Button btnIndex = new Button("首页");
        btnIndex.getStyleClass().addAll("header-nav-button", "header-nav-button-selected");
        navButtonPane.getChildren().add(btnIndex);

        //实现右侧放置登录用户名以及头像
        VBox vBox = new VBox();             vBox.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(10);   hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 20, 0, 0));
        ImageView imgHead = new ImageView("images/admin.png");
        imgHead.setFitWidth(28);    imgHead.setFitHeight(28);
        Label lblUserName = new Label("欢迎您，" + loginUser.getUserName());
        lblUserName.getStyleClass().add("header-nav-username");
        hBox.getChildren().addAll(imgHead, lblUserName);
        //为了实现居中
        vBox.getChildren().addAll(hBox);

        headerPane.setLeft(new ImageView("images/logo.png"));
        headerPane.setCenter(navButtonPane);
        headerPane.setRight(vBox);

        return headerPane;
    }

    private Node createBody() {
        BorderPane bodyPane = new BorderPane();
        bodyPane.setLeft(createNavPane());
        //内容面板会在之后的按钮事件中进行操作
        bodyPane.setCenter(this.contentPane);
        return bodyPane;
    }

    /**
     * 中部左侧的导航面板
     * @return
     */
    private Node createNavPane() {
        VBox navPane = new VBox();
        navPane.setPrefWidth(NAV_WIDTH);    //240
        //5个导航按钮
        FlowPane buttonPane = new FlowPane(5, 0);
        buttonPane.setPadding(new Insets(10, 0, 10, 20));
        Button[] buttons = new Button[5];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button("", new ImageView("images/nav-" + (i+1) + ".png"));
            buttons[i].setPrefSize(35, 22);
            buttons[i].setPadding(new Insets(0));
        }
        buttonPane.getChildren().addAll(buttons);

        //手风琴面板/折叠面板
        Accordion accordion = new Accordion();
        TitledPane bookManagePane = new TitledPane("图书管理", new Pane());
        TitledPane userManagePane = new TitledPane("用户管理", new Pane());
        TitledPane systemManagePane = new TitledPane("系统管理", new Pane());
        accordion.getPanes().addAll(bookManagePane, userManagePane, systemManagePane);
        //创建方法，统一设置按钮面板
        bookManagePane.setContent(createManagePane("图书管理", "分类管理"));
        userManagePane.setContent(createManagePane("读者管理", "角色管理"));
        systemManagePane.setContent(createManagePane("更改密码", "日志管理"));

        navPane.getChildren().addAll(buttonPane, accordion);
        return navPane;
    }

    /**
     * 根据传入的按钮文本数组创建相应的按钮面板
     * @param buttonTexts
     * @return
     */
    private Pane createManagePane(String ... buttonTexts){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0));
        Button[] buttons = new Button[buttonTexts.length];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(buttonTexts[i]);
            buttons[i].setPrefWidth(NAV_WIDTH);
            buttons[i].setOnAction(e->{
                Button button = (Button)e.getSource();
                System.out.println("当前点击的按钮：" + button.getText());
                //调用自定义方法，根据当前单击的按钮切换导航面板上的按钮以及右侧的内容面板
                changeByText(button.getText());
            });
        }
        vBox.getChildren().addAll(buttons);
        return vBox;
    }

    private void changeByText(String text) {
        ObservableList<Node> nodeList = navButtonPane.getChildren();
        boolean isContainButton = false;        //用来记录按钮集合中是否已经包含了某个按钮
        for(Node node : nodeList){
            if(!(node instanceof Button)) continue;
            Button button = (Button)node;
            //其他按钮取消选中状态
            button.getStyleClass().removeAll("header-nav-button-selected");
            if(text.equalsIgnoreCase(button.getText())){
                isContainButton = true;
                //当前按钮就设置为选中样式
                button.getStyleClass().add("header-nav-button-selected");
            }
        }
        if(!isContainButton){//如果导航栏中不包含text按钮，就创建一个新的按钮
            Button button = new Button(text);
            button.getStyleClass().addAll("header-nav-button", "header-nav-button-selected");
            button.setOnAction(e->changeByText(text));
            navButtonPane.getChildren().add(button);
        }
    }

    private Node createFooter() {
        BorderPane footerPane = new BorderPane();
        footerPane.getStyleClass().add("headPane-background");
        footerPane.setPrefHeight(40);
        return footerPane;
    }



    private void initEvents() {
    }

    public UserInfo getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(UserInfo loginUser) {
        this.loginUser = loginUser;
    }
}
