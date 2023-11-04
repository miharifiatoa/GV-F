package com.sales_management_javafx.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArrivalEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminArrivalBoxController implements Initializable {
    @FXML private Label text;
    @FXML private Label arrivalDateLabel;
    @FXML private Label descriptionLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArrivalEntity arrival){
        text.setText(arrival.getUser().getAccount().getUsername() + " a ajouté " + sum(arrival) + " produits au stock");
        arrivalDateLabel.setText(String.valueOf(arrival.getArrivalDate()));
        descriptionLabel.setText(arrival.getDescription());
    }
    private int sum(ArrivalEntity arrival){
        int sum = 0;
        for (ArrivalArticleEntity arrivalArticle : arrival.getArrivalArticles()){
            sum += arrivalArticle.getQuantity();
        }
        return sum;
    }
}
