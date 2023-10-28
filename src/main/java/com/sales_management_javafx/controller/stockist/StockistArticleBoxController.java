package com.sales_management_javafx.controller.stockist;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.StockistArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductTypeService;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class StockistArticleBoxController implements Initializable {
    @FXML private Label articleCodeLabel;
    @FXML private Label articlePriceLabel;
    @FXML private Label articleSizeLabel;
    @FXML private Label articleColorLabel;
    @FXML private Label articleQuantityLabel;
    @FXML private Label productTypeNameLabel;
    @FXML private Label quantityToRetireLabel;
    @FXML private Label quantityToShareLabel;
    @FXML private Button share;
    @FXML private Label add;
    @FXML private Label retire;
    @FXML private Button confirmAdd;
    @FXML private Button confirmRetire;
    @FXML private Button exitAdd;
    @FXML private Button exitRetire;
    @FXML private Button exitShare;
    @FXML private Button addInList;
    @FXML private TextField quantityAddedTextfield;
    @FXML private TextField quantityRetireTextfield;
    @FXML private TextField quantitySharedTextfield;
    @FXML private ImageView ShareIcon;
    @FXML private VBox shareVBox;
    @FXML private VBox articleVBox;
    @FXML private VBox addVBox;
    @FXML private VBox retireVBox;
    @FXML private StackPane articleBox;
    private final ArticleService articleService;

    public StockistArticleBoxController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.setShare();
        this.setAdd();
        this.setRetire();
        this.setExitShare();
        this.setExitAdd();
        this.setExitRetire();
        this.formValidation();
        this.addVBox.setVisible(false);
        this.retireVBox.setVisible(false);
        this.shareVBox.setVisible(false);
        NumberTextField.requireIntegerOnly(quantityAddedTextfield,1000);
    }
    public void initialize(ArticleEntity article){
        articleCodeLabel.setText(article.getCode());
        articlePriceLabel.setText(String.valueOf(article.getPrice()));
        articleSizeLabel.setText(article.getSize());
        articleColorLabel.setText(article.getColor());
        articleQuantityLabel.setText(String.valueOf(article.getQuantity()));
        productTypeNameLabel.setText(article.getProductType().getName());
        if (article.getQuantity()<=0){
            retire.setDisable(true);
            share.setDisable(true);
        }
        if (FileIO.readArticleFromFile("articles.dat").contains(article)){
            articleBox.setDisable(true);
        }
        if (FileIO.readArticleFromFile("arrivals.dat").contains(article)){
            articleBox.setDisable(true);
        }
        this.quantityToRetireLabel.setText("Vous pouvez retirer au plus " + article.getQuantity() + " produits");
        this.quantityToShareLabel.setText("Vous pouvez partager au plus " + article.getQuantity() + " produits");
        this.onAddArticleInShareList(article);
        this.setArticleInArrivalList(article);
        this.setConfirmRetire(article);
        NumberTextField.requireIntegerOnly(quantityRetireTextfield,article.getQuantity());
        NumberTextField.requireIntegerOnly(quantitySharedTextfield,article.getQuantity());
    }
    private void setAdd(){
        add.setOnMouseClicked(event->{
            this.articleVBox.setVisible(false);
            this.addVBox.setVisible(true);
        });
    }
    private void setArticleInArrivalList(ArticleEntity article){
        confirmAdd.setOnAction(event->{
            if (!this.quantityAddedTextfield.getText().isEmpty() && article!=null){
                try {
                    article.setQuantity(Integer.parseInt(quantityAddedTextfield.getText()));
                    List<ArticleEntity> articles = (List<ArticleEntity>) FileIO.readArticleFromFile("arrivals.dat");
                    articles.add(article);
                    FileIO.writeTo("arrivals.dat",articles);
                    ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(), 4);
                    stockistBoxLayoutScrollpane.setContent(gridPane);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }

            }

        });
    }
    private void setExitAdd(){
        exitAdd.setOnAction(event->{
            this.articleVBox.setVisible(true);
            this.addVBox.setVisible(false);
        });
    }
    private void setRetire(){
        this.retire.setOnMouseClicked(event->{
            this.retireVBox.setVisible(true);
            this.articleVBox.setVisible(false);
        });
    }
    private void setConfirmRetire(ArticleEntity article){
        this.confirmRetire.setOnAction(event->{
            if (!quantityRetireTextfield.getText().isEmpty() && article!=null){
                article.setQuantity(article.getQuantity() - Integer.parseInt(quantityRetireTextfield.getText()));
                ArticleEntity productResponse = this.articleService.update(article);
                if (productResponse!=null){
                    ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(), 4);
                    productBoxLayoutScrollpane.setContent(gridPane);
                }
            }
        });
    }
    private void setExitRetire(){
        exitRetire.setOnAction(event->{
            this.retireVBox.setVisible(false);
            this.articleVBox.setVisible(true);
        });
    }
    private void setShare(){
        this.share.setOnAction(event->{
            this.shareVBox.setVisible(true);
            this.articleVBox.setVisible(false);
        });
    }
    private void onAddArticleInShareList(ArticleEntity article){
        addInList.setOnAction(event->{
            article.setQuantity(Integer.parseInt(quantitySharedTextfield.getText()));
            Collection<ArticleEntity> existingPrices = FileIO.readArticleFromFile("articles.dat");
            existingPrices.add(article);
            FileIO.writeTo("articles.dat", existingPrices);
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
            GridPane gridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(), 4);
            productBoxLayoutScrollpane.setContent(gridPane);
        });
    }
    private void setExitShare(){
        this.exitShare.setOnAction(event->{
            this.shareVBox.setVisible(false);
            this.articleVBox.setVisible(true);
        });
    }
    private void formValidation(){
        if (quantityAddedTextfield.getText().isEmpty()){
            confirmAdd.setDisable(true);
        }
        if (quantityRetireTextfield.getText().isEmpty()){
            confirmRetire.setDisable(true);
        }
        if (quantitySharedTextfield.getText().isEmpty()){
            addInList.setDisable(true);
        }
        quantityAddedTextfield.textProperty().addListener((observableValue, s, t1) -> {
            confirmAdd.setDisable(quantityAddedTextfield.getText().isEmpty());
        });
        quantityRetireTextfield.textProperty().addListener((observableValue, s, t1) -> {
            confirmRetire.setDisable(quantityRetireTextfield.getText().isEmpty());
        });
        quantitySharedTextfield.textProperty().addListener((observableValue, s, t1) -> {
            addInList.setDisable(quantitySharedTextfield.getText().isEmpty());
        });
    }
    public void putIcons(){
        ShareIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShareIcon.png"))));
    }
}
