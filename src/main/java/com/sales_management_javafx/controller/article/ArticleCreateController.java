package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.ProductTypeGridPane;
import com.sales_management_javafx.composent.StockistArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ArticleCreateController implements Initializable {
    @FXML
    private TextField articlePriceTextfield;
    @FXML
    private TextField articleSizeTextfield;
    @FXML
    private TextField articleCodeTextfield;
    @FXML
    private TextField articleColorTextfield;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML
    private ScrollPane articleCreateScrollpane;
    @FXML
    private StackPane articleCreate;
    @FXML
    private VBox articleCreateVBox;
    @FXML
    private Label productTypeLabel;
    @FXML private Label identifyWarning;
    private final ProductTypeService productTypeService;
    private final ArticleService articleService;

    public ArticleCreateController() {
        this.productTypeService = new ProductTypeService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formValidation();
        this.setExit();
        NumberTextField.requireDouble(this.articlePriceTextfield);
    }
    public void initialize(ProductTypeEntity productType){
        this.productTypeLabel.setText("Nouveau " + productType.getName());
        this.setSave(productType);
    }
    private void formValidation(){
        if (this.articlePriceTextfield.getText().isEmpty() || this.articleCodeTextfield.getText().isEmpty()){
            this.save.setDisable(true);
        }
        this.articleCodeTextfield.textProperty().addListener((event,oldValue, newValue)->{
            String code = newValue.trim().toLowerCase();
            if (!articleCodeTextfield.getText().isEmpty()){
                ArticleEntity existedArticle =  articleService.isUniqueValue(code);
                if (existedArticle != null){
                    save.setDisable(true);
                    identifyWarning.setText(articleCodeTextfield.getText() + " existe deja dans la type de produit " + existedArticle.getProductType().getName());
                }
                else {
                    save.setDisable(false);
                    identifyWarning.setText(null);
                }
                if (articlePriceTextfield.getText().isEmpty()){
                    save.setDisable(true);
                }
            }
            else{
                save.setDisable(true);
                identifyWarning.setText("Champ obligatoire");
            }
        });
        articlePriceTextfield.textProperty().addListener(event->{
            if (!articlePriceTextfield.getText().isEmpty()){
                if (articleService.isUniqueValue(articleCodeTextfield.getText()) != null){
                    save.setDisable(true);
                }
                else{
                    save.setDisable(false);
                    identifyWarning.setText(null);
                }
            }
            else {
                save.setDisable(true);
                identifyWarning.setText("Champ obligatoire");
            }
        });
    }
    private void setExit(){
        exit.setOnAction(actionEvent -> {
            BorderPane modal = (BorderPane) articleCreate.getParent();
            modal.setVisible(false);
        });
    }
    private void setSave(ProductTypeEntity productType){
        save.setOnAction(actionEvent -> {
            if (!articlePriceTextfield.getText().isEmpty()){
                if (this.articleService.create(this.getArticle(productType))!=null){
                    StackPane  productBoxLayout = (StackPane) articleCreate.getParent().getParent();
                    ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) productBoxLayout.getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                    ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                    GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(),4);
                    GridPane productTypeGridPane = new ProductTypeGridPane().getGridPane(new ProductTypeService().getById(productType.getId()).getProduct().getProductTypes(), 4);
                    stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                    productBoxLayoutScrollpane.setContent(productTypeGridPane);
                    articleCreate.getParent().setVisible(false);
                }
            }
        });
    }
    public ArticleEntity getArticle(ProductTypeEntity productType){
        ArticleEntity article = new ArticleEntity();
        article.setCode((this.articleCodeTextfield.getText()));
        article.setPrice(Double.valueOf(this.articlePriceTextfield.getText()));
        article.setSize(this.articleSizeTextfield.getText());
        article.setQuantity(0);
        article.setCreatedDate(LocalDateTime.now());
        article.setColor(this.articleColorTextfield.getText());
        article.setProductType(productType);
        return article;
    }
}
