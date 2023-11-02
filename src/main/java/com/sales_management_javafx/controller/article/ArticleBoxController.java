package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class ArticleBoxController implements Initializable {
    @FXML private StackPane articleBox;
    @FXML private Label articlePriceLabel;
    @FXML private Label articleCodeLabel;
    @FXML private Label articleColorLabel;
    @FXML private Label articleSizeLabel;
    @FXML private Label articleQuantityLabel;
    @FXML private Label productTypeNameLabel;
    @FXML private Label deleteText;
    @FXML private Button delete;
    @FXML private Button confirmDelete;
    @FXML private Button edit;
    @FXML private Button exitDelete;
    @FXML private VBox articleVBox;
    @FXML private VBox deleteVBox;
    @FXML private ImageView DeleteIcon;
    @FXML private ImageView EditIcon;
    private final ArticleService articleService;

    public ArticleBoxController() {

        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.articleVBox.setVisible(true);
        this.deleteVBox.setVisible(false);
        this.setDelete();
        this.setExitDelete();
        this.setEdit();
        this.putIcons();
    }
    public void initialize(ArticleEntity article){
        Double price = article.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        this.productTypeNameLabel.setText(article.getProductType().getName());
        this.articleCodeLabel.setText(String.valueOf(article.getCode()));
        this.articlePriceLabel.setText(decimalFormat.format(price)+ " Ar");
        this.articleSizeLabel.setText(String.valueOf(article.getSize()));
        this.articleQuantityLabel.setText(String.valueOf(article.getQuantity()));
        this.articleColorLabel.setText(article.getColor());
        this.articleBox.getChildren().add(this.getArticleEdit(article));
        this.deleteText.setText("Voulez vous vraiment supprimer l' article " + article.getCode() + " dans la type du produit " + article.getProductType().getName() + "?");
        this.setConfirmDelete(article.getId());
        if (FileIO.readArticleFromFile("articles.dat").contains(article)){
            articleBox.setDisable(true);
        }
        if (FileIO.readArticleFromFile("arrivals.dat").contains(article)){
            articleBox.setDisable(true);
        }
        if (article.getQuantity() != 0){
            delete.setDisable(true);
        }
    }
    private void setDelete(){
        this.delete.setOnAction(event->{
            this.articleVBox.setVisible(false);
            this.deleteVBox.setVisible(true);
        });
    }
    private void setConfirmDelete(Long id){
        confirmDelete.setOnAction(event->{
            if (this.articleService.deleteById(id) != null){
                ProductTypeEntity productType = (ProductTypeEntity) FileIO.readFrom("product-type.dat");
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
                GridPane gridPane = new ArticleGridPane().getGridPane(new ProductTypeService().getById(productType.getId()).getArticles(), 4,false);
                productBoxLayoutScrollpane.setContent(gridPane);
                System.out.println(id);
            }
        });
    }
    private void setExitDelete(){
        this.exitDelete.setOnAction(event->{
            this.articleVBox.setVisible(true);
            this.deleteVBox.setVisible(false);
        });
    }
    private void setEdit(){
        edit.setOnAction(actionEvent -> {
            VBox editProductForm = (VBox) this.articleVBox.getParent().lookup("#articleEditVBox");
            editProductForm.setVisible(true);
            this.articleVBox.setVisible(false);
        });
    }
    public void putIcons(){
        DeleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
        EditIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
    }
    private VBox getArticleEdit(ArticleEntity priceVariation){
        FXMLLoader productVariationEditLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleEdit.fxml"));
        VBox productVariationEditVbox;
        try {
            productVariationEditVbox = productVariationEditLoader.load();
            ArticleEditController articleEditController = productVariationEditLoader.getController();
            articleEditController.initialize(priceVariation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productVariationEditVbox;
    }
}





