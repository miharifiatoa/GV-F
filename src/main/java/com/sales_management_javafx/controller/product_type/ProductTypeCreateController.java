package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeCreateController implements Initializable {
    @FXML private StackPane productTypeCreate;
    @FXML private TextField productTypeNameTextfield;
    @FXML private TextField productTypeReferenceTextfield;
    @FXML private TextField productTypeBrandTextfield;
    @FXML private TextField productTypeQualityTextfield;
    @FXML private Button save;
    @FXML private Button exit;
    @FXML private Label productNameLabel;
    @FXML private Label nameWarning;
    private final ProductTypeService productTypeService;

    public ProductTypeCreateController() {
        this.productTypeService = new ProductTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        formValidation();
    }
    public void initialize(ProductEntity product){
        productNameLabel.setText("Nouveau type du produit " + product.getName());
        this.setSave(product);
        this.setExit();
    }
    private void setSave(ProductEntity product){
        save.setOnAction(event->{
            if (this.productTypeService.create(getProductType(product)) != null){
                StackPane productBoxLayout = (StackPane) productTypeCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productGridPane = new ProductGridPane().getGridPane(new ProductService().getById(product.getId()).getProductCategory().getProducts(), 4,false);
                productBoxLayoutScrollpane.setContent(productGridPane);
                productTypeCreate.getParent().setVisible(false);
            }
        });

    }
    private void setExit(){
        exit.setOnAction(event->{
            BorderPane modal = (BorderPane) productTypeCreate.getParent();
            modal.setVisible(false);
        });
    }
    private ProductTypeEntity getProductType(ProductEntity product){
        ProductTypeEntity productType = new ProductTypeEntity();
        productType.setName(productTypeNameTextfield.getText());
        productType.setReference(productTypeReferenceTextfield.getText());
        productType.setBrand(productTypeBrandTextfield.getText());
        productType.setQuality(productTypeQualityTextfield.getText());
        productType.setProduct(product);
        return productType;
    }
    private void formValidation(){
        if (productTypeNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productTypeNameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            String typeName = newValue.trim().toLowerCase();
            if (!productTypeNameTextfield.getText().isEmpty()) {
                ProductTypeEntity existingProductType = productTypeService.isUniqueValue(typeName);
                if (existingProductType != null) {
                    nameWarning.setText(typeName + " existe déjà dans le type de produit " + existingProductType.getProduct().getName());
                    save.setDisable(true);
                } else {
                    nameWarning.setText(null);
                    save.setDisable(false);
                }
            } else {
                nameWarning.setText("Champ obligatoire");
                save.setDisable(true);
            }
        });

    }
}
