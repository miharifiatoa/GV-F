package com.sales_management_javafx.controller.sale;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class FactureFooterController implements Initializable {
    @FXML private Label sum;
    @FXML private Label isPayedLabel;
    @FXML private Label date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(SaleEntity sale){
        sum.setText(this.getSum(sale) + "Ar");
        date.setText(sale.getSaleDate().toLocalDate() + " à " + sale.getSaleDate().toLocalTime());
        if (sale.getPayed()){
            isPayedLabel.setText("payé");
        }
        else {
            isPayedLabel.setText("non payé");
        }
    }

    private String getSum(SaleEntity sale){
        double sum = 0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
            sum += saleArticle.getArticle().getPrice() * saleArticle.getQuantity();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(sum);
    }
}
