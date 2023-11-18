package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.classes.DecimalFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.PaymentModeEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class SalePaymentBoxController implements Initializable {
    @FXML private Label payLabel;
    @FXML private Label paymentModeLabel;
    @FXML private Label paymentDateLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(PaymentEntity payment){
        payLabel.setText(DecimalFormat.format(payment.getPay()) + "Ar");
        paymentModeLabel.setText(payment.getPaymentMode().getDescription());
        paymentDateLabel.setText(String.valueOf(payment.getPaymentDate().toLocalDate()));
    }
}
