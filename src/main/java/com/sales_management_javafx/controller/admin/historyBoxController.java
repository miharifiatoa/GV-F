package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.sales_management.entity.StockHistoryEntity;

public class historyBoxController implements Initializable {
    @FXML ImageView deleteIcon;
    @FXML Label codeArticle;
    @FXML Label color;
    @FXML Label size;
    @FXML Label marque;
    @FXML Label reference;
    @FXML Label prix;
    @FXML Label type;
    @FXML Label categorie;
    @FXML Label action;
    @FXML Label description;
    @FXML Label date;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void initialize(StockHistoryEntity history){
        codeArticle.setText(history.getArticleType().getArticle().getCode());
        color.setText(history.getArticleType().getColor());
        size.setText(history.getArticleType().getSize());
        marque.setText(history.getArticleType().getArticle().getProductTypeEntity().getBrand());
        reference.setText(history.getArticleType().getArticle().getProductTypeEntity().getReference());
        prix.setText(history.getArticleType().getArticle().getPrice().toString());
        type.setText(history.getArticleType().getArticle().getProductTypeEntity().getName());
        categorie.setText(history.getArticleType().getArticle().getProductTypeEntity().getProduct().getProductCategory().getName());
        action.setText(history.getAction());
        description.setText(history.getDescription());
        date.setText(history.getDate().toString());
        
    }
    public void putIcons(){
        deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }

}