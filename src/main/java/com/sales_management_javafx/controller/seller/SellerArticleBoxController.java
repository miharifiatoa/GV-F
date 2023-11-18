package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
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
import org.sales_management.service.ArticleTypeService;

import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;

public class SellerArticleBoxController implements Initializable {
    @FXML private Label priceLabel;
    @FXML private Label colorLabel;
    @FXML private Label codeLabel;
    @FXML private Label sizeLabel;
    @FXML private Label quantityLabel;
    @FXML private Label productTypeNameLabel;
    @FXML private Label title;
    @FXML private Label text;
    @FXML private Button sale;
    @FXML private Button save;
    @FXML private Button exit;
    @FXML private TextField quantityToSaleTextfield;
    @FXML private VBox saleBox;
    @FXML private VBox articleBox;
    @FXML private StackPane sellerArticleBox;
    private final ArticleTypeService articleTypeService;

    public SellerArticleBoxController() {
        this.articleTypeService = new ArticleTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saleBox.setVisible(false);
        articleBox.setVisible(true);
        this.setSale();
        this.setExit();
        this.formValidation();
    }
    public void initialize(ArticleTypeEntity articleType){
        text.setText("Vous pouvez vendre " + articleType.getQuantity() + " " + articleType.getArticle().getProductTypeEntity().getName() + " au max");
        if (articleType.getQuantity() == 0){
            sale.setDisable(true);
        }
        if (FileIO.readArticleFromFile("sales.dat").contains(articleType)){
            sellerArticleBox.setDisable(true);
        }
        title.setText("Vente de : " + articleType.getArticle().getProductTypeEntity().getProduct().getName());
        productTypeNameLabel.setText(articleType.getArticle().getProductTypeEntity().getName());
        codeLabel.setText(articleType.getArticle().getCode());
        priceLabel.setText(articleType.getArticle().getPrice() +"Ar");
        colorLabel.setText(articleType.getColor());
        sizeLabel.setText(articleType.getSize());
        quantityLabel.setText(String.valueOf(articleType.getQuantity()));
        NumberTextField.requireIntegerOnly(quantityToSaleTextfield,articleType.getQuantity());
        this.setSave(articleType);
    }
    private void setSale(){
        sale.setOnAction(event->{
            saleBox.setVisible(true);
            articleBox.setVisible(false);
        });
    }
    private void setExit(){
        exit.setOnAction(event->{
            saleBox.setVisible(false);
            articleBox.setVisible(true);
        });
    }
    private void setSave(ArticleTypeEntity article){
        save.setOnAction(event->{
            article.setQuantity(Integer.parseInt(quantityToSaleTextfield.getText()));
            article.setId(article.getId());
            Collection<ArticleTypeEntity> articles = FileIO.readArticleFromFile("sales.dat");
            articles.add(article);
            FileIO.writeTo("sales.dat",articles);
            GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleTypeService.getAll(),4);
            GridPane pannierArticleGridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("sales.dat"),2);
            ScrollPane sellerArticleScrollpane = (ScrollPane) sellerArticleBox.getParent().getParent().getParent().getParent();
            sellerArticleScrollpane.setContent(sellerArticleGridPane);
            BorderPane sellerLayout = (BorderPane) sellerArticleScrollpane.getParent().getParent().getParent();
            ScrollPane pannierLayoutScrollpane = (ScrollPane) sellerLayout.lookup("#pannierLayoutScrollpane");
            Label priceTotal = (Label) sellerLayout.lookup("#priceTotal");
            if (pannierLayoutScrollpane != null){
                pannierLayoutScrollpane.setContent(pannierArticleGridPane);
                System.out.println(pannierLayoutScrollpane);
            }
            if (priceTotal != null){
                priceTotal.setText("Prix total : " + FileIO.getPriceTotal("sales.dat") +"Ar");
            }
        });
    }
    private void formValidation(){
        if (quantityToSaleTextfield.getText().isEmpty() || Objects.equals(quantityToSaleTextfield.getText(), String.valueOf(0))){
            save.setDisable(true);
        }
        quantityToSaleTextfield.textProperty().addListener(event->{
            save.setDisable(quantityToSaleTextfield.getText().isEmpty() ||  Objects.equals(quantityToSaleTextfield.getText(), String.valueOf(0)));
        });
    }
}
