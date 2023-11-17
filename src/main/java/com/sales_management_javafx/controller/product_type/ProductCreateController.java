package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.composent.ProductCategoryGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.InventoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.InventoryService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCreateController implements Initializable {
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
    private Label identifyWarning;
    @FXML
    private Label nameWarning;
    @FXML
    private Label productCategoryNameLabel;

    private final InventoryService inventoryService;
    private final ProductTypeService productTypeService;
    private final ProductService productService;

    public ProductCreateController() {
        this.inventoryService = new InventoryService();
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
        productTypeNameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            String productName = newValue.trim().toLowerCase();
            if (!productTypeNameTextfield.getText().isEmpty()) {
                ProductTypeEntity existingProduct = productTypeService.isProductNameExists(productName);
                if (existingProduct != null) {
                    nameWarning.setText(productName + " existe déjà dans la liste de " + existingProduct.getProduct().getName());
                    save.setDisable(true);
                } else {
                    nameWarning.setText(null);
                    save.setDisable(false);
                }
            } else {
                save.setDisable(true);
                nameWarning.setText(null);
            }
        });

    }
    private void setExitCreateProduct(){
        exitCreateProduct.setOnAction(event->{
            productCreate.getParent().setVisible(false);
        });
    }
    private void setSave(ProductEntity productCategory){
        save.setOnAction(event->{
            ProductTypeEntity product = productTypeService.create(getProduct(productCategory));
            if (product != null){
                StackPane productBoxLayout = (StackPane) productCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productCategoryGridpane = new ProductCategoryGridPane().getGridPane(new ProductService().getAll(),4);
                productBoxLayoutScrollpane.setContent(productCategoryGridpane);
                productCreate.getParent().setVisible(false);
            }
        });
    }
    private ProductTypeEntity getProduct(ProductEntity product){
        ProductTypeEntity productType = new ProductTypeEntity();
        InventoryEntity inventory = inventoryService.getById(1L);
        ProductEntity productCategoryPersisted = productService.getById(product.getId());
        productType.setName(productTypeNameTextfield.getText());
        productType.setReference(productTypeReferenceTextfield.getText());
        productType.setBrand(productTypeBrandTextfield.getText());
        productType.setInventory(inventory);
        productType.setProduct(productCategoryPersisted);
        return productType;
    }
}
