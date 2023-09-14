package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.product.ProductBoxController;
import com.sales_management_javafx.controller.product.ProductEditFormController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.sales_management.entity.ProductEntity;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class ProductGridPane extends GridPane {
    private final GridPane gridPane;

    public ProductGridPane() {
        this.gridPane = new GridPane();
    }
    public GridPane getGridPane(Collection<ProductEntity> products,int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (ProductEntity product : products) {
            gridPane.add(this.getProductBox(product), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.add(this.getProductCreateFormBox(), col, row);
        return gridPane;
    }
    public StackPane getProductBox(ProductEntity product){
        FXMLLoader productBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBox.fxml"));
        StackPane productBoxStackpane;
        try {
            productBoxStackpane = productBoxLoader.load();
            ProductBoxController productBoxController = productBoxLoader.getController();
            productBoxController.initializeProductData(product);
            productBoxController.onConfirmDeleteProduct(product.getId());
            productBoxController.onConfirmAddProduct(product);
            productBoxStackpane.getChildren().add(this.getProductEditBox(product));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxStackpane;
    }
    public StackPane getProductCreateFormBox(){
        FXMLLoader productFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productCreateForm.fxml"));
        StackPane productFormBox;
        try {
            productFormBox = productFormLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productFormBox;
    }
    public VBox getProductEditBox(ProductEntity product){
        FXMLLoader productEditFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productEditForm.fxml"));
        VBox productEditForm;
        try {
            productEditForm = productEditFormLoader.load();
            ProductEditFormController productEditFormController = productEditFormLoader.getController();
            productEditFormController.initializeForm(product);
            productEditFormController.onConfirmEditProduct(product.getId());
            productEditForm.setVisible(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productEditForm;
    }
}
