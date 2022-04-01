package com.xuetang9.kenny.bookmanage.view.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 图书管理面板，包含图书管理、编辑图书、统计分析和添加图书
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookManagePane extends StackPane {
    private TableView tableView = new TableView();

    public BookManagePane(){
        setPadding(new Insets(10, 20, 10, 20));
        TabPane tabPane = new TabPane();
        //设置标签页不会被关闭
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab tab_bookmanage = new Tab("图书管理", createTabBookManage());

        tabPane.getTabs().addAll(tab_bookmanage);
        getChildren().add(tabPane);
    }

    /**
     * 创建图书管理标签面板 Top - Center(top-center-bottom)
     * @return
     */
    private Node createTabBookManage() {
        ComboBox<String> cmbBookCate = new ComboBox<>();
        //组合框中的数据应该来自数据库 - 调用业务层代码
        cmbBookCate.getItems().addAll("请选择", "小说");
        DatePicker dateFrom = new DatePicker(LocalDate.now());
        DatePicker dateTo   = new DatePicker(LocalDate.now());
        TextField txtKeyWord = new TextField();
        txtKeyWord.setPromptText("请输入关键字...");
        Button btnSearch = new Button("搜索");
        Button btnClear = new Button("清空");
        //图书管理标签面板的根面板
        BorderPane root = new BorderPane();
        //搜索小面板
        HBox searchPane = new HBox(2);
        searchPane.getChildren().addAll(
                new Label("分类："), cmbBookCate,
                new Label("时间："), dateFrom, new Label(" - "), dateTo,
                new Label("关键字："), txtKeyWord,
                btnSearch, btnClear
        );
        //美化搜索面板
        btnSearch.getStyleClass().add("button-normal"); btnSearch.setStyle("-fx-font-size: 14px");
        btnClear.getStyleClass().add("button-special"); btnClear.setStyle("-fx-font-size: 14px");
        VBox searchPane_v = new VBox(); searchPane_v.setAlignment(Pos.CENTER);
        searchPane_v.getChildren().add(searchPane);
        searchPane_v.setStyle("-fx-border-color: #cccccc;-fx-background-color: rgb(235, 240, 241);");
        searchPane_v.setPrefHeight(80);
        searchPane_v.setPadding(new Insets(0, 0, 0, 10));
        HBox.setMargin(cmbBookCate, new Insets(-3, 0, 0, 0));
        HBox.setMargin(dateFrom, new Insets(-3, 0, 0, 0));
        HBox.setMargin(dateTo, new Insets(-3, 0, 0, 0));
        HBox.setMargin(txtKeyWord, new Insets(-3, 0, 0, 0));
        HBox.setMargin(btnSearch, new Insets(-5, 0, 0, 0));
        HBox.setMargin(btnClear, new Insets(-5, 0, 0, 0));
        BorderPane.setMargin(searchPane_v, new Insets(10, 0, 0, 0));

        //tableView面板
        BorderPane tableViewPane = new BorderPane(); //为了后面方便设置不同的分页控件，后面会定义成成员属性
        tableViewPane.setPadding(new Insets(20, 0, 10, 0));
        tableViewPane.setTop(createButtonPane());
        //中部放置一个分页控件/面板
        int pageCount = 10;     //数据来自业务逻辑层
        Node pagination = createPagination(pageCount, 5, 0);
        tableViewPane.setCenter(pagination);
        tableViewPane.setBottom(createButtonPane());
        BorderPane.setMargin(pagination, new Insets(10, 0, 0, 0));

        root.setTop(searchPane_v);
        root.setCenter(tableViewPane);
        return root;
    }



    /** 与tableView控件绑定的图书集合数据 */
    private ObservableList<FXBook> bookList = FXCollections.observableArrayList();

    /**
     * 创建一个分页面板，可以实现数据的分页显示
     * @param pageCount     分页的总页面数
     * @param itemPerPage   每页显示的数据条数
     * @param currPageIndex 当先显示分页的下标
     * @return
     */
    private Node createPagination(int pageCount, int itemPerPage, int currPageIndex) {
        Pagination pagination = new Pagination(pageCount, currPageIndex);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                int pageIndex = param.intValue();           //获得当前显示的分页下标
                tableView.getItems().clear();               //为了方便显示不同的查询结果，所以每次更新分页时，首先清空tableView控件
                //连接数据库，根据分页下标返回对应的集合数据
                //为了实现分页的效果，所以需要subList
                int fromIndex = pageIndex * itemPerPage;    //起始下标
                int toIndex   = (pageIndex + 1) * itemPerPage;
                if(toIndex >= bookList.size()) toIndex = bookList.size();
                List subList = bookList.subList(fromIndex, toIndex);
                tableView.setItems(FXCollections.observableArrayList(subList));
                return tableView;
            }
        });
        return pagination;
    }

    private Node createButtonPane() {
        HBox buttonPane = new HBox(10);
        String[] btnTexts = {"新增", "修改", "导出", "删除"};
        Button[] buttons = new Button[btnTexts.length];
        for(int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(btnTexts[i]);
            if(i != 3){
                buttons[i].getStyleClass().add("button-normal");
            }else{
                buttons[i].getStyleClass().add("button-special");
            }
            buttons[i].setOnAction(e->{
                //可以根据按钮的文本调用相应的事件处理程序
                //大家酌情自己实现
            });
        }
        buttonPane.getChildren().addAll(buttons);
        return buttonPane;
    }

    /**
     * tableView控件专用的图书对象类
     */
    private class FXBook {
        private IntegerProperty bookId;
        private StringProperty bookName;
        //........
    }
}
