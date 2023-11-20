package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import java.net.URL;
import java.util.ResourceBundle;

import com.sales_management_javafx.classes.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.sales_management.entity.StockHistoryEntity;

public class historyBoxController implements Initializable {
    @FXML Label action;
    @FXML Label description;
    @FXML Label date;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void initialize(StockHistoryEntity history){
        action.setText(history.getAction());
        description.setText(history.getDescription());
        date.setText(DateTimeFormatter.format(history.getDate()));
        
    }
}