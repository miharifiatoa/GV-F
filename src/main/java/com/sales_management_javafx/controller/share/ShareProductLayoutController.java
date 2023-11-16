package com.sales_management_javafx.controller.share;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.share.ShareGridPane;
import com.sales_management_javafx.composent.ShopGridPane;
import com.sales_management_javafx.composent.StockistArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.entity.ShareEntity;
import org.sales_management.entity.ShopEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.ShareService;
import org.sales_management.service.ShopService;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;
public class ShareProductLayoutController implements Initializable {
    @FXML private StackPane shareArticleLayoutStackpane;
    @FXML private BorderPane shareArticleLayoutBorderpane;
    @FXML private ScrollPane shareArticleLayoutScrollpane;
    @FXML private ImageView shareListIcon;
    @FXML private ImageView shareIcon;
    @FXML private ImageView sendIcon;
    @FXML private ImageView shopIcon;
    @FXML private ImageView returnIcon;
    @FXML private Button closeShareListButton;
    @FXML private Button shareProductButton;
    @FXML private Button sendProductButton;
    @FXML private Button returnButton;
    @FXML private Button shareList;
    @FXML private Button share;
    @FXML private GridPane shareToolbar;
    @FXML private BorderPane shopLayoutBorderpane;
    private final ShopService shopService;
    private final ShareService shareService;
    private final UserEntity user;

    public ShareProductLayoutController() {
        this.user = SessionManager.getSession().getCurrentUser();
        this.shareService = new ShareService();
        this.shopService = new ShopService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.shareArticleLayoutBorderpane.setVisible(true);
        this.shopLayoutBorderpane.setVisible(false);
        this.shareProductButton.setDisable(true);
        this.shareToolbar.setVisible(true);
        this.putIcons();
        closeShareListButton.setOnAction(event->this.setCloseShareListButton());
        this.setProducts();
        this.onShareProduct();
        this.onReturnToShareList();
        this.setShops();
        this.setShareProductButton();
        this.setShare();
        this.setShareList();
    }
    private void setShare(){
        share.setOnAction(event->{
            setProducts();
        });
    }
    private void setShareList(){
        shareList.setOnAction(event->{
            GridPane shareGridPane = new ShareGridPane().getGridPane(new ShareService().getSharesByDate(LocalDate.now()),4);
            shareArticleLayoutScrollpane.setContent(shareGridPane);
        });
    }
    public void setProducts(){
        GridPane articleInfoGridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("shares.dat"),2);
        shareArticleLayoutScrollpane.setContent(articleInfoGridPane);
    }
    public void putIcons(){
        shareIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShowShareListIcon.png"))));
        shareListIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ListIcon.png"))));
        sendIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/SendIcon.png"))));
        shopIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShopIcon.png"))));
        returnIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ReturnIcon.png"))));
    }
    private void onShareProduct(){
        this.sendProductButton.setOnAction(event->{
            this.shareArticleLayoutBorderpane.setVisible(false);
            this.shopLayoutBorderpane.setVisible(true);
        });
    }
    private void setShareProductButton(){
        shareProductButton.setOnAction(event->{
            ShareEntity share = new ShareEntity();
            ShopEntity shop = (ShopEntity) FileIO.readFrom("shop.dat");
            share.setShop(shop);
            share.setShareDate(LocalDateTime.now());
            if (user != null){
                share.setUser(user);
            }
            share.setShareDate(LocalDateTime.now());
            Collection<ArticleTypeEntity> articles = FileIO.readArticleFromFile("shares.dat");
            if (shareService.toShareArticles(share , articles) != null){
                articles.clear();
                FileIO.writeTo("shares.dat",articles);
                GridPane gridPane = new StockistArticleGridPane().getGridPane(new ArticleTypeService().getAll(),4);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) shareArticleLayoutStackpane.getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                stockistBoxLayoutScrollpane.setContent(gridPane);
                this.setCloseShareListButton();
            }
        });
    }
    private void setShops(){
        GridPane gridPane = new ShopGridPane().getGridPane(shopService.getAll(),4);
        ScrollPane scrollPane = (ScrollPane) shopLayoutBorderpane.getCenter();
        scrollPane.setContent(gridPane);
    }
    private void onReturnToShareList(){
        this.returnButton.setOnAction(event->{
            this.shareArticleLayoutBorderpane.setVisible(true);
            this.shopLayoutBorderpane.setVisible(false);
        });
    }
    private void setCloseShareListButton(){
        shareArticleLayoutStackpane.getParent().setVisible(false);
        BorderPane stockistLayout = (BorderPane) shareArticleLayoutStackpane.getParent().getParent().getParent();
        stockistLayout.setBottom(getToolbar());
    }

    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}

