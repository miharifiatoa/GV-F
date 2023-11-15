package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ProductCategoryGridPane;
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
import org.sales_management.entity.InventoryEntity;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.InventoryService;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;

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
    private TextField productNameTextfield;
    @FXML
    private TextField productReferenceTextfield;
    @FXML
    private TextField productQualityTextfield;
    @FXML
    private TextField productBrandTextfield;
    @FXML
    private Label identifyWarning;
    @FXML
    private Label nameWarning;
    @FXML
    private Label productCategoryNameLabel;

    private final InventoryService inventoryService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public ProductCreateController() {
        this.inventoryService = new InventoryService();
        this.productService = new ProductService();
        this.productCategoryService = new ProductCategoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
        this.setExitCreateProduct();
    }
    public void initialize(ProductCategoryEntity productCategory){
        productCategoryNameLabel.setText("Nouveau " + productCategory.getName());
        this.setSave(productCategory);
    }
    private void formValidation(){
        if (productNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productNameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            String productName = newValue.trim().toLowerCase();
            if (!productNameTextfield.getText().isEmpty()) {
                ProductEntity existingProduct = productService.isProductNameExists(productName);
                if (existingProduct != null) {
                    nameWarning.setText(productName + " existe déjà dans la liste de " + existingProduct.getProductCategory().getName());
                    save.setDisable(true);
                } else {
                    nameWarning.setText(null);
                    save.setDisable(false);
                }
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
    private void setSave(ProductCategoryEntity productCategory){
        save.setOnAction(event->{
            ProductEntity product = productService.create(getProduct(productCategory));
            if (product != null){
                StackPane productBoxLayout = (StackPane) productCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productCategoryGridpane = new ProductCategoryGridPane().getGridPane(new ProductCategoryService().getAll(),4);
                productBoxLayoutScrollpane.setContent(productCategoryGridpane);
                productCreate.getParent().setVisible(false);
            }
        });
    }
    private ProductEntity getProduct(ProductCategoryEntity productCategory){
        ProductEntity product = new ProductEntity();
        InventoryEntity inventory = inventoryService.getById(1L);
        ProductCategoryEntity productCategoryPersisted = productCategoryService.getById(productCategory.getId());
        product.setName(productNameTextfield.getText());
        product.setInventory(inventory);
        product.setProductCategory(productCategoryPersisted);
        return product;
    }
}
