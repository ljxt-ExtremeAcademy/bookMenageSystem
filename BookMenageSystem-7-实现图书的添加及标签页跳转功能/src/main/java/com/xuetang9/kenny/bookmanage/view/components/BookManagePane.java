package com.xuetang9.kenny.bookmanage.view.components;

import com.xuetang9.kenny.bookmanage.entity.BookCate;
import com.xuetang9.kenny.bookmanage.entity.BookInfo;
import com.xuetang9.kenny.bookmanage.entity.BookSearchCondition;
import com.xuetang9.kenny.bookmanage.service.BookCateService;
import com.xuetang9.kenny.bookmanage.service.BookService;
import com.xuetang9.kenny.bookmanage.service.impl.BookCateServiceImpl;
import com.xuetang9.kenny.bookmanage.service.impl.BookServiceImpl;
import com.xuetang9.kenny.bookmanage.util.DateUtil;
import javafx.beans.property.*;
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
import java.util.List;

/**
 * 图书管理面板，包含图书管理、编辑图书、统计分析和添加图书
 * @author 老九学堂·窖头
 * @version 1.0
 * @copyright http://www.xuetang9.com
 */
public class BookManagePane extends StackPane {
    private TableView tableView = new TableView();
    private ComboBox<String> cmbBookCate = new ComboBox<>();
    private DatePicker dateFrom = new DatePicker(LocalDate.now().plusYears(31));
    private DatePicker dateTo   = new DatePicker(LocalDate.now().plusYears(31).plusMonths(3));
    private TextField txtKeyWord = new TextField();
    private Button btnSearch = new Button("搜索");
    private Button btnClear = new Button("清空");
    //每次集合中的数据更新时，同样需要修改分页的页数
    private Pagination pagination = new Pagination(10, 0);
    /** 为了方便其他面板更改标签页，设置为成员对象 */
    private TabPane tabPane = new TabPane();
    private BookEditPane addBookPane = new BookEditPane();
    private BookEditPane updateBookPane = new BookEditPane();

    public BookManagePane(){
        setPadding(new Insets(10, 20, 10, 20));

        //设置标签页不会被关闭
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab tab_bookmanage = new Tab("图书管理", createTabBookManage());
        Tab tab_bookadd    = new Tab("添加图书", addBookPane);
        Tab tab_bookupdate = new Tab("编辑图书", updateBookPane);

        tabPane.getTabs().addAll(tab_bookmanage, tab_bookadd, tab_bookupdate);
        getChildren().add(tabPane);


        setCmbBookCateDate();
        //初始化完所有的控件后，再初始化TableView中的内容
        initTableViewColumn();
        //初始化TableView后，填充数据 - 注意：如果数据比较多，那么这里建议使用一个线程去读取数据和填充
        setTableViewData();

        //初始化事件
        initEvents();
    }

    public void setTabeIndex(int index){
        //设置标签页的显示下标
        tabPane.getSelectionModel().select(index);
    }

    private void initEvents() {
        btnSearch.setOnAction(e->{
            BookSearchCondition condition = new BookSearchCondition();
            condition.setKeyWords(txtKeyWord.getText());
            condition.setCateCode(cmbBookCate.getSelectionModel().getSelectedItem().split("-")[0]);
            condition.setFromDate(dateFrom.getValue());
            condition.setToDate(dateTo.getValue());
            List<BookInfo> bookList = bookService.findByCondition(condition);
            this.setTableViewData(bookList);
        });
        tableView.setOnMouseClicked(e->{
            if(e.getClickCount() == 2){//双击
                FXBook book = (FXBook)tableView.getSelectionModel().getSelectedItem();
                updateBookPane.fillData(book);
                setTabeIndex(2);
            }
        });
    }

