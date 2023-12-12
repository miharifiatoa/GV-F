package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.composent.admin.AdminArticleTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ArticleTypeEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminArticleBoxController implements Initializable {
    @FXML private Label codeLabel;
    @FXML private Label quantityLabel;
    @FXML private Label priceLabel;
    @FXML private StackPane articleBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        codeLabel.setText(article.getCode());
        quantityLabel.setText(this.getQuantity(article) + " reste(s)");
        priceLabel.setText(DecimalFormat.format(article.getPrice()) + " Ar");
        this.setArticleBox(article);
    }
    private void setArticleBox(ArticleEntity article){
        articleBox.setOnMouseClicked(event->{
            GridPane gridPane = new AdminArticleTypeGridPane().getGridPane(article.getArticleTypeEntities(),4);
            getDashboardLayoutScrollpane().setContent(gridPane);
        });
    }
    private ScrollPane getDashboardLayoutScrollpane(){
        return (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
    }
    private int getQuantity(ArticleEntity article){
        int quantity = 0;
        for (ArticleTypeEntity articleType : article.getArticleTypeEntities()){
            quantity += articleType.getQuantity();
        }
        return quantity;
    }
}
