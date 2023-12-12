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
import org.sales_management.entity.*;
import org.sales_management.service.ArrivalArticleService;
import org.sales_management.service.SaleArticleService;
import org.sales_management.service.ShareArticleService;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.sales_management.service.ShareService;

public class ArticleStoryController implements Initializable {
    @FXML private ScrollPane storyScrollpane;
    @FXML private DatePicker storyDatePicker;
    @FXML private Label title;
    @FXML private Label arrivalLabel;
    @FXML private Label saleLabel;
    @FXML private Label shareLabel;
    @FXML private Button action;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArticleTypeEntity articleType){
        this.setArrival(articleType);
        this.setArrivalLabel(articleType);
        this.setShareLabel(articleType);
        this.setSaleLabel(articleType);
        this.setStoryDatePicker(articleType);
//        title.setText(articleType.getArticle().getProductTypeEntity().getProduct().getProductCategory().getName() + " / " + articleType.getArticle().getProductTypeEntity().getProduct().getName() + " / " + articleType.getArticle().getProductTypeEntity().getName() + " / " + articleType.getArticle().getCode());
        this.infoBox(articleType);
    }
    
    private void infoBox(ArticleTypeEntity articleType){
        action.setOnAction(event -> {
            Scene scene = new Scene(getArticleParam(articleType));
            Stage mainStage = SalesApplication.getPrimaryStage();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initOwner(mainStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        });
    }
    private void setArrivalLabel(ArticleTypeEntity articleType){
        arrivalLabel.setOnMouseClicked(event->{
            setArrival(articleType);
        });
    }
    private void setArrival(ArticleTypeEntity articleType){
        storyDatePicker.setId("arrival");
        GridPane arrivalArticlesGridPane = new ArrivalArticlesGridPane().getGridPane(articleType.getArrivalArticles(),1);
        storyScrollpane.setContent(arrivalArticlesGridPane);
    }
    private void setShareLabel(ArticleTypeEntity articleType){
        shareLabel.setOnMouseClicked(event->{
            storyDatePicker.setId("share");
            GridPane saleArticlesGridPane = new ShareArticlesGridPane().getGridPane(articleType.getShareArticles(),1);
            storyScrollpane.setContent(saleArticlesGridPane);
        });
    }
    private void setSaleLabel(ArticleTypeEntity articleType){
        saleLabel.setOnMouseClicked(event->{
            storyDatePicker.setId("sale");
            GridPane saleArticlesGridPane = new SaleArticlesGridPane().getGridPane(articleType.getSaleArticles(),1);
            storyScrollpane.setContent(saleArticlesGridPane);
        });
    }
    private Collection<LocalDate> getArrivalsArticleDate(ArticleTypeEntity articleType){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ArrivalArticleEntity arrivalArticle : articleType.getArrivalArticles()) {
            localDates.add(arrivalArticle.getArrivalDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSharesArticleDate(ArticleTypeEntity articleType){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ShareArticleEntity shareArticle : articleType.getShareArticles()) {
            localDates.add(shareArticle.getShareDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSalesArticleDate(ArticleTypeEntity articleType){
        Collection<LocalDate> localDates = new HashSet<>();
        for (SaleArticleEntity saleArticle : articleType.getSaleArticles()) {
            localDates.add(saleArticle.getSaleDate().toLocalDate());
        }
        return localDates;
    }
    private void setStoryDatePicker(ArticleTypeEntity articleType){
        storyDatePicker.setOnAction(event->{
            LocalDate date = storyDatePicker.getValue();
            if (Objects.equals(storyDatePicker.getId(), "arrival")){
                GridPane arrivalArticlesGridPane = new ArrivalArticlesGridPane().getGridPane(new ArrivalArticleService().getArrivalArticlesByDate(articleType,date),1);
                storyScrollpane.setContent(arrivalArticlesGridPane);
            }
            else if (Objects.equals(storyDatePicker.getId(), "share")){
                GridPane shareArticlesGridPane = new ShareArticlesGridPane().getGridPane(new ShareArticleService().getShareArticlesByDate(articleType,date),1);
                storyScrollpane.setContent(shareArticlesGridPane);
            }
            else if (Objects.equals(storyDatePicker.getId(), "sale")){
                GridPane saleArticlesGridPane = new SaleArticlesGridPane().getGridPane(new SaleArticleService().getSaleArticlesByDate(articleType,date),1);
                storyScrollpane.setContent(saleArticlesGridPane);
            }
        });
        storyDatePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (Objects.equals(storyDatePicker.getId(), "arrival")) {
                            if (getArrivalsArticleDate(articleType).contains(item)) {
                                setStyle("-fx-background-color: lightgreen;");
                            }
                        }
                        else if (Objects.equals(storyDatePicker.getId(), "share")){
                            if (getSharesArticleDate(articleType).contains(item)) {
                                setStyle("-fx-background-color: #ff2ceabc;");
                            }
                        }
                        else if (Objects.equals(storyDatePicker.getId(), "sale")){
                            if (getSalesArticleDate(articleType).contains(item)) {
                                setStyle("-fx-background-color: ffa600;");
                            }
                        }
                    }

                };
            }
        });
    }
    private Parent getArticleParam(ArticleTypeEntity articleType){
        Parent parent;
        FXMLLoader loader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/articleParam.fxml"));
        try {
            parent = loader.load();
            ParamController paramController = loader.getController();
            paramController.initialize(articleType);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parent;
    }
}
