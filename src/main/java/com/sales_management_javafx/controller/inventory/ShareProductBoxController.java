package com.sales_management_javafx.controller.inventory;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.ProductFile;
import com.sales_management_javafx.composent.ProductShareGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ShareProductBoxController implements Initializable {
    @FXML
    private BorderPane shareProductLayoutBorderpane;
    @FXML
    private ScrollPane shareProductLayoutScrollpane;
    @FXML
    private ImageView shareListIcon;
    @FXML
    private Button closeShareListButton;
    @FXML
    private GridPane toolbar;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.onCloseShareList();
        this.setProducts();
    }
    public void setProducts(){
        GridPane gridPane = new ProductShareGridPane().getGridPane(ProductFile.readProductsFromFile(),2 );
        shareProductLayoutScrollpane.setContent(gridPane);
    }
    public void putIcons(){
        shareListIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShowShareListIcon.png"))));
    }
    private void onCloseShareList(){
        closeShareListButton.setOnAction(event->{
            BorderPane borderPane = (BorderPane) shareProductLayoutBorderpane.getParent();
            borderPane.setBottom(this.getToolbar());
        });
    }
    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}
