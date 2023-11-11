package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.ArrivalArticlesGridPane;
import com.sales_management_javafx.composent.SaleArticlesGridPane;
import com.sales_management_javafx.composent.ShareArticlesGridPane;
import com.sales_management_javafx.composent.admin.AdminArrivalGridPane;
import com.sales_management_javafx.composent.admin.AdminSaleGridPane;
import com.sales_management_javafx.composent.admin.AdminShareGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.service.ArrivalArticleService;
import org.sales_management.service.SaleArticleService;
import org.sales_management.service.ShareArticleService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArticleStoryController implements Initializable {
    @FXML private ScrollPane saleInfoScrollpane;
    @FXML private ScrollPane shareInfoScrollpane;
    @FXML private ScrollPane arrivalInfoScrollpane;
    @FXML private DatePicker saleDatePicker;
    @FXML private DatePicker arrivalDatePicker;
    @FXML private DatePicker shareDatePicker;
    @FXML private Label title;
    @FXML private Button action;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleEntity article){
        this.setSaleInfoScrollpane(article);
        this.setArrivalInfoScrollpane(article);
        this.setShareInfoScrollpane(article);
        this.setArrivalDatePicker(article);
        this.setSaleDatePicker(article);
        this.setShareDatePicker(article);
        title.setText(article.getProductType().getName() + " : " + article.getCode());
    }
    private void setSaleInfoScrollpane(ArticleEntity article){
        GridPane saleArticlesGridPane = new SaleArticlesGridPane().getGridPane(article.getSaleArticles(),1);
        saleInfoScrollpane.setContent(saleArticlesGridPane);
    }
    private void setArrivalInfoScrollpane(ArticleEntity article){
        GridPane arrivalArticlesGridPane = new ArrivalArticlesGridPane().getGridPane(article.getArrivalArticles(),1);
        arrivalInfoScrollpane.setContent(arrivalArticlesGridPane);
    }
    private void setShareInfoScrollpane(ArticleEntity article){
        GridPane saleArticlesGridPane = new ShareArticlesGridPane().getGridPane(article.getShareArticles(),1);
        shareInfoScrollpane.setContent(saleArticlesGridPane);
    }
    private Collection<LocalDate> getArrivalsArticleDate(ArticleEntity article){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ArrivalArticleEntity arrivalArticle : article.getArrivalArticles()){
            localDates.add(arrivalArticle.getArrivalDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSalesArticleDate(ArticleEntity article){
        Collection<LocalDate> localDates = new HashSet<>();
        for (SaleArticleEntity saleArticle : article.getSaleArticles()){
            localDates.add(saleArticle.getSaleDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSharesArticleDate(ArticleEntity article){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ShareArticleEntity shareArticle : article.getShareArticles()){
            localDates.add(shareArticle.getShareDate().toLocalDate());
        }
        return localDates;
    }
    private void setArrivalDatePicker(ArticleEntity article){
        arrivalDatePicker.setOnAction(event->{
            LocalDate date = arrivalDatePicker.getValue();
            GridPane arrivalArticlesGridPane = new ArrivalArticlesGridPane().getGridPane(new ArrivalArticleService().getArrivalsArticleInArticleByDate(article,date),1);
            arrivalInfoScrollpane.setContent(arrivalArticlesGridPane);
        });
        arrivalDatePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (getArrivalsArticleDate(article).contains(item)) {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                    }

                };
            }
        });
    }
    private void setSaleDatePicker(ArticleEntity article){
        saleDatePicker.setOnAction(event->{
            LocalDate date = saleDatePicker.getValue();
            GridPane saleArticlesGridPane = new SaleArticlesGridPane().getGridPane(new SaleArticleService().getSalesArticleInArticleByDate(article,date),1);
            saleInfoScrollpane.setContent(saleArticlesGridPane);
        });
        saleDatePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (getSalesArticleDate(article).contains(item)) {
                            setStyle("-fx-background-color: ffa600;");
                        }
                    }

                };
            }
        });
    }
    private void setShareDatePicker(ArticleEntity article){
        shareDatePicker.setOnAction(event->{
            LocalDate date = shareDatePicker.getValue();
            GridPane shareArticlesGridPane = new ShareArticlesGridPane().getGridPane(new ShareArticleService().getSharesArticleInArticleByDate(article,date),1);
            shareInfoScrollpane.setContent(shareArticlesGridPane);
        });
        shareDatePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (getSharesArticleDate(article).contains(item)) {
                            setStyle("-fx-background-color: #ff2ceabc;");
                        }
                    }

                };
            }
        });
    }
    private void setTitle(){

    }
}
