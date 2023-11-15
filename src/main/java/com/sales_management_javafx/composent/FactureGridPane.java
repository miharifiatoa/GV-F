package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.sale.SalePaymentBoxGridPane;
import com.sales_management_javafx.controller.sale.FactureArticleBoxController;
import com.sales_management_javafx.controller.sale.FactureFooterController;
import com.sales_management_javafx.controller.sale.FactureHeaderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.io.IOException;

public class FactureGridPane {
    private final GridPane gridPane;
    public FactureGridPane() {
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
        gridPane.add(this.getFactureHeader(sale),col,row);
        row++;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()) {
            gridPane.add(this.getFactureBox(saleArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.add(new SalePaymentBoxGridPane().getGridPane(sale,1),col,row);
        row++;
        gridPane.add(this.getFactureFooter(sale),col,row);
        gridPane.getStyleClass().add("box");
        gridPane.setId("product-type-grid-pane");
        return gridPane;
    }
    private StackPane getFactureBox(SaleArticleEntity saleArticle){
        FXMLLoader factureBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/factureArticleBox.fxml"));
        StackPane factureBox;
        try {
            factureBox = factureBoxLoader.load();
            FactureArticleBoxController factureArticleBoxController = factureBoxLoader.getController();
            factureArticleBoxController.initialize(saleArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return factureBox;
    }
    private VBox getFactureFooter(SaleEntity sale){
        FXMLLoader factureInfoLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/factureFooter.fxml"));
        VBox factureInfo;
        try {
            factureInfo = factureInfoLoader.load();
            FactureFooterController factureFooterController = factureInfoLoader.getController();
            factureFooterController.initialize(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return factureInfo;
    }
    private StackPane getFactureHeader(SaleEntity sale){
        FXMLLoader factureHeaderLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/factureHeader.fxml"));
        StackPane factureHeader;
        try {
            factureHeader = factureHeaderLoader.load();
            FactureHeaderController factureHeaderController = factureHeaderLoader.getController();
            factureHeaderController.initialize(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return factureHeader;
    }
}
