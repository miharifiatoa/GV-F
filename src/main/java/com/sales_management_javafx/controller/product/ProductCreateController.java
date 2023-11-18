package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductCreateController implements Initializable {
    @FXML
    private VBox articleCreate;
    @FXML
    private TextField productNameTextfield;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML private Label productCategoryLabel;
    @FXML private Label nameWarning;
    private final ProductService productService;

    public ProductCreateController() {
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
    }
    public void initialize(ProductCategoryEntity productCategory){
        productCategoryLabel.setText("Nouveau " + productCategory.getName());
    }
    private void onCancelCreateArticle(){
        exit.setOnAction(event->{
            BorderPane parent = (BorderPane) articleCreate.getParent();
        });
    }

    public void formValidation(){
        if (this.productNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productNameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            String productName = newValue.trim().toLowerCase();

            if (!productName.isEmpty()) {
                ProductEntity existingProduct = productService.isUniqueValue(productName);

                if (existingProduct != null) {
                    nameWarning.setText(productName + " existe déjà dans la liste de catégories de produits");
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
    private ProductEntity createProduct(){
        ProductEntity product = new ProductEntity();
        if (!this.productNameTextfield.getText().isEmpty()){
            product.setName(this.productNameTextfield.getText());
            return this.productService.create(product);
        }
        else {
            return null;
        }
    }
}
