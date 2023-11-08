package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ShopEntity;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShopBoxController implements Initializable {
    @FXML
    private Label shopNameLabel;
    @FXML
    private Label shopAddresslabel;
    @FXML
    private StackPane shopBoxStackpane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void initialize(ShopEntity shop){
        this.shopNameLabel.setText(shop.getName());
        this.shopAddresslabel.setText(shop.getAddress());
        this.onSelectShop(shop);
    }
    private void onSelectShop(ShopEntity shop){
        shopBoxStackpane.setOnMouseClicked(event->{
            GridPane gridPane = (GridPane) shopBoxStackpane.getParent();
            for (Node node : gridPane.getChildren()){
                node.getStyleClass().remove("active");
            }
            shopBoxStackpane.getStyleClass().add("active");
            FileIO.writeTo("shop.dat",shop);
            handleSelectShop();
        });
    }
    private void handleSelectShop(){
        BorderPane shopLayoutBorderpane = (BorderPane) shopBoxStackpane.getParent().getParent().getParent().getParent().getParent();
        Button shareProductButton = (Button) shopLayoutBorderpane.lookup("#shareProductButton");
        shareProductButton.setDisable(false);
    }
}
