package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleTypeGridPane;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.controller.article_type.ArticleTypeCreateController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ProductTypeService;
import org.sales_management.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArticleBoxController implements Initializable {
    @FXML private StackPane articleBox;
    @FXML private VBox articleVBox;
    @FXML private VBox deleteVBox;
    @FXML private Label articlePrice;
    @FXML private Label articleNameLabel;
    @FXML private Label productTypeName;
    @FXML private Label articleQuantityLabel;
    @FXML private Label deleteText;
    @FXML private Button edit;
    @FXML private Button delete;
    @FXML private Label save;
    @FXML private Label exit;
    @FXML private Label add;
    @FXML private ImageView editIcon;
    @FXML private ImageView deleteIcon;
    private final ArticleService articleService;
    private final ProductTypeService productTypeService;

    public ArticleBoxController() {
        this.productTypeService = new ProductTypeService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        articleVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.setDelete();
        this.setExit();
    }
    public void initialize(ArticleEntity article){
        if (!article.getArticleTypeEntities().isEmpty()){
            delete.setDisable(true);
        }
        else {
            articleNameLabel.setDisable(true);
        }
        deleteText.setText("Voulez vous vraiment supprimer cet article dans le type de produit " + article.getProductTypeEntity().getName() + " ?");
        articleNameLabel.setText(article.getCode());
        articlePrice.setText(article.getPrice() + "Ar");
        productTypeName.setText(article.getProductTypeEntity().getName());
        articleQuantityLabel.setText(article.getArticleTypeEntities().size() + " type(s)");
        this.setProductTypeNameLabel(article);
        this.setAdd(article);
        this.setConfirmDelete(article);
        this.setEdit(article);
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private void setProductTypeNameLabel(ArticleEntity productType){
        articleNameLabel.setOnMouseClicked(event->{
            GridPane articleGridpane = new ArticleTypeGridPane().getGridPane(productType.getArticleTypeEntities(),4,false);
            getProductBoxLayoutScrollpane().setContent(articleGridpane);
        });
    }
    private void setAdd(ArticleEntity productType){
        add.setOnMouseClicked(event->{
            StackPane productBoxLayout = this.getProductBoxLayout();
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getArticleCreate(productType));
            modal.setVisible(true);
        });
    }
    private void setDelete(){
        delete.setOnAction(event->{
            articleVBox.setVisible(false);
            deleteVBox.setVisible(true);
        });
    }
    private void setConfirmDelete(ArticleEntity article) {
        save.setOnMouseClicked(event -> {
            if (articleService.deleteById(article.getId()) != null){
                GridPane gridPane = new ArticleGridPane().getGridPane(new ProductTypeService().getById(article.getProductTypeEntity().getId()).getArticles(),4);
                getProductBoxLayoutScrollpane().setContent(gridPane);
            }
        });
    }

    private void setExit(){
        exit.setOnMouseClicked(event->{
            articleVBox.setVisible(true);
            deleteVBox.setVisible(false);
        });
    }
    private void setEdit(ArticleEntity productType){
        edit.setOnAction(event->{
            articleBox.getChildren().add(this.getProductTypeEdit(productType));
            articleVBox.setVisible(false);
        });
    }
    public StackPane getProductBoxLayout(){
        return (StackPane) articleBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private ScrollPane getProductBoxLayoutScrollpane(){
        return (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
    }

    private StackPane getArticleCreate(ArticleEntity productType){
        FXMLLoader articleCreateLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article_type/articleTypeCreate.fxml"));
        StackPane articleCreate;
        try {
            articleCreate = articleCreateLoader.load();
            ArticleTypeCreateController articleTypeCreateController = articleCreateLoader.getController();
            articleTypeCreateController.initialize(productType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleCreate;
    }
    private VBox getProductTypeEdit(ArticleEntity productType){
        FXMLLoader productTypeEditLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleEdit.fxml"));
        VBox productTypeEdit;
        try {
            productTypeEdit = productTypeEditLoader.load();
            ArticleEditController articleEditController = productTypeEditLoader.getController();
            articleEditController.initialize(productType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productTypeEdit;
    }
}
