package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.classes.DecimalFormat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminPaymentBoxController implements Initializable {
    @FXML private Label descriptionLabel;
    @FXML private Label paymentLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(Object[] result){
        descriptionLabel.setText(result[0].toString());
        paymentLabel.setText(DecimalFormat.format((Double) result[1]) + "Ar");
    }
}
