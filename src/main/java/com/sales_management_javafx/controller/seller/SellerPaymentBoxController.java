package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.classes.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.PaymentModeEntity;
import org.sales_management.service.PaymentModeService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class SellerPaymentBoxController implements Initializable {
    @FXML private HBox paymentModeBox;
    @FXML private GridPane paymentBox;
    @FXML private Button remove;
    @FXML private TextField payTextfield;
    private final PaymentModeService paymentModeService;
    private PaymentModeEntity paymentBy;
    private Double pay;
    private SellerPaymentController sellerPaymentController;

    public SellerPaymentBoxController() {
        this.paymentModeService = new PaymentModeService();
        this.paymentBy = new PaymentModeService().getById(1L);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.paymentBy();
        this.updateTotal();
        NumberTextField.requireDouble(payTextfield);
    }
    public void initialize(VBox vBox){
        this.setRemove(vBox);
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
    public PaymentEntity getPayment(){
        PaymentEntity payment = new PaymentEntity();
        try {
            Double pay =  Double.parseDouble(payTextfield.getText());
            payment.setPay(pay);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentMode(paymentBy);
            return payment;
        }
        catch (NumberFormatException e){
            return null;
        }
    }
    public Double getPay(){
        try {
            return Double.parseDouble(payTextfield.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void setRemove(VBox paymentVBox){
        remove.setOnAction(event->{
            paymentVBox.getChildren().remove(paymentBox);
        });
    }
    public void setSellerPaymentController(SellerPaymentController sellerPaymentController){
        this.sellerPaymentController = sellerPaymentController;
    }
    private void updateTotal(){
        payTextfield.textProperty().addListener(event->{
            if (sellerPaymentController != null){
                sellerPaymentController.updateTotal();
            }
        });
    }
}
