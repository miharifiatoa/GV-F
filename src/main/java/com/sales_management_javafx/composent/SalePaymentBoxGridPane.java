package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.sale.SaleBoxController;
import com.sales_management_javafx.controller.sale.SalePaymentBoxController;
import com.sales_management_javafx.controller.seller.SellerPaymentBoxController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.SaleEntity;

import java.io.IOException;
import java.util.Collection;

public class SalePaymentBoxGridPane {
    private final GridPane gridPane;
    public SalePaymentBoxGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(SaleEntity sale , int colSize) {
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (PaymentEntity payment : sale.getPayments()) {
                gridPane.add(this.getSalePaymentBox(payment), col, row);
                col++;
                if (col == colSize) {
                    col = 0;
                    row++;
                }
        }
        if (!sale.getPayed()){
            gridPane.add(new Button("+"),col,row);
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getSalePaymentBox(PaymentEntity payment){
        FXMLLoader salePaymentBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/salePaymentBox.fxml"));
        StackPane salePaymentBox;
        try {
            salePaymentBox = salePaymentBoxLoader.load();
            SalePaymentBoxController salePaymentBoxController = salePaymentBoxLoader.getController();
            salePaymentBoxController.initialize(payment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return salePaymentBox;
    }
}
