package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.composent.ProductCategoryGridPane;
import com.sales_management_javafx.composent.ProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductEditController implements Initializable {
    @FXML private VBox editVBox;
    @FXML private TextField productNameTextField;
    @FXML private Label exit;
    @FXML private Label save;
    private final ProductService productService;

    public ProductEditController() {
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
        this.formValidation();
        this.editVBox.setVisible(false);
    }
    public void initialize(ProductEntity product){
        productNameTextField.setText(product.getName());
        this.setSave(product);
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            editVBox.setVisible(false);
            editVBox.getParent().lookup("#productVBox").setVisible(true);
        });
    }
    private void setSave(ProductEntity product){
        save.setOnMouseClicked(event->{
            ProductEntity productEntity = productService.getById(product.getId());
            if (productEntity != null){
                productEntity.setName(productNameTextField.getText());
                if (productService.update(productEntity) != null){
                    GridPane gridPane = new ProductGridPane().getGridPane(productService.getById(product.getId()).getProductCategory().getProducts(), 4);
                    ScrollPane productBoxLayoutScrollPane = (ScrollPane) editVBox.getParent().getParent().getParent().getParent().getParent();
                    productBoxLayoutScrollPane.setContent(gridPane);
                }
            }
        });
    }
    private void formValidation(){
        if (productNameTextField.getText().isEmpty()){
            save.setDisable(true);
        }
        productNameTextField.textProperty().addListener(event->{
            save.setDisable(productNameTextField.getText().isEmpty());
        });
    }
}
