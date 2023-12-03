package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.composent.ProductTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeEditController implements Initializable {
    @FXML private VBox productTypeEdit;
    @FXML private Label exit;
    @FXML private Label save;
    @FXML private TextField productTypeReferenceTextfield;
    @FXML private TextField productTypeBrandTextfield;
    @FXML private TextField productTypeNameTextfield;

    private final ProductTypeService productTypeService;

    public ProductTypeEditController() {
        this.productTypeService = new ProductTypeService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTypeEdit.setVisible(false);
        this.setExitCreateProduct();
        this.formValidation();
    }

    private void setExitCreateProduct(){
        exit.setOnMouseClicked(event->{
            productTypeEdit.setVisible(false);
            productTypeEdit.getParent().lookup("#productInfoVBox").setVisible(true);
        });;
    }
    public void initialize(ProductTypeEntity productType){
        productTypeBrandTextfield.setText(productType.getBrand());
        productTypeReferenceTextfield.setText(productType.getReference());
        productTypeNameTextfield.setText(productType.getName());
        this.setSave(productType);
    }
    private void setSave(ProductTypeEntity productType){
        save.setOnMouseClicked(event->{
            ProductTypeEntity productTypePersisted = productTypeService.update(getProductType(productType));
            if (productTypePersisted != null){
                ScrollPane scrollPane = (ScrollPane) productTypeEdit.getParent().getParent().getParent().getParent().getParent();
                GridPane gridPane = new ProductTypeGridPane().getGridPane(new ProductTypeService().getById(productType.getId()).getProduct().getProductTypes(), 4,false);
                scrollPane.setContent(gridPane);
            }
        });
    }
    private ProductTypeEntity getProductType(ProductTypeEntity productType){
        productType.setName(productTypeNameTextfield.getText());
        productType.setReference(productTypeReferenceTextfield.getText());
        productType.setBrand(productTypeBrandTextfield.getText());
        return productType;
    }
    private void formValidation(){
        if (productTypeNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productTypeNameTextfield.textProperty().addListener(event->{
            save.setDisable(productTypeNameTextfield.getText().isEmpty());
        });
    }
}
