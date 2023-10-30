package com.sales_management_javafx.controller.sale;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class SaleBoxController implements Initializable {
    @FXML private Label clientNameLabel;
    @FXML private Label saleDateLabel;
    @FXML private Label articleNumbersLabel;
    @FXML private Label descriptionLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(SaleEntity sale){
        clientNameLabel.setText(sale.getClientName());
        saleDateLabel.setText(String.valueOf(sale.getSaleDate()));
        descriptionLabel.setText(sale.getDescription());
        articleNumbersLabel.setText(String.valueOf(getTotalSize(sale)));
    }
    private int getTotalSize(SaleEntity sale){
        int total = 0;

        return total;
    }
}
