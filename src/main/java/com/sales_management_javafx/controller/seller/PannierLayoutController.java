package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PannierLayoutController implements Initializable {
    @FXML
    private BorderPane pannierLayout;
    @FXML
    private Button closePannierButton;
    @FXML
    private ImageView pannierIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.onClosePannier();
    }
    private void onClosePannier(){
        this.closePannierButton.setOnAction(event->{
            BorderPane borderPane = (BorderPane) this.pannierLayout.getParent();
            borderPane.setBottom(this.getToolbar());
        });
    }
    private GridPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerToolbar.fxml"));
        GridPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  toolbar;
    }
    private void putIcons(){
        pannierIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PannierIcon.png"))));
    }
}
