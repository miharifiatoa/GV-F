package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.ProductTypeGridPane;
import com.sales_management_javafx.composent.SellerPriceVariationGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ProductEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerProductBoxController implements Initializable {
    @FXML
    private StackPane sellerProductBox;
    @FXML
    private Label sellerProductNameLabel;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
    }
    public void initialize(ProductEntity product){
        sellerProductNameLabel.setText(product.getName());
        this.onShowProductType(product);
    }
    public void onShowProductType(ProductEntity product){
        sellerProductNameLabel.setOnMouseClicked(event->{
            GridPane gridPane = new ProductTypeGridPane().getGridPane(product.getProductTypes(),4);
            ScrollPane sellerProductScrollpane = (ScrollPane) sellerProductBox.getParent().getParent().getParent().getParent();
            sellerProductScrollpane.setContent(gridPane);
        });
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
}
