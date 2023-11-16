package com.sales_management_javafx.controller.article_type;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.ArticleService;

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
    private final ArticleTypeService articleTypeService;

    public ArticleBoxController() {

        this.articleTypeService = new ArticleTypeService();
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
    public void initialize(ArticleTypeEntity articleType){
        Double price = articleType.getArticle().getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        this.articleCodeLabel.setText(String.valueOf(articleType.getArticle().getCode()));
        this.articlePriceLabel.setText(decimalFormat.format(price)+ " Ar");
        this.articleBox.getChildren().add(this.getArticleEdit(articleType));
        this.deleteText.setText("Voulez vous vraiment supprimer l' article " + articleType.getArticle().getCode() + " dans la type du produit " + articleType.getArticle().getProductTypeEntity().getName() + "?");
        this.setConfirmDelete(articleType.getId());
        if (FileIO.readArticleFromFile("articles.dat").contains(articleType)){
            articleBox.setDisable(true);
        }
        if (FileIO.readArticleFromFile("arrivals.dat").contains(articleType)){
            articleBox.setDisable(true);
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
            if (this.articleTypeService.deleteById(id) != null){
                ArticleEntity productType = (ArticleEntity) FileIO.readFrom("product-type.dat");
                ScrollPane productBoxLayoutScrollpane = (ScrollPane) articleBox.getParent().getParent().getParent().getParent();
                GridPane gridPane = new ArticleGridPane().getGridPane(new ArticleService().getById(productType.getId()).getArticleTypeEntities(), 4,false);
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
    private VBox getArticleEdit(ArticleTypeEntity priceVariation){
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





