package com.sales_management_javafx.controller.seller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.sales_management.entity.PaymentModeEntity;
import org.sales_management.service.PaymentModeService;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerPaymentController implements Initializable {
    @FXML private HBox paymentModeBox;
    private final PaymentModeService paymentModeService;
    private PaymentModeEntity paymentBy;

    public SellerPaymentController() {
        this.paymentModeService = new PaymentModeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.paymentBy();
    }
    private void paymentBy(){
        ToggleGroup toggleGroup = new ToggleGroup();
        for (PaymentModeEntity paymentMode : paymentModeService.getAll()){
            RadioButton radioButton = new RadioButton(paymentMode.getDescription());
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setOnAction(event->{
                paymentBy = paymentMode;
                System.out.println(paymentBy.getDescription());
            });
            if (paymentMode.getId() == 1){
                radioButton.setSelected(true);
            }
            paymentModeBox.getChildren().add(radioButton);
        }
    }
}
