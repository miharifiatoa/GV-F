package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.PriceVariationGridPane;
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

public class ProductBoxController implements Initializable {
    @FXML
    private StackPane productBox;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
    @FXML
    private Label productNameLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();

    }
    public void initialize(ProductEntity product){
        productNameLabel.setText(product.getName());
        this.showProductVariation(product);
    }
    private void putIcons(){
        this.editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
        this.deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
    }
    private void showProductVariation(ProductEntity product){
        productNameLabel.setOnMouseClicked(event->{
            FileIO.writeTo("product.dat",product);
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBox.getParent().getParent().getParent().getParent();
            GridPane gridPane = new PriceVariationGridPane().getGridPane(product.getPriceVariations(),3,true);
            productBoxLayoutScrollpane.setContent(gridPane);
        });
    }
}