    /**
     * 创建图书管理标签面板 Top - Center(top-center-bottom)
     * @return
     */
    private Node createTabBookManage() {
        //将组合框设置成成员对象，方便其他方法调用
        //组合框中的数据应该来自数据库 - 调用业务层代码
        cmbBookCate.getItems().addAll("请选择", "小说");
        txtKeyWord.setPromptText("请输入关键字...");
        dateFrom.setPrefWidth(120);
        dateTo.setPrefWidth(120);

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
//        Node pagination = setPagination(pageCount, 5, 0);
        //实际的顺序应该是bookList中先有数据，再根据数据设置对应的分页
        setPagination(pageCount, 0);
        tableViewPane.setCenter(pagination);
        tableViewPane.setBottom(createButtonPane());
        BorderPane.setMargin(pagination, new Insets(10, 0, 0, 0));

        root.setTop(searchPane_v);
        root.setCenter(tableViewPane);
        return root;
    }



    /** 与tableView控件绑定的图书集合数据 */
    private ObservableList<FXBook> bookList = FXCollections.observableArrayList();

    /** 将每个分页所显示的条目数设置为静态成员 */
    public static int ITEM_PER_PAGE = 5;

    /**
     * 创建一个分页面板，可以实现数据的分页显示
     * @param pageCount     分页的总页面数
     * @param currPageIndex 当先显示分页的下标
     * @return
     */
    private void setPagination(int pageCount, int currPageIndex) {
        //Pagination pagination = new Pagination(pageCount, currPageIndex);
        this.pagination.setPageCount(pageCount);
        this.pagination.setCurrentPageIndex(currPageIndex);
        this.pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                int pageIndex = param.intValue();           //获得当前显示的分页下标
                tableView.getItems().clear();               //为了方便显示不同的查询结果，所以每次更新分页时，首先清空tableView控件
                //连接数据库，根据分页下标返回对应的集合数据
                //为了实现分页的效果，所以需要subList
                int fromIndex = pageIndex * ITEM_PER_PAGE;    //起始下标
                int toIndex   = (pageIndex + 1) * ITEM_PER_PAGE;
                if(toIndex >= bookList.size()) toIndex = bookList.size();
                List subList = bookList.subList(fromIndex, toIndex);
                tableView.setItems(FXCollections.observableArrayList(subList));
                return tableView;
            }
        });
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

    /*****  在图书管理界面的TableView中显示数据库中的图书信息 **********************/
    //1、书写图书实体类、FXBook类
    //2、书写BookDAO以及实现类 - 建议进行查询的测试
    //3、设置TableView控件的列，以便填充数据
    //4、填充数据并设置分页

    /**
     * 初始化表格控件的列，以便后续的数据绑定 - 本方法建议在构造方法的最后调用
     */
    private void initTableViewColumn(){
        String[] colNames = {"图书编号", "图书名称", "出版号", "拼音输入码", "作者", "关键字", "内容摘要", "单价", "库存", "上架时间"};
        String[] fields   = {"bookId", "bookName", "isbn", "inputCode", "author", "keywords", "publisher", "price", "storeCount", "regDate"};
        int[] colWidths = {80, 120, 80, 120, 100, 120, 200, 40, 40, 80};
        TableColumn[] columns = new TableColumn[colNames.length];
        for(int i = 0; i < columns.length; i++){
            columns[i] = new TableColumn(colNames[i]);
            //绑定TableView控件和实体类中的属性
            columns[i].setCellValueFactory(new PropertyValueFactory<>(fields[i]));
            columns[i].setPrefWidth(colWidths[i]);
        }
        tableView.getColumns().addAll(columns);
        Label label = new Label("没有相应的图书信息，请修改查询条件！");
        label.setStyle("-fx-font-size: 18px;-fx-text-fill: red;");
        tableView.setPlaceholder(label);
    }


    /**
     * 图书业务对象作为面板的成员对象 - 大家在实现代码的时候，可以将对象定义在上面，在构造方法中实例化bookService对象
     */
    private BookService bookService = new BookServiceImpl();

    public void setTableViewData(List<BookInfo> bookList){
        //bookList为当前方法传入的集合对象
        //this.bookList为当前面板的成员对象 - 这里只是为了命名方便做出的妥协，大家可以根据自己的需要自由命名
        //tableView.getItems().clear();
        this.bookList.clear();

        if(null == bookList) {
            return;
        }
        //bookList中先有数据，才可以设置分页
        bookList.forEach(book->{
            this.bookList.add(new FXBook(book));
        });
        //需要修改下分页控件的属性 - 这里默认每页显示5条，5也可以设置为常量
        int pageCount = bookList.size() / 5;
        if(pageCount == 0) pageCount = 1;       //至少有1页
        if(this.bookList.size() > ITEM_PER_PAGE && this.bookList.size() % 5 != 0){
            pageCount++;
        }
        setPagination(pageCount, 0);

//        this.pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
//        this.pagination.setCurrentPageIndex(0);
//        pagination.setPageFactory(new Callback<Integer, Node>() {
//            @Override
//            public Node call(Integer param) {
//                int pageIndex = param.intValue();           //获得当前显示的分页下标
//                tableView.getItems().clear();               //为了方便显示不同的查询结果，所以每次更新分页时，首先清空tableView控件
//                //连接数据库，根据分页下标返回对应的集合数据
//                //为了实现分页的效果，所以需要subList
//                int fromIndex = pageIndex * 5;    //起始下标
//                int toIndex   = (pageIndex + 1) * 5;
//                if(toIndex >= bookList.size()) toIndex = bookList.size();
//                List subList = bookList.subList(fromIndex, toIndex);
//                tableView.setItems(FXCollections.observableArrayList(subList));
//                return tableView;
//            }
//        });


    }

    /**
     * 从数据库读取图书信息，填充到TableView控件中显示
     */
    private void setTableViewData(){
        //调用业务方法，获取t_bookinfo表中的所有数据
        //List<BookInfo> bookList = bookService.findAll();
        List<BookInfo> bookInfoList = bookService.findAll();
        setTableViewData(bookInfoList);
        //需要将BookInfo转换成FXBook对象以便显示
//        tableView.getItems().clear();
//        bookInfoList.forEach(book->{
//            bookList.add(new FXBook(book));
//        });
//        ObservableList itemList = tableView.getItems();
//        for(BookInfo book : bookList){
//            itemList.add(new FXBook(book));
//        }
    }


    /**  完成图书分类组合框的数据填充 */
    public void setCmbBookCateDate(){
        //也可以把Service对象设置成为成员对象
        BookCateService bookCateService = new BookCateServiceImpl();
        List<BookCate> cateList = bookCateService.getAllParentCate();
        cmbBookCate.getItems().clear();
        cateList.forEach(cate->{
            String code = cate.getBookCateCode();   //图书分类代码，如A、B、C等
            String name = cate.getBookCateName();   //分类名称
            name = name.length() >= 6 ? name.substring(0, 6) + "..." : name;
            cmbBookCate.getItems().add(code + "-" + name);
        });
        cmbBookCate.setValue("请选择分类");
        //cmbBookCate.getSelectionModel().select(1);
    }


    /**
     * tableView控件专用的图书对象类
     */
    public class FXBook {
        private IntegerProperty bookId;
        private StringProperty isbn;
        private StringProperty bookName;
        private StringProperty inputCode;
        private StringProperty author;
        private StringProperty keyWords;
        private StringProperty cateCode;
        private StringProperty publisher;
        private StringProperty summary;
        private StringProperty contentIntro;
        private StringProperty coverPath;
        private DoubleProperty price;
        private IntegerProperty storeCount;
        private StringProperty regDate;     //因为只是显示日期，所以直接使用StringProperty类型
        private StringProperty memo;

        public FXBook(BookInfo book){
            this.bookId = new SimpleIntegerProperty(book.getBookId());
            this.bookName = new SimpleStringProperty(book.getBookName());
            this.isbn = new SimpleStringProperty(book.getIsbn());
            this.inputCode = new SimpleStringProperty(book.getInputCode());
            this.author = new SimpleStringProperty(book.getAuthor());
            this.keyWords = new SimpleStringProperty(book.getKeyWords());
            this.cateCode = new SimpleStringProperty(book.getCateCode());
            this.publisher = new SimpleStringProperty(book.getPublisher());
            this.summary = new SimpleStringProperty(book.getSummary());
            this.contentIntro = new SimpleStringProperty(book.getContentIntro());
            this.coverPath = new SimpleStringProperty(book.getCoverPath());
            this.price = new SimpleDoubleProperty(book.getPrice());
            this.storeCount = new SimpleIntegerProperty(book.getStoreCount());
            if(book.getRegDate() != null){
                this.regDate = new SimpleStringProperty(DateUtil.GetLocalDateTimeFromDefaultPattern(book.getRegDate()));
            }else{
                this.regDate = new SimpleStringProperty("上架时间未知");
            }
            this.memo = new SimpleStringProperty(book.getMemo());
        }

        public int getBookId() {
            return bookId.get();
        }

        public IntegerProperty bookIdProperty() {
            return bookId;
        }

        public void setBookId(int bookId) {
            this.bookId.set(bookId);
        }

        public String getIsbn() {
            return isbn.get();
        }

        public StringProperty isbnProperty() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn.set(isbn);
        }

        public String getBookName() {
            return bookName.get();
        }

        public StringProperty bookNameProperty() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName.set(bookName);
        }

        public String getInputCode() {
            return inputCode.get();
        }

        public StringProperty inputCodeProperty() {
            return inputCode;
        }

        public void setInputCode(String inputCode) {
            this.inputCode.set(inputCode);
        }

        public String getAuthor() {
            return author.get();
        }

        public StringProperty authorProperty() {
            return author;
        }

        public void setAuthor(String author) {
            this.author.set(author);
        }

        public String getKeyWords() {
            return keyWords.get();
        }

        public StringProperty keyWordsProperty() {
            return keyWords;
        }

        public void setKeyWords(String keyWords) {
            this.keyWords.set(keyWords);
        }

        public String getCateCode() {
            return cateCode.get();
        }

        public StringProperty cateCodeProperty() {
            return cateCode;
        }

        public void setCateCode(String cateCode) {
            this.cateCode.set(cateCode);
        }

        public String getPublisher() {
            return publisher.get();
        }

        public StringProperty publisherProperty() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher.set(publisher);
        }

        public String getSummary() {
            return summary.get();
        }

        public StringProperty summaryProperty() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary.set(summary);
        }

        public String getContentIntro() {
            return contentIntro.get();
        }

        public StringProperty contentIntroProperty() {
            return contentIntro;
        }

        public void setContentIntro(String contentIntro) {
            this.contentIntro.set(contentIntro);
        }

        public String getCoverPath() {
            return coverPath.get();
        }

        public StringProperty coverPathProperty() {
            return coverPath;
        }

        public void setCoverPath(String coverPath) {
            this.coverPath.set(coverPath);
        }

        public double getPrice() {
            return price.get();
        }

        public DoubleProperty priceProperty() {
            return price;
        }

        public void setPrice(double price) {
            this.price.set(price);
        }

        public int getStoreCount() {
            return storeCount.get();
        }

        public IntegerProperty storeCountProperty() {
            return storeCount;
        }

        public void setStoreCount(int storeCount) {
            this.storeCount.set(storeCount);
        }

        public String getRegDate() {
            return regDate.get();
        }

        public StringProperty regDateProperty() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate.set(regDate);
        }

        public String getMemo() {
            return memo.get();
        }

        public StringProperty memoProperty() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo.set(memo);
        }
    }
}
