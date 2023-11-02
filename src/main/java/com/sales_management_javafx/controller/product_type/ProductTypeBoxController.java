package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleGridPane;
import com.sales_management_javafx.composent.ProductTypeGridPane;
import com.sales_management_javafx.controller.article.ArticleCreateController;
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
import org.sales_management.entity.ProductEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ProductTypeService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductTypeBoxController implements Initializable {
    @FXML private StackPane productTypeBox;
    @FXML private VBox productTypeVBox;
    @FXML private VBox deleteVBox;
    @FXML private Label productTypeReferenceLabel;
    @FXML private Label productTypeBrandLabel;
    @FXML private Label productTypeQualiteLabel;
    @FXML private Label productTypeNameLabel;
    @FXML private Label articleQuantityLabel;
    @FXML private Label deleteText;
    @FXML private Button edit;
    @FXML private Button delete;
    @FXML private Button confirmDelete;
    @FXML private Button exit;
    @FXML private Label add;
    @FXML private ImageView editIcon;
    @FXML private ImageView deleteIcon;
    private final ProductTypeService productTypeService;
    private final ProductService productService;

    public ProductTypeBoxController() {
        this.productService = new ProductService();
        this.productTypeService = new ProductTypeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        productTypeVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.setDelete();
        this.setExit();
    }
    public void initialize(ProductTypeEntity productType){
        if (productType.getArticles().isEmpty()){
            productTypeNameLabel.setDisable(true);
        }
        else {
            delete.setDisable(true);
        }
        deleteText.setText("Voulez vous supprimer " + productType.getName() + " dans la liste des produits " + productType.getProduct().getName());
        productTypeNameLabel.setText(productType.getName());
        productTypeReferenceLabel.setText(productType.getReference());
        productTypeBrandLabel.setText(productType.getBrand());
        productTypeQualiteLabel.setText(productType.getQuality());
        articleQuantityLabel.setText(productType.getArticles().size() + " article(s)");
        this.setProductTypeNameLabel(productType);
        this.setAdd(productType);
        this.setConfirmDelete(productType);
        this.setEdit(productType);
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private void setProductTypeNameLabel(ProductTypeEntity productType){
        productTypeNameLabel.setOnMouseClicked(event->{
            FileIO.writeTo("product-type.dat",productType);
            GridPane articleGridpane = new ArticleGridPane().getGridPane(productType.getArticles(),4,false);
            getProductBoxLayoutScrollpane().setContent(articleGridpane);
        });
    }
    private void setAdd(ProductTypeEntity productType){
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
    private void setConfirmDelete(ProductTypeEntity productType) {
        confirmDelete.setOnAction(event -> {
            if (productTypeService.deleteById(productType.getId()) != null){
                ProductEntity product = (ProductEntity) FileIO.readFrom("product.dat");
                GridPane productTypeGridPane = new ProductTypeGridPane().getGridPane(new ProductService().getById(product.getId()).getProductTypes(),4);
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
    private void setEdit(ProductTypeEntity productType){
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

    private StackPane getArticleCreate(ProductTypeEntity productType){
        FXMLLoader articleCreateLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleCreate.fxml"));
        StackPane articleCreate;
        try {
            articleCreate = articleCreateLoader.load();
            ArticleCreateController articleCreateController = articleCreateLoader.getController();
            articleCreateController.initialize(productType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleCreate;
    }
    private VBox getProductTypeEdit(ProductTypeEntity productType){
        FXMLLoader productTypeEditLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_type/productTypeEdit.fxml"));
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
