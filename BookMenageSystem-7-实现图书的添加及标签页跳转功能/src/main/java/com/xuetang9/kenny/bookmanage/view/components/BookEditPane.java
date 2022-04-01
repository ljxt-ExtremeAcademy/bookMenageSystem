package com.xuetang9.kenny.bookmanage.view.components;

import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.entity.BookSearchCondition;
import com.xuetang9.kenny.bookmanage.service.BookService;
import com.xuetang9.kenny.bookmanage.service.impl.BookServiceImpl;
import com.xuetang9.kenny.bookmanage.util.PinYinUtil;
import com.xuetang9.kenny.bookmanage.view.MainStagePaneFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 图书添加/图书修改面板
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookEditPane extends StackPane {
    private TextField txtBookCate   = new TextField();        //图书分类框
    private TextField txtBookName   = new TextField();        //图书名称框
    private TextField txtIsbn       = new TextField();        //isbn框
    private TextField txtKeywords   = new TextField();
    private TextField txtInputCode  = new TextField();        //拼音码框
    private TextField txtPublisher  = new TextField();
    private TextField txtAuthor     = new TextField();        //作者框
    private TextField txtPrice      = new TextField();        //单价框
    private TextField txtStoreCount = new TextField();        //库存框
    private TextField txtCateCode   = new TextField();        //分类号
    private TextArea  txtSummary    = new TextArea();         //摘要框，多行文本框
    private HTMLEditor txtContentIntro = new HTMLEditor();    //可编辑的内容简介框，返回编辑后的HTML字符串
    private ImageView imgCover      = new ImageView();        //封面图片
    private DatePicker dpPublish    = new DatePicker(LocalDate.now().plusYears(30));
    private Button    btnSubmit     = new Button("默认为发布");
    private Button    btnReset      = new Button("重置");
    private Button    btnValidate   = new Button("...");
    private Label     lblExists     = new Label("验证isbn是否已存在");
    /** 为了方便调用，将service业务对象设置为当前对象的成员 */
    private BookService bookService =  new BookServiceImpl();

    /**
     * 适合在添加图书时调用，各控件的Text属性都为默认值
     */
    public BookEditPane(){
        initComponents();
        initEvents();
    }



    /**
     * 根据传入的图书对象信息填充面板上各控件的内容 - 本方法适合在修改/编辑图书信息时调用
     * @param book
     */
    public BookEditPane(BookInfo book){
        initComponents();
        //为各个控件赋值
        setValuesByBook(book);
        initEvents();
    }

    /**
     * 绘制各控件
     */
    private void initComponents() {
        BorderPane root = new BorderPane();
        root.setCenter(createCenter());
        root.setRight(createRight());
        root.setBottom(createBottom());
        //容易落下的一句：
        getChildren().add(root);
    }

    private Node createCenter() {
        GridPane centerPane = new GridPane();
        centerPane.setPrefWidth(800);
        centerPane.setVgap(10);     centerPane.setHgap(10);
        centerPane.addRow(0, new Label("*ISBN："),txtIsbn, btnValidate, lblExists);
        centerPane.addRow(1, new Label("*书名"), txtBookName, new Label("输入码："), txtInputCode);
        centerPane.addRow(2, new Label("著者："), txtAuthor, new Label("定价："), txtPrice, new Label("库存："), txtStoreCount);
        centerPane.addRow(3, new Label("*分类号："), txtBookCate, new Label("出版社："), txtPublisher);
        centerPane.addRow(4, new Label("关键字："), txtKeywords);
        centerPane.addRow(5, new Label("摘要："), txtSummary);
        centerPane.addRow(6, new Label("内容简介："), txtContentIntro);

//        lblExists需要跨3列
        GridPane.setColumnSpan(lblExists, 3);
        GridPane.setColumnSpan(txtInputCode, 3);
        GridPane.setColumnSpan(txtPublisher, 3);
        GridPane.setColumnSpan(txtKeywords, 5);
        GridPane.setColumnSpan(txtSummary, 5);
        GridPane.setColumnSpan(txtContentIntro, 5);

        centerPane.setPadding(new Insets(20, 0, 20, 0));
        //只需要设置某列中的一个控件的宽度，其他行的控件宽度会自动更改
        txtIsbn.setPrefWidth(310);
        txtInputCode.setPrefWidth(310);
        txtPrice.setPrefWidth(140);
        txtStoreCount.setPrefWidth(140);
        return centerPane;
    }


    private Node createRight() {
        VBox rightPane = new VBox();
        rightPane.setPadding(new Insets(20, 0, 20, 0));
        rightPane.setSpacing(10);
        rightPane.getChildren().addAll(new Label("封面图片："), imgCover, new Label("发布时间："), dpPublish);
        //设置一个默认封面图片 - 宽200，高280
        imgCover.setFitWidth(200);      imgCover.setFitHeight(280);
        imgCover.setImage(new Image("images/default.jpg"));
        imgCover.setStyle("-fx-cursor: hand;");
        return rightPane;
    }


    private Node createBottom() {
        FlowPane bottomPane = new FlowPane();
        btnSubmit.setText("添加");
        btnSubmit.getStyleClass().add("button-normal");
        btnReset.getStyleClass().add("button-normal");
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(5));
        bottomPane.setHgap(20);
        bottomPane.getChildren().addAll(btnSubmit, btnReset);
        return bottomPane;
    }

    /**
     * 为各控件设置传入的book值
     * @param book
     */
    private void setValuesByBook(BookInfo book) {
        btnSubmit.setText("修改");
    }

    /**
     * 获得传入绝对路径下的相对路径 - 去掉当前工程根目录的路径
     * @param absPath
     * @return
     */
    private String getRelativePath(String absPath){
        String rootPath = new File(".").getAbsolutePath();
        rootPath = rootPath.substring(0, rootPath.length() - 1);
        return absPath.replace(rootPath, "");
    }

    private void initEvents() {
        //图书名称输入完毕后，自动生成拼音的首字母大写
        txtBookName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    //失去焦点时执行
                    txtInputCode.setText(PinYinUtil.getFirstSpell(txtBookName.getText()));
                }
            }
        });
        imgCover.setOnMouseClicked(e->{
            //弹出文件选择框
            FileChooser chooser = new FileChooser();
            //设置文件选择框的默认路径为当年工程根目录
            chooser.setInitialDirectory(new File("."));
            //显示文件选择框，并获得用户选择的文件对象
            File file = chooser.showOpenDialog(null);
            //System.out.println("用户选择的文件路径：" + file.getAbsolutePath());
            if(null == file) return;
            //如果用户选择了图片(要判断图片的格式 - 自行判断) 就显示，如果不能转换为图片，就显示默认值
            imgCover.setImage(new Image("file:" + file.getAbsolutePath()));
            //设置userData为图片的绝对路径，方便后面取出
            imgCover.setUserData(file.getAbsolutePath());
            System.out.println("文件的绝对路径：" + file.getAbsolutePath());
            //String rootPath = new File(".").getAbsolutePath();
            //System.out.println("工程的根目录：" + rootPath);
            //rootPath = rootPath.substring(0,rootPath.length() - 1);
            System.out.println("图片的相对路径：" + getRelativePath(file.getAbsolutePath()));
        });
        btnSubmit.setOnAction(e->{

            Button button = (Button)e.getSource();
            if(button.getText().equals("添加")){
                BookInfo newBook = new BookInfo();
                newBook.setIsbn(txtIsbn.getText());
                newBook.setBookName(txtBookName.getText());
                newBook.setInputCode(txtInputCode.getText());
                newBook.setAuthor(txtAuthor.getText());
                newBook.setKeyWords(txtKeywords.getText());
                newBook.setCateCode(txtCateCode.getText());
                newBook.setPublisher(txtPublisher.getText());
                newBook.setSummary(txtSummary.getText());
                newBook.setContentIntro(txtContentIntro.getHtmlText());
                newBook.setCoverPath(getRelativePath(imgCover.getUserData().toString()));
                newBook.setPrice(Double.parseDouble(txtPrice.getText()));
                newBook.setStoreCount(Integer.parseInt(txtStoreCount.getText()));
                newBook.setRegDate(LocalDateTime.of(dpPublish.getValue(), LocalTime.MIN));
                newBook.setMemo("备注信息");
                bookService.save(newBook);
                System.out.println("执行添加");
            }else if(button.getText().equals("修改")){

                System.out.println("执行修改");
            }
            //保存到数据库中后，跳转回图书管理标签页，并在TableView控件中显示刚添加的图书信息
            //查询出刚添加的数据，并显示这个数据
            BookSearchCondition condition = new BookSearchCondition();
            condition.setIsbn(txtIsbn.getText());
            List<BookInfo> bookList = bookService.findByCondition(condition);
            //通过静态工厂获得图书管理面板
            BookManagePane bookManagePane = (BookManagePane)MainStagePaneFactory.CreatePaneByText("图书管理");
            //调用图书管理面板的方法，设置当前显示的标签页为第一页，设置bookList为刚查询出的图书集合
            bookManagePane.setTableViewData(bookList);
            bookManagePane.setTabeIndex(0);
        });
    }

    public void fillData(BookManagePane.FXBook book){
        if(book == null) return;
        txtIsbn.setText(book.getIsbn());
        txtBookName.setText(book.getBookName());
        //将相对路径转换成绝对路径
        String absPath = new File(".").getAbsolutePath();
        absPath = absPath.substring(0, absPath.length() - 1);
        String imagePath = absPath + book.getCoverPath();
        imgCover.setImage(new Image("file:" + imagePath));
        imgCover.setUserData(book.getCoverPath());
    }


}
