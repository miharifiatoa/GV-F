package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.classes.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.ShareArticleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminArticleStoryBoxController implements Initializable {
    @FXML private Label text;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(SaleArticleEntity saleArticle){
        if (saleArticle.isCanceled()){
            text.setDisable(true);
            text.getStyleClass().add("canceled-text");
            text.setText(saleArticle.getSale().getUser().getAccount().getUsername()
                    + " a annulé(e) la vente de "
                    + saleArticle.getQuantity()
                    + " "
                    + saleArticle.getArticleType().getArticle().getProductTypeEntity().getName()
                    + " "
                    + saleArticle.getArticleType().getColor()
                    + " , taille : "
                    + saleArticle.getArticleType().getSize()
                    + " au client "
                    + saleArticle.getSale().getClient().getName()
                    + " qui a passé le "
                    + DateTimeFormatter.format(saleArticle.getSaleDate()));
        }
        else{
            text.setText(saleArticle.getSale().getUser().getAccount().getUsername()
                    + " a vendu "
                    + saleArticle.getQuantity()
                    + " "
                    + saleArticle.getArticleType().getArticle().getProductTypeEntity().getName()
                    + " "
                    + saleArticle.getArticleType().getColor()
                    + " , taille : "
                    + saleArticle.getArticleType().getSize()
                    + " au client "
                    + saleArticle.getSale().getClient().getName()
                    + " le "
                    + DateTimeFormatter.format(saleArticle.getSaleDate()));
        }
    }
    public void initialize(ArrivalArticleEntity arrivalArticle){
        if (arrivalArticle.isCanceled()){
            text.setDisable(true);
            text.getStyleClass().add("canceled-text");
            text.setText(arrivalArticle.getArrival().getUser().getAccount().getUsername()
                    + " a annulé(e) l ' ajout de "
                    + arrivalArticle.getQuantity()
                    + " "
                    + arrivalArticle.getArticleType().getArticle().getProductTypeEntity().getName()
                    + " "
                    + arrivalArticle.getArticleType().getColor()
                    + " , taille : "
                    + arrivalArticle.getArticleType().getSize()
                    + " qui arrive au stock le "
                    + DateTimeFormatter.format(arrivalArticle.getArrivalDate()));
        }
        else {
            text.setText(arrivalArticle.getArrival().getUser().getAccount().getUsername()
                    + " a ajouté(e) "
                    + arrivalArticle.getQuantity()
                    + " "
                    + arrivalArticle.getArticleType().getArticle().getProductTypeEntity().getName()
                    + " "
                    + arrivalArticle.getArticleType().getColor()
                    + " , taille : "
                    + arrivalArticle.getArticleType().getSize()
                    + "  au stock le "
                    + DateTimeFormatter.format(arrivalArticle.getArrivalDate()));
        }
    }
    public void initialize(ShareArticleEntity shareArticle){
        if (shareArticle.isCanceled()){
            text.setDisable(true);
            text.getStyleClass().add("canceled-text");
            text.setText(shareArticle.getShare().getUser().getAccount().getUsername()
                    + " a annulé(e) le partage de "
                    + shareArticle.getQuantity()
                    + " "
                    + shareArticle.getArticleType().getArticle().getProductTypeEntity().getName()
                    + " "
                    + shareArticle.getArticleType().getColor()
                    + " , taille : "
                    + shareArticle.getArticleType().getSize()
                    + " vers le boutique "
                    + shareArticle.getShare().getShop().getName()
                    + " à "
                    + shareArticle.getShare().getShop().getAddress()
                    + " qui a passé le "
                    + DateTimeFormatter.format(shareArticle.getShareDate()));
        }
        else {
            text.setText(shareArticle.getShare().getUser().getAccount().getUsername()
                    + " a partagé "
                    + shareArticle.getQuantity()
                    + " "
                    + shareArticle.getArticleType().getArticle().getProductTypeEntity().getName()
                    + " "
                    + shareArticle.getArticleType().getColor()
                    + " , taille : "
                    + shareArticle.getArticleType().getSize()
                    + " vers le boutique "
                    + shareArticle.getShare().getShop().getName()
                    + " à " + shareArticle.getShare().getShop().getAddress()
                    + " le " + DateTimeFormatter.format(shareArticle.getShareDate()));
        }
    }
}
