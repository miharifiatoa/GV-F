package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.*;
import com.sales_management_javafx.composent.admin.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.service.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardMenuBoxController implements Initializable {
    @FXML
    private StackPane dashboardMenuBox;
    @FXML
    private GridPane dashboardMenu;
    @FXML
    private ImageView menuIcon;
    @FXML
    private Label menuLabel;
    private BorderPane account;
    private BorderPane shop;
    private BorderPane article;
    private BorderPane product;
    private BorderPane sale;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(String menu, String image_name){
        this.menuIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/"+ image_name))));
        this.menuLabel.setText(menu);
        this.menuLabel.setId(menu);
        this.initialize();
        this.onShowContent();
    }
    public void initialize(){
        FXMLLoader accountLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountLayout.fxml"));
        FXMLLoader shopLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopLayout.fxml"));
        FXMLLoader articleLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryLayout.fxml"));
        try {
            this.account = accountLoader.load();
            this.shop = shopLoader.load();
            this.article = articleLoader.load();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
    public void onShowContent(){
        menuLabel.setOnMouseClicked(this::handleClickMenuItems);
    }
    public void handleClickMenuItems(MouseEvent event){
        BorderPane dashboardLayout = (BorderPane) dashboardMenuBox.getParent().getParent();
        ScrollPane dashboardLayoutScrollpane = (ScrollPane) dashboardLayout.lookup("#dashboardLayoutScrollpane");
        switch (menuLabel.getId()) {
            case "ACCOUNT" ->{
                GridPane accountGridPane = new AccountGridPane().getGridPane(new AccountService().getAll(),4);
                dashboardLayoutScrollpane.setContent(accountGridPane);
            }
            case "SHOP" -> {
                GridPane adminShopGridPane = new AdminShopGridPane().getGridPane(new ShopService().getAll(),4);
                dashboardLayoutScrollpane.setContent(adminShopGridPane);
            }
            case "SHARE" -> {
                GridPane shareGridPane = new AdminShareGridPane().getGridPane(new ShareService().getAll(),4);
                dashboardLayoutScrollpane.setContent(shareGridPane);
            }
            case "SALE" ->{
                GridPane adminSaleGridPane = new AdminSaleGridPane().getGridPane(new SaleService().getAll(),4);
                dashboardLayoutScrollpane.setContent(adminSaleGridPane);
            }
            case "ARRIVAL" -> {
                GridPane arrivalGridPane = new AdminArrivalGridPane().getGridPane(new ArrivalService().getAll(),4);
                dashboardLayoutScrollpane.setContent(arrivalGridPane);
            }
            case "STOCK" -> {
                GridPane adminArticleGridPane = new AdminArticleGridPane().getGridPane(new ArticleService().getAll(),4);
                dashboardLayoutScrollpane.setContent(adminArticleGridPane);
            }
        }
        GridPane gridPane = (GridPane) dashboardMenuBox.getParent();
        for (Node node : gridPane.getChildren()){
            StackPane stackPane = (StackPane) node;
            for (Node node1 : stackPane.getChildren()){
                node1.getStyleClass().remove("active");
            }
        }
        dashboardMenu.getStyleClass().add("active");
        event.consume();
    }
    public StackPane getProductBoxLayout(){
        FXMLLoader productBoxLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBoxLayout.fxml"));
        StackPane productBoxLayout;
        try {
            productBoxLayout = productBoxLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxLayout;
    }
}
