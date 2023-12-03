package com.sales_management_javafx.controller.product_category;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.ProductCategoryGridPane;
import com.sales_management_javafx.composent.ProductGridPane;
import com.sales_management_javafx.controller.product.ProductCreateController;
import com.sales_management_javafx.controller.product_type.ProductTypeEditController;
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
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.entity.ProductTypeEntity;
import org.sales_management.service.ProductCategoryService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductCategoryBoxController implements Initializable {
    @FXML private StackPane productCategoryBox;
    @FXML private VBox deleteVBox;
    @FXML private VBox productCategoryVBox;
    @FXML private Label productCategoryNameLabel;
    @FXML private Label createProductLabel;
    @FXML private ImageView deleteIcon;
    @FXML private ImageView editIcon;
    @FXML private Button delete;
    @FXML private Button edit;
    @FXML private Label save;
    @FXML private Label exit;
    @FXML private Label quantityLabel;
    @FXML private Label deleteText;
    private final ProductCategoryService productCategoryService;

    public ProductCategoryBoxController() {
        this.productCategoryService = new ProductCategoryService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        productCategoryVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.setDelete();
        this.setExit();
        this.setEdit();
    }
    private void setEdit(){
        edit.setOnAction(event->{
            productCategoryVBox.setVisible(false);
            productCategoryBox.lookup("#editVBox").setVisible(true);
        });
    }
    private void setDelete(){
        delete.setOnAction(event->{
            productCategoryVBox.setVisible(false);
            deleteVBox.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnMouseClicked(event->{
            productCategoryVBox.setVisible(true);
            deleteVBox.setVisible(false);
        });
    }
    public void initialize(ProductCategoryEntity productCategory){
        if (!productCategory.getProducts().isEmpty()){
            delete.setDisable(true);
        }
        else {
            productCategoryNameLabel.setDisable(true);
        }
        productCategoryNameLabel.setText(productCategory.getName());
        this.onShowProducts(productCategory);
        this.onCreateProduct(productCategory);
        this.setSave(productCategory);
        quantityLabel.setText(productCategory.getProducts().size() + " produit(s)");
        deleteText.setText("Voulez vous vraiment supprimer ce categorie de produit : " + productCategory.getName() + " ?");
        productCategoryBox.getChildren().add(this.getProductCategoryEdit(productCategory));
    }
    public StackPane getProductBoxLayout(){
        return (StackPane) productCategoryBox.getParent().getParent().getParent().getParent().getParent().getParent().getParent();
    }
    private ScrollPane getProductBoxLayoutScrollpane(){
        return (ScrollPane) productCategoryBox.getParent().getParent().getParent().getParent();
    }
    private void setSave(ProductCategoryEntity productCategory){
        save.setOnMouseClicked(event->{
            if (productCategoryService.deleteById(productCategory.getId()) != null){
                GridPane productCategoryGridpane = new ProductCategoryGridPane().getGridPane(productCategoryService.getAll(),4);
                getProductBoxLayoutScrollpane().setContent(productCategoryGridpane);
            }
        });
    }
    private void onShowProducts(ProductCategoryEntity productCategory){
        productCategoryNameLabel.setOnMouseClicked(event->{
            StackPane productLayout = this.getProductBoxLayout();
            BorderPane productBoxLayoutBorderpane = (BorderPane) productLayout.lookup("#productBoxLayoutBorderpane");
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productLayout.lookup("#productBoxLayoutScrollpane");
            GridPane productGridPane = new ProductGridPane().getGridPane(this.productCategoryService.getById(productCategory.getId()).getProducts(),4);
            productBoxLayoutScrollpane.setContent(productGridPane);
            productBoxLayoutBorderpane.setCenter(productBoxLayoutScrollpane);
        });
    }
    private void onCreateProduct(ProductCategoryEntity productCategory){
        createProductLabel.setOnMouseClicked(event->{
            StackPane productBoxLayout = this.getProductBoxLayout();
            BorderPane modal = (BorderPane) productBoxLayout.lookup("#modal");
            modal.setCenter(this.getProductCreateBox(productCategory));
            modal.setVisible(true);
            event.consume();
        });
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private StackPane getProductCreateBox(ProductCategoryEntity productCategory){
        FXMLLoader createProductBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productCreate.fxml"));
        StackPane createProductBox;
        try {
            createProductBox = createProductBoxLoader.load();
            ProductCreateController productCreateController = createProductBoxLoader.getController();
            productCreateController.initialize(productCategory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return createProductBox;
    }
    private VBox getProductCategoryEdit(ProductCategoryEntity productCategory){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product_category/productCategoryEdit.fxml"));
        VBox vBox;
        try {
            vBox = fxmlLoader.load();
            ProductCategoryEditController productCategoryEditController = fxmlLoader.getController();
            productCategoryEditController.initialize(productCategory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vBox;
    }
}
