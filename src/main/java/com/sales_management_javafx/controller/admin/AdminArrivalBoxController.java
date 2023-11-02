package com.sales_management_javafx.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
        text.setText("Mihaja a ajouté ");
        arrivalDateLabel.setText(String.valueOf(arrival.getArrivalDate()));
        descriptionLabel.setText(arrival.getDescription());
    }
}
