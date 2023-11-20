package com.sales_management_javafx.controller.stockist;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.stockist.StockistArticleTypeGridPane;
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
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.service.ArticleTypeService;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class StockistArticleTypeBoxController implements Initializable {
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
    private final ArticleTypeService articleTypeService;

    public StockistArticleTypeBoxController() {
        this.articleTypeService = new ArticleTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.setShare();
        this.setAdd();
        this.setExitShare();
        this.setExitAdd();
        this.setExitRetire();
        this.formValidation();
        this.addVBox.setVisible(false);
        this.retireVBox.setVisible(false);
        this.shareVBox.setVisible(false);
        NumberTextField.requireIntegerOnly(quantityAddedTextfield,1000);
    }
    public void initialize(ArticleTypeEntity article){
        articleCodeLabel.setText(article.getArticle().getCode());
        articlePriceLabel.setText(String.valueOf(article.getArticle().getPrice()));
        articleSizeLabel.setText(article.getSize());
        articleColorLabel.setText(article.getColor());
        articleQuantityLabel.setText(String.valueOf(article.getQuantity()));
        productTypeNameLabel.setText(article.getArticle().getCode());
        if (article.getQuantity()<=0){
            share.setDisable(true);
        }
        if (FileIO.readArticleFromFile("shares.dat").contains(article)){
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
    }
    private void setAdd(){
        add.setOnMouseClicked(event->{
            this.articleVBox.setVisible(false);
            this.addVBox.setVisible(true);
        });
    }
    private void setArticleInArrivalList(ArticleTypeEntity articleType){
        confirmAdd.setOnAction(event->{
            if (!this.quantityAddedTextfield.getText().isEmpty() && articleType!=null){
                try {
                    articleType.setQuantity(Integer.parseInt(quantityAddedTextfield.getText()));
                    List<ArticleTypeEntity> articles = (List<ArticleTypeEntity>) FileIO.readArticleFromFile("arrivals.dat");
                    articles.add(articleType);
                    FileIO.writeTo("arrivals.dat",articles);
                    ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getById(articleType.getId()).getArticle().getArticleTypeEntities(), 4);
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
    private void setConfirmRetire(ArticleTypeEntity article){
        this.confirmRetire.setOnAction(event->{
            if (!quantityRetireTextfield.getText().isEmpty() && article!=null){
                article.setQuantity(article.getQuantity() - Integer.parseInt(quantityRetireTextfield.getText()));
                ArticleTypeEntity productResponse = this.articleTypeService.update(article);
                if (productResponse!=null){
                    ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getAll(), 4);
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
    private void onAddArticleInShareList(ArticleTypeEntity articleType){
        addInList.setOnAction(event->{
            articleType.setQuantity(Integer.parseInt(quantitySharedTextfield.getText()));
            Collection<ArticleTypeEntity> existingTypes = FileIO.readArticleFromFile("shares.dat");
            existingTypes.add(articleType);
            FileIO.writeTo("shares.dat", existingTypes);
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
            GridPane gridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getById(articleType.getId()).getArticle().getArticleTypeEntities(), 4);
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
        if (quantityAddedTextfield.getText().isEmpty() || Integer.parseInt(quantityAddedTextfield.getText())<=0){
            confirmAdd.setDisable(true);
        }
        if (quantityRetireTextfield.getText().isEmpty()  || Integer.parseInt(quantityRetireTextfield.getText())<=0){
            confirmRetire.setDisable(true);
        }
        if (quantitySharedTextfield.getText().isEmpty()  || Integer.parseInt(quantitySharedTextfield.getText())<=0){
            addInList.setDisable(true);
        }
        quantityAddedTextfield.textProperty().addListener((observableValue, s, t1) -> {
            confirmAdd.setDisable(quantityAddedTextfield.getText().isEmpty() || Integer.parseInt(quantityAddedTextfield.getText())<=0);
        });
        quantityRetireTextfield.textProperty().addListener((observableValue, s, t1) -> {
            confirmRetire.setDisable(quantityRetireTextfield.getText().isEmpty()  || Integer.parseInt(quantityRetireTextfield.getText())<=0);
        });
        quantitySharedTextfield.textProperty().addListener((observableValue, s, t1) -> {
            addInList.setDisable(quantitySharedTextfield.getText().isEmpty()  || Integer.parseInt(quantitySharedTextfield.getText())<=0);
        });
    }
    public void putIcons(){
        ShareIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShareIcon.png"))));
    }
}
