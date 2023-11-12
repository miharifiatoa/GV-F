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
                    + " produit(s) au client "
                    + saleArticle.getSale().getClient().getName()
                    + " qui a passé le "
                    + DateTimeFormatter.format(saleArticle.getSaleDate()));
        }
        else{
            text.setText(saleArticle.getSale().getUser().getAccount().getUsername()
                    + " a vendu " + saleArticle.getQuantity()
                    + " produit(s) au client "
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
                    + " a annulé l' ajout de "
                    + arrivalArticle.getQuantity()
                    + " produit(s) qui arrive au stock le "
                    + DateTimeFormatter.format(arrivalArticle.getArrivalDate()));
        }
        else {
            text.setText(arrivalArticle.getArrival().getUser().getAccount().getUsername()
                    + " a ajouté "
                    + arrivalArticle.getQuantity()
                    + " produit(s) au stock le "
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
                    + " produit(s) vers le boutique "
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
                    + " produit(s) vers le boutique "
                    + shareArticle.getShare().getShop().getName()
                    + " à " + shareArticle.getShare().getShop().getAddress()
                    + " le " + DateTimeFormatter.format(shareArticle.getShareDate()));
        }
    }
}
