package com.sales_management_javafx.controller.product_category;

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
import org.sales_management.service.ProductCategoryService;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.StackPane;

public class ProductCategoryCreateController implements Initializable {
    @FXML
    private StackPane productCategoryCreate;
    @FXML
    private TextField productCategoryNameTextfield;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML private Label productCategoryLabel;
    @FXML private Label nameWarning;
    private final ProductCategoryService productCategoryService;

    public ProductCategoryCreateController() {
        this.productCategoryService = new ProductCategoryService();
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
                productCategoryLabel.setText("Nouveau " + productCategoryNameTextfield.getText());
                this.formValidation();
                this.setSave();
    }
    private void setExit(){
        exit.setOnAction(event->{
            BorderPane modal = (BorderPane) productCategoryCreate.getParent();
            modal.setVisible(false);
        });
    }

    public void formValidation(){
        if (this.productCategoryNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productCategoryNameTextfield.textProperty().addListener(observable -> {
            if (!productCategoryNameTextfield.getText().isEmpty()) {
                nameWarning.setText(null);
                save.setDisable(false);
            } else {
                nameWarning.setText("Champ obligatoire");
                save.setDisable(true);
            }
        });

    }
    private void setSave(){
        save.setOnAction(event->{
            ProductCategoryEntity newProductCategory = new ProductCategoryEntity();
            newProductCategory.setName(productCategoryNameTextfield.getText());
            if (productCategoryService.create(newProductCategory) != null){
                StackPane productBoxLayout = (StackPane) productCategoryCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productCategoryGridPane = new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(), 4);
                productBoxLayoutScrollpane.setContent(productCategoryGridPane);
                productCategoryCreate.getParent().setVisible(false);
            }
        });
    }
}
