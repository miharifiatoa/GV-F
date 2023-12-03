package com.sales_management_javafx.controller.article_type;

import com.sales_management_javafx.composent.ProductTypeGridPane;
import com.sales_management_javafx.composent.ArticleTypeGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleTypeEditController implements Initializable {
    @FXML
    private TextField articleSizeTextfield;
    @FXML
    private TextField articleColorTextfield;
    @FXML
    private Label save;
    @FXML
    private Label exit;
    @FXML
    private VBox articleEditVBox;
    @FXML
    ProductTypeGridPane productTypeGridPane = new ProductTypeGridPane();
    private final ProductTypeService productTypeService;
    private final ProductService productService;
    private final ArticleTypeService articleTypeService;

    public ArticleTypeEditController() {
        this.articleTypeService = new ArticleTypeService();
        this.productService = new ProductService();
        this.productTypeService = new ProductTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.articleEditVBox.setVisible(false);
        this.setExit();
    }

    public void initialize(ArticleTypeEntity article){
        this.articleSizeTextfield.setText(article.getSize());
        this.articleColorTextfield.setText(article.getColor());
        this.setSave(article.getId());
    }
    public void setSave(Long article_id){
        save.setOnMouseClicked(actionEvent -> {
            if (this.articleTypeService.update(this.getNewArticle(article_id))!=null){
                GridPane gridPane = new ArticleTypeGridPane().getGridPane(new ArticleTypeService().getById(article_id).getArticle().getArticleTypeEntities(), 4,false);
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleEditVBox.getParent().getParent().getParent().getParent().getParent();
                productBoxLayoutScrollpane.setContent(gridPane);
            }
        });
    }
    private ArticleTypeEntity getNewArticle(Long article_id){
        ArticleTypeEntity article = this.articleTypeService.getById(article_id);
        article.setSize(this.articleSizeTextfield.getText());
        article.setColor(this.articleColorTextfield.getText());
        return article;
    }
    public void setExit(){
        exit.setOnMouseClicked(actionEvent -> {
            VBox articleVBox = (VBox) articleEditVBox.getParent().lookup("#articleVBox");
            articleEditVBox.setVisible(false);
            articleVBox.setVisible(true);
        });
    }
}
