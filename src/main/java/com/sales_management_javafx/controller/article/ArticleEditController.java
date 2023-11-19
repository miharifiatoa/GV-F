package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.composent.ArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleEditController implements Initializable {
    @FXML private VBox productTypeEditVBox;
    @FXML private TextField articlePrice;
    @FXML private Button exit;
    @FXML private Button save;
    private final ArticleService articleService;

    public ArticleEditController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
        this.formValidation();
    }
    public void initialize(ArticleEntity article){
        articlePrice.setText(String.valueOf(article.getPrice()));
        this.setSave(article);
    }
    private void setExit(){
        exit.setOnAction(event->{
            productTypeEditVBox.setVisible(false);
        });
    }
    private void setSave(ArticleEntity article){
        save.setOnAction(event->{
            ArticleEntity articleEntity = articleService.getById(article.getId());
            if (articleEntity != null){
                articleEntity.setPrice(Double.valueOf(articlePrice.getText()));
                if (articleService.update(articleEntity) != null){
                    GridPane productTypeGridPane = new ArticleGridPane().getGridPane(new ArticleService().getById(article.getId()).getProductTypeEntity().getArticles(), 4);
                    ScrollPane productBoxLayoutScrollPane = (ScrollPane) productTypeEditVBox.getParent().getParent().getParent().getParent().getParent();
                    productBoxLayoutScrollPane.setContent(productTypeGridPane);
                }
            }
        });
    }
    private void formValidation(){
        if (articlePrice.getText().isEmpty()){
            save.setDisable(true);
        }
        articlePrice.textProperty().addListener(event->{
            save.setDisable(articlePrice.getText().isEmpty());
        });
    }
}
