package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeCreateController implements Initializable {
    @FXML
    private StackPane productCreate;
    @FXML
    private Button exitCreateProduct;
    @FXML
    private Button save;
    @FXML
    private TextField productTypeNameTextfield;
    @FXML
    private TextField productTypeReferenceTextfield;
    @FXML
    private TextField productTypeBrandTextfield;
    @FXML
    private Label nameWarning;
    @FXML
    private Label productCategoryNameLabel;

    private final ProductTypeService productTypeService;
    private final ProductService productService;

    public ProductTypeCreateController() {
        this.productTypeService = new ProductTypeService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
        this.setExitCreateProduct();
    }
    public void initialize(ProductEntity productCategory){
        productCategoryNameLabel.setText("Nouveau " + productCategory.getName());
        this.setSave(productCategory);
    }
    private void formValidation(){
        if (productTypeNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productTypeNameTextfield.textProperty().addListener(observable -> {
            if (!productTypeNameTextfield.getText().isEmpty()) {
                nameWarning.setText(null);
                save.setDisable(false);
            } else {
                save.setDisable(true);
                nameWarning.setText("Champ obligatoire");
            }
        });

    }
    private void setExitCreateProduct(){
        exitCreateProduct.setOnAction(event->{
            productCreate.getParent().setVisible(false);
        });
    }
    private void setSave(ProductEntity product){
        save.setOnAction(event->{
            ProductTypeEntity productType = productTypeService.create(getProduct(product));
            if (productType != null){
                StackPane productBoxLayout = (StackPane) productCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productGridpane = new ProductGridPane().getGridPane(new ProductService().getAll(),4);
                productBoxLayoutScrollpane.setContent(productGridpane);
                productCreate.getParent().setVisible(false);
            }
        });
    }
    private ProductTypeEntity getProduct(ProductEntity product){
        ProductTypeEntity productType = new ProductTypeEntity();
        ProductEntity productCategoryPersisted = productService.getById(product.getId());
        productType.setName(productTypeNameTextfield.getText());
        productType.setReference(productTypeReferenceTextfield.getText());
        productType.setBrand(productTypeBrandTextfield.getText());
        productType.setProduct(productCategoryPersisted);
        return productType;
    }
}
