package com.sales_management_javafx.controller.product;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.sales_management_javafx.composent.ProductCategoryGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.StackPane;

public class ProductCreateController implements Initializable {
    @FXML
    private StackPane productCreate;
    @FXML
    private TextField productNameTextfield;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML private Label productCategoryLabel;
    @FXML private Label nameWarning;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public ProductCreateController() {
        this.productCategoryService = new ProductCategoryService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
        this.formValidation();
    }
    public void initialize(ProductCategoryEntity productCategory){
        productCategoryLabel.setText("Nouveau " + productCategory.getName());
        this.setSave(productCategory);
    }
    private void setExit(){
        exit.setOnAction(event->{
            BorderPane modal = (BorderPane) productCreate.getParent();
            modal.setVisible(false);
        });
    }

    public void formValidation(){
        if (this.productNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productNameTextfield.textProperty().addListener(observable -> {
            if (!productNameTextfield.getText().isEmpty()) {
                nameWarning.setText(null);
                save.setDisable(false);
            } else {
                nameWarning.setText("Champ obligatoire");
                save.setDisable(true);
            }
        });

    }
    private void setSave(ProductCategoryEntity productCategory){
        save.setOnAction(event->{
            ProductEntity product = new ProductEntity();
            product.setProductCategory(productCategory);
            product.setName(productNameTextfield.getText());
            if (productService.create(product) != null){
                StackPane productBoxLayout = (StackPane) productCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productCategoryGridPane = new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(), 4);
                productBoxLayoutScrollpane.setContent(productCategoryGridPane);
                productCreate.getParent().setVisible(false);
            }
        });
    }
}
