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
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;

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
    private final ArticleService articleService;

    public SellerArticleBoxController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saleBox.setVisible(false);
        articleBox.setVisible(true);
        this.setSale();
        this.setExit();
        this.formValidation();
    }
    public void initialize(ArticleEntity article){
        text.setText("Vous pouvez vendre " + article.getQuantity() + " " + article.getProductType().getName() + " au max");
        if (article.getQuantity() == 0){
            sale.setDisable(true);
        }
        if (FileIO.readArticleFromFile("sales.dat").contains(article)){
            sellerArticleBox.setDisable(true);
        }
        title.setText("Vente de : " + article.getProductType().getProduct().getProductCategory().getName());
        productTypeNameLabel.setText(article.getProductType().getName());
        codeLabel.setText(article.getCode());
        priceLabel.setText(article.getPrice() +"Ar");
        colorLabel.setText(article.getColor());
        sizeLabel.setText(article.getSize());
        quantityLabel.setText(String.valueOf(article.getQuantity()));
        NumberTextField.requireIntegerOnly(quantityToSaleTextfield,article.getQuantity());
        this.setSave(article);
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
    private void setSave(ArticleEntity article){
        save.setOnAction(event->{
            article.setQuantity(Integer.parseInt(quantityToSaleTextfield.getText()));
            article.setId(article.getId());
            Collection<ArticleEntity> articles = FileIO.readArticleFromFile("sales.dat");
            articles.add(article);
            FileIO.writeTo("sales.dat",articles);
            GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleService.getAll(),4);
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
