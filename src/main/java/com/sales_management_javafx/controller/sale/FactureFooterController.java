package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.actions.Payment;
import com.sales_management_javafx.classes.DecimalFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class FactureFooterController implements Initializable {
    @FXML private Label sum;
    @FXML private Label isPayedLabel;
    @FXML private Label date;
    @FXML private Label rest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(SaleEntity sale){
        sum.setText(DecimalFormat.format(Payment.getTotalToPay(sale) - sale.getDelivery()) + "Ar");
        rest.setText(DecimalFormat.format((Payment.getTotalToPay(sale) - sale.getDelivery()) - Payment.getPayed(sale)) + "Ar");
        date.setText(sale.getSaleDate().toLocalDate() + " à " + sale.getSaleDate().toLocalTime());
        if (sale.getPayed()){
            isPayedLabel.setText("payé");
        }
        else {
            isPayedLabel.setText("non payé");
        }
    }
}
