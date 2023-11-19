package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.arrival.ArrivalArticlesGridPane;
import com.sales_management_javafx.composent.sale.SaleArticlesGridPane;
import com.sales_management_javafx.composent.share.ShareArticlesGridPane;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.service.ArrivalArticleService;
import org.sales_management.service.SaleArticleService;
import org.sales_management.service.ShareArticleService;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    public void initialize(ArticleTypeEntity article){
        this.setSaleInfoScrollpane(article);
        this.setArrivalInfoScrollpane(article);
        this.setShareInfoScrollpane(article);
        this.setArrivalDatePicker(article);
        this.setSaleDatePicker(article);
        this.setShareDatePicker(article);
        title.setText(article.getArticle().getCode() + " : " + article.getArticle().getCode());
        this.infoBox(article);
    }
    
    public void infoBox(ArticleTypeEntity articleType){
        action.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/articleParam.fxml"));

                loader.setControllerFactory(controllerClass -> {
                    try {
                        return controllerClass.getConstructor(ArticleTypeEntity.class).newInstance(articleType);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage mainStage = SalesApplication.getPrimaryStage();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(articleType.getArticle().getCode()+" Color : "+
                            articleType.getColor()+" Size : "+articleType.getSize());
            stage.initOwner(mainStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


        });
    }
    
    
    private void setSaleInfoScrollpane(ArticleTypeEntity article){
        GridPane saleArticlesGridPane = new SaleArticlesGridPane().getGridPane(article.getSaleArticles(),1);
        saleInfoScrollpane.setContent(saleArticlesGridPane);
    }
    private void setArrivalInfoScrollpane(ArticleTypeEntity article){
        GridPane arrivalArticlesGridPane = new ArrivalArticlesGridPane().getGridPane(article.getArrivalArticles(),1);
        arrivalInfoScrollpane.setContent(arrivalArticlesGridPane);
    }
    private void setShareInfoScrollpane(ArticleTypeEntity article){
        GridPane saleArticlesGridPane = new ShareArticlesGridPane().getGridPane(article.getShareArticles(),1);
        shareInfoScrollpane.setContent(saleArticlesGridPane);
    }
    private Collection<LocalDate> getArrivalsArticleDate(ArticleTypeEntity article){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ArrivalArticleEntity arrivalArticle : article.getArrivalArticles()){
            localDates.add(arrivalArticle.getArrivalDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSalesArticleDate(ArticleTypeEntity article){
        Collection<LocalDate> localDates = new HashSet<>();
        for (SaleArticleEntity saleArticle : article.getSaleArticles()){
            localDates.add(saleArticle.getSaleDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSharesArticleDate(ArticleTypeEntity article){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ShareArticleEntity shareArticle : article.getShareArticles()){
            localDates.add(shareArticle.getShareDate().toLocalDate());
        }
        return localDates;
    }
    private void setArrivalDatePicker(ArticleTypeEntity article){
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
    private void setSaleDatePicker(ArticleTypeEntity article){
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
    private void setShareDatePicker(ArticleTypeEntity article){
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
