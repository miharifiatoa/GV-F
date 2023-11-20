package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.ProductTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.InventoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.InventoryService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeEditController implements Initializable {
    @FXML
    private VBox productTypeEdit;
    @FXML
    private Button exit;
    @FXML
    private Button save;
    @FXML
    private TextField productTypeReferenceTextfield;
    @FXML
    private TextField productTypeBrandTextfield;

    private final InventoryService inventoryService;
    private final ProductTypeService productTypeService;
    private final ProductService productService;

    public ProductTypeEditController() {
        this.inventoryService = new InventoryService();
        this.productTypeService = new ProductTypeService();
        this.productService = new ProductService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExitCreateProduct();
        productTypeEdit.setVisible(false);
    }

    private void setExitCreateProduct(){
        exit.setOnAction(event->{
            productTypeEdit.setVisible(false);
            productTypeEdit.getParent().lookup("#productInfoVBox").setVisible(true);
        });;
    }
    public void initialize(ProductTypeEntity productType){
        productTypeBrandTextfield.setText(productType.getBrand());
        productTypeReferenceTextfield.setText(productType.getReference());
        this.setSave(productType);
    }
    private void setSave(ProductTypeEntity productType){
        save.setOnAction(event->{
            ProductTypeEntity productTypePersisted = productTypeService.update(getProductType(productType));
            if (productTypePersisted != null){
                ScrollPane scrollPane = (ScrollPane) productTypeEdit.getParent().getParent().getParent().getParent().getParent();
                GridPane gridPane = new ProductTypeGridPane().getGridPane(new ProductTypeService().getById(productType.getId()).getProduct().getProductTypes(), 4,false);
                scrollPane.setContent(gridPane);
            }
        });
    }
    private ProductTypeEntity getProductType(ProductTypeEntity productType){
        productType.setReference(productTypeReferenceTextfield.getText());
        productType.setBrand(productTypeBrandTextfield.getText());
        return productType;
    }
}
