package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.SellerProductGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;
import org.sales_management.service.ShopService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class SellerLayoutController implements Initializable {
    @FXML
    private BorderPane sellerLayout;
    @FXML
    private ScrollPane sellerProductScrollpane;
    private final ShopService shopService;
    private final ProductService productService;

    public SellerLayoutController() {
        this.shopService = new ShopService();
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sellerLayout.setBottom(this.getToolbar());
//        this.setProducts();
    }
//    private void setProducts(){
//        Collection<ProductEntity> products = productService.getAll();
//        GridPane gridPane = new SellerProductGridPane().getGridPane(products,4);
//        sellerProductScrollpane.setContent(gridPane);
//    }
    private GridPane getToolbar(){
        FXMLLoader pannierToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerToolbar.fxml"));
        GridPane pannierToolbar;
        try {
            pannierToolbar = pannierToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pannierToolbar;
    }
}
