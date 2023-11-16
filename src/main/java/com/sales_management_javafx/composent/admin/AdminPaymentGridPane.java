package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminPaymentBoxController;
import com.sales_management_javafx.controller.arrival.ArrivalBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArrivalEntity;
import org.sales_management.entity.PaymentModeEntity;
import org.sales_management.service.PaymentModeService;
import org.sales_management.service.PaymentService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

public class AdminPaymentGridPane {
    private final GridPane gridPane;
    public AdminPaymentGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(LocalDate localDate) {
        Collection<PaymentModeEntity> paymentModes = new PaymentModeService().getAll();
        for (int i = 0 ; i < paymentModes.size() ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /paymentModes.size());
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (PaymentModeEntity paymentMode : paymentModes) {
            for (Object[] result : new PaymentService().getPaymentsByModeAndDate(paymentMode.getDescription(), localDate)) {
                gridPane.add(getPaymentBox(result), col, row);
            }
            col++;
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getPaymentBox(Object[] result){
        FXMLLoader adminPaymentBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/adminPaymentBox.fxml"));
        StackPane adminBox;
        try {
            adminBox = adminPaymentBoxLoader.load();
            AdminPaymentBoxController adminPaymentBoxController = adminPaymentBoxLoader.getController();
            adminPaymentBoxController.initialize(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return adminBox;
    }
}
