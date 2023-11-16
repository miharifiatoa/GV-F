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
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductCategoryFormController implements Initializable {
    @FXML
    private VBox articleFormBox;
    @FXML
    private TextField productCategoryNameTextfield;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML private Label nameWarning;
    private final ProductService productService;

    public ProductCategoryFormController() {
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onCancelCreateArticle();
        this.formValidation();
        this.onCreateArticle();
    }
    private void onCancelCreateArticle(){
        exit.setOnAction(event->{
            BorderPane parent = (BorderPane) articleFormBox.getParent();
            parent.setBottom(this.getToolbar());
        });
    }

    public void formValidation(){
        if (this.productCategoryNameTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        productCategoryNameTextfield.textProperty().addListener((observable, oldValue, newValue) -> {
            String categoryName = newValue.trim().toLowerCase();

            if (!categoryName.isEmpty()) {
                ProductEntity existingCategory = productService.isUniqueValue(categoryName);

                if (existingCategory != null) {
                    nameWarning.setText(categoryName + " existe déjà dans la liste de catégories de produits");
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
    private void onCreateArticle(){
        this.save.setOnAction(event->{
            if (this.createArticle()!=null){
                BorderPane parent = (BorderPane) articleFormBox.getParent();
                BorderPane account_layout = (BorderPane) this.articleFormBox.getParent();
                BorderPane account_table_borderpane = (BorderPane) account_layout.getCenter();
                @SuppressWarnings("unchecked")
                TableView<ProductEntity> articleTable = (TableView<ProductEntity>) account_table_borderpane.getCenter();
                articleTable.setItems(FXCollections.observableArrayList(this.productService.getAll()));
                parent.setBottom(this.getToolbar());
            }
        });
    }
    private ProductEntity createArticle(){
        ProductEntity article = new ProductEntity();
        if (!this.productCategoryNameTextfield.getText().isEmpty()){
            article.setName(this.productCategoryNameTextfield.getText());
            return this.productService.create(article);
        }
        else {
            return null;
        }
    }
    private GridPane getToolbar(){
        GridPane toolbarGridpane;
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryToolbar.fxml"));
        try {
            toolbarGridpane = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbarGridpane;
    }
}
