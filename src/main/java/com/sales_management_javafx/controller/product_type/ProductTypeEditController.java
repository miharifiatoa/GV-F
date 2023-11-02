package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.composent.ProductTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeEditController implements Initializable {
    @FXML private VBox productTypeEditVBox;
    @FXML private TextField productTypeReferenceTextfield;
    @FXML private TextField productTypeBrandTextfield;
    @FXML private TextField productTypeQualityTextfield;
    @FXML private Button exit;
    @FXML private Button save;
    private final ProductTypeService productTypeService;

    public ProductTypeEditController() {
        this.productTypeService = new ProductTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
    }
    public void initialize(ProductTypeEntity productType){
        productTypeReferenceTextfield.setText(productType.getReference());
        productTypeBrandTextfield.setText(productType.getBrand());
        productTypeQualityTextfield.setText(productType.getQuality());
        this.setSave(productType);
    }
    private void setExit(){
        exit.setOnAction(event->{
            productTypeEditVBox.setVisible(false);
        });
    }
    private void setSave(ProductTypeEntity productType){
        save.setOnAction(event->{
            ProductTypeEntity productTypeEntity = productTypeService.getById(productType.getId());
            if (productTypeEntity != null){
                productTypeEntity.setReference(productTypeReferenceTextfield.getText());
                productTypeEntity.setBrand(productTypeBrandTextfield.getText());
                productTypeEntity.setQuality(productTypeQualityTextfield.getText());
                if (productTypeService.update(productTypeEntity) != null){
                    GridPane productTypeGridPane = new ProductTypeGridPane().getGridPane(new ProductTypeService().getById(productType.getId()).getProduct().getProductTypes(), 4);
                    ScrollPane productBoxLayoutScrollPane = (ScrollPane) productTypeEditVBox.getParent().getParent().getParent().getParent().getParent();
                    productBoxLayoutScrollPane.setContent(productTypeGridPane);
                }
            }
        });
    }
}
