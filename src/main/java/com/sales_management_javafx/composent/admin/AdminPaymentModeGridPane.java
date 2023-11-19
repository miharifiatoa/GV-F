package com.sales_management_javafx.composent.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.PaymentModeBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Collection;
import org.sales_management.entity.PaymentModeEntity;

public class AdminPaymentModeGridPane {
    private final GridPane gridPane;

    public AdminPaymentModeGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<PaymentModeEntity> modes, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (PaymentModeEntity mode : modes) {
            gridPane.add(this.getAdminPaymentModeBox(mode), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getAdminPaymentModeBox(PaymentModeEntity mode){
        FXMLLoader paymentModeBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/paymentModeBox.fxml"));
        StackPane payModeBox;
        try {
            payModeBox = paymentModeBoxLoader.load();
            PaymentModeBoxController paymentModeBoxController = paymentModeBoxLoader.getController();
            paymentModeBoxController.initialize(mode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return payModeBox;
    }
}
