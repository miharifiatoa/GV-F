package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.classes.DateTimeFormatter;
import com.sales_management_javafx.composent.admin.AdminArrivalInfoGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArrivalEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminArrivalBoxController implements Initializable {
    @FXML private Label text;
    @FXML private Label arrivalDateLabel;
    @FXML private Label descriptionLabel;
    @FXML private VBox arrivalVBox;
    @FXML private StackPane arrivalBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ArrivalEntity arrival){
        arrivalDateLabel.setText(DateTimeFormatter.format(arrival.getArrivalDate()));
        descriptionLabel.setText(arrival.getDescription());
        if (arrival.isCanceled()){
            text.setDisable(true);
            text.getStyleClass().add("canceled-text");
            text.setText(arrival.getUser().getAccount().getUsername() + " a annulé l'ajout de " + sum(arrival) + " produit(s) qui arrive au stock le : ");
        }
        else {
            text.setText(arrival.getUser().getAccount().getUsername() + " a ajouté " + sum(arrival) + " produit(s) au stock le : ");
        }
        this.setArrivalVBox(arrival);
    }
    private int sum(ArrivalEntity arrival){
        int sum = 0;
        for (ArrivalArticleEntity arrivalArticle : arrival.getArrivalArticles()){
            sum += arrivalArticle.getQuantity();
        }
        return sum;
    }
    private void setArrivalVBox(ArrivalEntity arrival){
        arrivalVBox.setOnMouseClicked(event->{
            GridPane arrivalInfoGridPane = new AdminArrivalInfoGridPane().getGridPane(arrival,2);
            getDashboardLayoutScrollpane().setContent(arrivalInfoGridPane);
        });
    }
    private ScrollPane getDashboardLayoutScrollpane(){
        return (ScrollPane) arrivalBox.getParent().getParent().getParent().getParent();
    }
}
