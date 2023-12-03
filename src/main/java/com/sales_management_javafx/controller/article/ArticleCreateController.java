package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.composent.ProductTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ProductTypeService;
import org.sales_management.service.ArticleService;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleCreateController implements Initializable {
    @FXML private StackPane productTypeCreate;
    @FXML private TextField articleCode;
    @FXML private TextField articlePrice;
    @FXML private Button save;
    @FXML private Button exit;
    @FXML private Label productNameLabel;
    @FXML private Label nameWarning;
    private final ArticleService articleService;

    public ArticleCreateController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ProductTypeEntity productType){
        productNameLabel.setText("Nouveau " + productType.getName());
        this.setSave(productType);
        this.setExit();
        this.formValidation(productType);
    }
    private void setSave(ProductTypeEntity productType){
        save.setOnAction(event->{
            if (this.articleService.create(getProductType(productType)) != null){
                StackPane productBoxLayout = (StackPane) productTypeCreate.getParent().getParent();
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane productGridPane = new ProductTypeGridPane().getGridPane(new ProductTypeService().getAll(), 4,false);
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
    private ArticleEntity getProductType(ProductTypeEntity productType){
        ArticleEntity article = new ArticleEntity();
        article.setPrice(Double.valueOf(articlePrice.getText()));
        article.setCode(articleCode.getText());
        article.setProductTypeEntity(productType);
        return article;
    }
    private void formValidation(ProductTypeEntity productType){
        if (this.articlePrice.getText().isEmpty() || this.articleCode.getText().isEmpty()){
            this.save.setDisable(true);
        }
        this.articleCode.textProperty().addListener((event,oldValue, newValue)->{
            if (articlePrice.getText().isEmpty() || articleCode.getText().isEmpty()){
                save.setDisable(true);
            }
            else {
                String code = newValue.trim().toLowerCase();
                if(articleService.isUniqueValue(code) != null){
                    save.setDisable(true);
                    nameWarning.setText(newValue + " existe deja dans le type de produit " + productType.getName());
                }
                else {
                    save.setDisable(false);
                    nameWarning.setText(null);
                }
            }
        });
        this.articlePrice.textProperty().addListener((event,oldValue, newValue)->{
            if (articlePrice.getText().isEmpty() || articleCode.getText().isEmpty()){
                save.setDisable(true);
            }
            else {
                String code = articleCode.getText().trim().toLowerCase();
                if(articleService.isUniqueValue(code) != null){
                    save.setDisable(true);
                    nameWarning.setText(articleCode.getText() + " existe deja dans le type de produit " + productType.getName());
                }
                else {
                    save.setDisable(false);
                    nameWarning.setText(null);
                }
            }
        });
    }
}
