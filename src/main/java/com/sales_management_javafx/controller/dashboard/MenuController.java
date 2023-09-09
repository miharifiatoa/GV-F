package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.enums.Menu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private VBox menu;
    private BorderPane account;

    private BorderPane shop;
    private BorderPane article;
    private BorderPane product;
    private BorderPane sale;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader accountLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountLayout.fxml"));
        FXMLLoader shopLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopLayout.fxml"));
        FXMLLoader articleLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/article.fxml"));
        FXMLLoader productLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/product.fxml"));
        FXMLLoader saleLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/sale.fxml"));
        try {
            this.account = accountLoader.load();
            this.shop = shopLoader.load();
            this.article = articleLoader.load();
            this.product = productLoader.load();
            this.sale = saleLoader.load();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        this.createMenuItems();
        this.onClickMenuItems();
    }
    private void createMenuItems() {
        for (Menu menu : Menu.values()) {
            AnchorPane anchorPane = new AnchorPane();
            Label menuItemLabel = new Label(menu.getValue());
            menuItemsConfiguration(anchorPane, menuItemLabel, menu);
            anchorPane.getChildren().add(menuItemLabel);
            this.menu.getChildren().add(anchorPane);
        }
    }
    private void menuItemsConfiguration(AnchorPane anchorPane, Label menuItem, Menu menu) {
        anchorPane.setId(menu.getValue());
        menuItem.getStyleClass().add("item-label");
        AnchorPane.setLeftAnchor(menuItem, 0.0);
        AnchorPane.setTopAnchor(menuItem, 0.0);
        AnchorPane.setRightAnchor(menuItem, 0.0);
        AnchorPane.setBottomAnchor(menuItem, 0.0);
        anchorPane.getStyleClass().add("no-active");
    }

    public void onClickMenuItems() {
        for (Node node : menu.getChildren()){
            node.setOnMouseClicked(event -> {
                if (Objects.equals(node.getStyleClass().toString(), "no-active")){
                    for (Node node1 :menu.getChildren()){
                        node1.getStyleClass().remove("active");
                    }
                    node.getStyleClass().add("active");
                }
                this.handleClickMenuItems(event,node);
            });
        }
    }
    public void handleClickMenuItems(MouseEvent event,Node node){
        BorderPane dashboard = (BorderPane) menu.getParent();
        switch (node.getId()) {
            case "Comptes" -> dashboard.setCenter(this.account);
            case "Boutiques" -> dashboard.setCenter(this.shop);
            case "Articles" -> dashboard.setCenter(this.article);
            case "Produits" -> dashboard.setCenter(this.product);
            case "Ventes" -> dashboard.setCenter(this.sale);
        }
        BorderPane.setAlignment(dashboard, Pos.CENTER);
        event.consume();
    }
}
