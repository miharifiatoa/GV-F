package com.sales_management_javafx.controller.product_category;

import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.ProductCategoryGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductCategoryService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductCategoryEditController implements Initializable {
    @FXML private VBox editVBox;
    @FXML private TextField productCategoryNameTextField;
    @FXML private Label exit;
    @FXML private Label save;
    private final ProductCategoryService productCategoryService;

    public ProductCategoryEditController() {
        this.productCategoryService = new ProductCategoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
        this.formValidation();
        this.editVBox.setVisible(false);
    }
    public void initialize(ProductCategoryEntity productCategory){
        productCategoryNameTextField.setText(productCategory.getName());
        this.setSave(productCategory);
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            editVBox.setVisible(false);
            editVBox.getParent().lookup("#productCategoryVBox").setVisible(true);
        });
    }
    private void setSave(ProductCategoryEntity productCategory){
        save.setOnMouseClicked(event->{
            ProductCategoryEntity productCategoryEntity = productCategoryService.getById(productCategory.getId());
            if (productCategoryEntity != null){
                productCategoryEntity.setName(productCategoryNameTextField.getText());
                if (productCategoryService.update(productCategoryEntity) != null){
                    GridPane gridPane = new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(), 4);
                    ScrollPane productBoxLayoutScrollPane = (ScrollPane) editVBox.getParent().getParent().getParent().getParent().getParent();
                    productBoxLayoutScrollPane.setContent(gridPane);
                }
            }
        });
    }
    private void formValidation(){
        if (productCategoryNameTextField.getText().isEmpty()){
            save.setDisable(true);
        }
        productCategoryNameTextField.textProperty().addListener(event->{
            save.setDisable(productCategoryNameTextField.getText().isEmpty());
        });
    }
}
