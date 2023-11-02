package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.admin.AdminArrivalBoxController;
import com.sales_management_javafx.controller.sale.FactureArticleBoxController;
import com.sales_management_javafx.controller.sale.FactureInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.ArrivalEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.io.IOException;
import java.util.Collection;

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
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()) {
            gridPane.add(this.getFactureBox(saleArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.add(this.getFactureInfo(sale),col,row);
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
    private VBox getFactureInfo(SaleEntity sale){
        FXMLLoader factureInfoLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/factureInfo.fxml"));
        VBox factureInfo;
        try {
            factureInfo = factureInfoLoader.load();
            FactureInfoController factureInfoController = factureInfoLoader.getController();
            factureInfoController.initialize(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return factureInfo;
    }
}
