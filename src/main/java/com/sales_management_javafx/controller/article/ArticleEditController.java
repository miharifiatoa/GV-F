package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.composent.ArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductCategoryService;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ArticleEditController implements Initializable {
    @FXML
    private TextField articlePriceTextfield;
    @FXML
    private TextField articleSizeTextfield;
    @FXML
    private TextField articleColorTextfield;
    @FXML
    private Button save;
    @FXML
    private Button exit;
    @FXML
    private ScrollPane articleEditScrollpane;
    @FXML
    private VBox articleEditVBox;
    @FXML
    ProductGridPane productGridPane =new ProductGridPane();
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ArticleService articleService;

    public ArticleEditController() {
        this.articleService = new ArticleService();
        this.productCategoryService = new ProductCategoryService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.articleEditVBox.setVisible(false);
        this.formValidation();
        this.setExit();
        NumberTextField.requireDouble(this.articlePriceTextfield);
    }
    private void formValidation(){
        if (articlePriceTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        articlePriceTextfield.textProperty().addListener(event->{
            save.setDisable(articlePriceTextfield.getText().isEmpty());
        });
    }

    public void initialize(ArticleEntity article){
        Double price = article.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        this.articlePriceTextfield.setText(decimalFormat.format(price));
        this.articleSizeTextfield.setText(article.getSize());
        this.articleColorTextfield.setText(article.getColor());
        this.setSave(article.getId());
    }
    public void setSave(Long article_id){
        save.setOnAction(actionEvent -> {
            if (!articlePriceTextfield.getText().isEmpty()){
                if (this.articleService.update(this.getNewArticle(article_id))!=null){
                    GridPane gridPane = new ArticleGridPane().getGridPane(new ArticleService().getById(article_id).getProductType().getArticles(), 4,false);
                    ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleEditVBox.getParent().getParent().getParent().getParent().getParent();
                    productBoxLayoutScrollpane.setContent(gridPane);
                }
            }
        });
    }
    private ArticleEntity getNewArticle(Long article_id){
        ArticleEntity article = this.articleService.getById(article_id);
        article.setSize(this.articleSizeTextfield.getText());
        article.setPrice(Double.valueOf(this.articlePriceTextfield.getText()));
        article.setColor(this.articleColorTextfield.getText());
        return article;
    }
    public void setExit(){
        exit.setOnAction(actionEvent -> {
            VBox articleVBox = (VBox) articleEditVBox.getParent().lookup("#articleVBox");
            articleEditVBox.setVisible(false);
            articleVBox.setVisible(true);
        });
    }
}
