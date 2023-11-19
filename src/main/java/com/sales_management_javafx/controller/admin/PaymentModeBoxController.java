package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.admin.AdminPaymentModeGridPane;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.sales_management.entity.PaymentModeEntity;
import org.sales_management.service.PaymentModeService;

public class PaymentModeBoxController implements Initializable {
    @FXML
    private GridPane payGrid;
    @FXML
    private GridPane confirm;
    @FXML
    private Button non;
    @FXML
    private Button oui;
    @FXML
    private Label NamePaymentLabel;
    @FXML
    private Label del;
    @FXML
    private StackPane paymentModeBoxStackPane;
    private final PaymentModeService paymentModeService;
    private final AdminPaymentModeGridPane adminPaymentModeGridPane;

    public PaymentModeBoxController() {
        this.adminPaymentModeGridPane = new AdminPaymentModeGridPane();
        this.paymentModeService = new PaymentModeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(PaymentModeEntity mode){
        if (!mode.getPayments().isEmpty()){
            del.setDisable(true);
        }
        NamePaymentLabel.setText(mode.getDescription());
        onClickX();
        onClickNon();
        onClickOui(mode);
    }
    
    private void onClickX(){
        del.setOnMouseClicked(event1 -> {
            payGrid.setVisible(false);
            confirm.setVisible(true);
        });
    }
    
    private void onClickNon(){
        non.setOnMouseClicked(event2 -> {
            payGrid.setVisible(true);
            confirm.setVisible(false);
        });
    }
    
    private void onClickOui(PaymentModeEntity mode){
        oui.setOnMouseClicked(event1 -> {
            PaymentModeEntity dropedMode = this.paymentModeService.deleteById(mode.getId());
                if(dropedMode.getId()>0){
                    FXMLLoader paymentModeLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/PaymentLayout.fxml"));
                    BorderPane paymentModeLayout;
                    try {
                        paymentModeLayout = paymentModeLayoutLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    BorderPane dashboardLayout = (BorderPane) paymentModeBoxStackPane.getParent().getParent().getParent().getParent().getParent().getParent();
                    dashboardLayout.setBottom(paymentModeLayout); 
                }
        });
    }
}
