package com.sales_management_javafx.controller.article_type;

import com.sales_management_javafx.classes.NumberTextField;
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
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.ArticleService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ArticleTypeCreateController implements Initializable {
    @FXML
    private TextField articleSizeTextfield;
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
    private final ArticleService articleService;
    private final ArticleTypeService articleTypeService;

    public ArticleTypeCreateController() {
        this.articleService = new ArticleService();
        this.articleTypeService = new ArticleTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setExit();
        this.validation();
    }
    public void initialize(ArticleEntity productType){
        this.productTypeLabel.setText("Nouveau " + productType.getCode());
        this.setSave(productType);
    }

    private void setExit(){
        exit.setOnAction(actionEvent -> {
            BorderPane modal = (BorderPane) articleCreate.getParent();
            modal.setVisible(false);
        });
    }
    private void setSave(ArticleEntity article){
        save.setOnAction(actionEvent -> {
            if (this.articleTypeService.create(this.getArticle(article))!=null){
                StackPane  productBoxLayout = (StackPane) articleCreate.getParent().getParent();
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) productBoxLayout.getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayout.lookup("#productBoxLayoutScrollpane");
                GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(new ArticleTypeService().getAll(),4);
                GridPane productTypeGridPane = new ProductTypeGridPane().getGridPane(new ArticleService().getById(article.getId()).getProductTypeEntity().getArticles(), 4);
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                productBoxLayoutScrollpane.setContent(productTypeGridPane);
                articleCreate.getParent().setVisible(false);
            }
        });
    }
    public ArticleTypeEntity getArticle(ArticleEntity article){
        ArticleTypeEntity articleType = new ArticleTypeEntity();
        articleType.setSize(this.articleSizeTextfield.getText());
        articleType.setQuantity(0);
        articleType.setCreatedDate(LocalDateTime.now());
        articleType.setColor(this.articleColorTextfield.getText());
        articleType.setArticle(article);
        return articleType;
    }
    private void validation(){
        save.setDisable(true);
        articleSizeTextfield.textProperty().addListener(event->{
            if (articleColorTextfield.getText().isEmpty() || articleSizeTextfield.getText().isEmpty()){
                save.setDisable(true);
            }
        });
        articleColorTextfield.textProperty().addListener(event->{
            save.setDisable(articleColorTextfield.getText().isEmpty() || articleSizeTextfield.getText().isEmpty());
        });
    }
}
