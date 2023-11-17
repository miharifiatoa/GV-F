package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.ProductTypeGridPane;
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
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ProductTypeService;
import org.sales_management.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeBoxController implements Initializable {
    @FXML private StackPane productTypeBox;
    @FXML private VBox productTypeVBox;
    @FXML private VBox deleteVBox;
    @FXML private Label articlePrice;
    @FXML private Label productTypeNameLabel;
    @FXML private Label productTypeName;
    @FXML private Label articleQuantityLabel;
    @FXML private Label deleteText;
    @FXML private Button edit;
    @FXML private Button delete;
    @FXML private Button confirmDelete;
    @FXML private Button exit;
    @FXML private Label add;
    @FXML private ImageView editIcon;
    @FXML private ImageView deleteIcon;
    private final ArticleService articleService;
    private final ProductTypeService productTypeService;

    public ProductTypeBoxController() {
        this.productTypeService = new ProductTypeService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        productTypeVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.setDelete();
        this.setExit();
    }
    public void initialize(ArticleEntity article){
        if (article.getArticleTypeEntities().isEmpty()){
            productTypeNameLabel.setDisable(true);
        }
        else {
            delete.setDisable(true);
        }
        deleteText.setText("Voulez vous vraiment supprimer cet article dans le type de produit " + article.getProductTypeEntity().getName() + " ?");
        productTypeNameLabel.setText(article.getCode());
        articlePrice.setText(article.getPrice() + "Ar");
        productTypeName.setText(article.getProductTypeEntity().getName());
        articleQuantityLabel.setText(article.getArticleTypeEntities().size() + " types d' article");
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
        productTypeNameLabel.setOnMouseClicked(event->{
            FileIO.writeTo("product-type.dat",productType);
            GridPane articleGridpane = new ArticleGridPane().getGridPane(productType.getArticleTypeEntities(),4,false);
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
            productTypeVBox.setVisible(false);
            deleteVBox.setVisible(true);
        });
    }
    private void setConfirmDelete(ArticleEntity productType) {
        confirmDelete.setOnAction(event -> {
            if (articleService.deleteById(productType.getId()) != null){
                ProductTypeEntity product = (ProductTypeEntity) FileIO.readFrom("product.dat");
                GridPane productTypeGridPane = new ProductTypeGridPane().getGridPane(new ProductTypeService().getById(product.getId()).getArticles(),4);
                getProductBoxLayoutScrollpane().setContent(productTypeGridPane);
            }

        });
    }

    private void setExit(){
        exit.setOnAction(event->{
            productTypeVBox.setVisible(true);
            deleteVBox.setVisible(false);
        });
    }
    private void setEdit(ArticleEntity productType){
        edit.setOnAction(event->{
            productTypeBox.getChildren().add(this.getProductTypeEdit(productType));
        });
    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productTypeBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private ScrollPane getProductBoxLayoutScrollpane(){
        return (ScrollPane) productTypeBox.getParent().getParent().getParent().getParent();
    }

    private StackPane getArticleCreate(ArticleEntity productType){
        FXMLLoader articleCreateLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article_type/articleCreate.fxml"));
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
        FXMLLoader productTypeEditLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/productTypeEdit.fxml"));
        VBox productTypeEdit;
        try {
            productTypeEdit = productTypeEditLoader.load();
            ProductTypeEditController productTypeEditController = productTypeEditLoader.getController();
            productTypeEditController.initialize(productType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productTypeEdit;
    }
}
