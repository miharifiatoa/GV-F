package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.admin.AdminPaymentModeGridPane;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.PaymentModeEntity;
import org.sales_management.service.PaymentModeService;

public class PaymentModeLayoutController implements Initializable {
    @FXML private BorderPane modePaymentBorderPane;
    @FXML private ImageView MoneyIcon;
    @FXML private ScrollPane modePaymentScroll;
    @FXML private TextField newMode;
    @FXML private Button confirm;
    @FXML private Button exit;
    private final PaymentModeService paymentModeService;
    public PaymentModeLayoutController(){
        this.paymentModeService = new PaymentModeService();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newMode.textProperty().addListener((observable, oldValue, newValue) -> {
        validation();
        });
        this.exit.setOnAction(event->this.setExit());
        onCreateMode();
        putIcons();
        setPayLayout();
    }
    
    private void validation(){
        boolean isNewModeFilled = newMode.getText().isEmpty();
        confirm.setDisable(isNewModeFilled);
    }
    
    private void onCreateMode(){
        confirm.setOnAction(event -> {
            if(!newMode.getText().isEmpty()){
                
                PaymentModeEntity mode = new PaymentModeEntity();
                mode.setDescription(newMode.getText());
                this.paymentModeService.create(mode);
                if(mode.getId()>0){
                    setPayLayout();
                    newMode.setText("");
                }
            }
        });
    }
    
    private void setPayLayout(){
        GridPane gridPane = new AdminPaymentModeGridPane().getGridPane(paymentModeService.getAll(),4);
        modePaymentScroll.setContent(gridPane);
    }
    
    private void setExit(){
        BorderPane borderPane = (BorderPane) this.modePaymentBorderPane.getParent();
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPane.setBottom(dashboardToolbar);
    
    }
    
    private void putIcons(){
        MoneyIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/MoneyIcon.png"))));
    }
}
