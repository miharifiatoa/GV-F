package com.sales_management_javafx.controller.inventory;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ProductShareGridPane;
import com.sales_management_javafx.composent.ProductVariationGridPane;
import com.sales_management_javafx.composent.ShopGridPane;
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
import org.sales_management.service.ShopService;

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
    private ImageView sendIcon;
    @FXML
    private ImageView shopIcon;
    @FXML private ImageView returnIcon;
    @FXML
    private Button closeShareListButton;
    @FXML
    private Button shareProductButton;
    @FXML
    private Button sendProductButton;
    @FXML
    private Button returnButton;
    @FXML
    private GridPane shareToolbar;
    @FXML
    private BorderPane shopLayoutBorderpane;
    private final ShopService shopService;

    public ShareProductBoxController() {
        this.shopService = new ShopService();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.shareProductLayoutBorderpane.setVisible(true);
        this.shopLayoutBorderpane.setVisible(false);
        this.shareProductButton.setDisable(true);
        this.shareToolbar.setVisible(true);
        this.putIcons();
        this.onCloseShareList();
        this.setProducts();
        this.onShareProduct();
        this.onReturnToShareList();
        this.onShowProductShareConfirmBox();
        this.setShops();
    }

    public void setProducts(){
        GridPane gridPane = new ProductShareGridPane().getGridPane(FileIO.readPricesFromFile("prices.dat"),1 );
        shareProductLayoutScrollpane.setContent(gridPane);
    }
    public void putIcons(){
        shareListIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShowShareListIcon.png"))));
        sendIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/SendIcon.png"))));
        shopIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShopIcon.png"))));
        returnIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ReturnIcon.png"))));
    }
    private void onShareProduct(){
        this.sendProductButton.setOnAction(event->{
            this.shareProductLayoutBorderpane.setVisible(false);
            this.shopLayoutBorderpane.setVisible(true);
        });
    }
    private void setShops(){
        GridPane gridPane = new ShopGridPane().getGridPane(shopService.getAll(),3);
        ScrollPane scrollPane = (ScrollPane) shopLayoutBorderpane.getCenter();
        scrollPane.setContent(gridPane);
    }
    private void onReturnToShareList(){
        this.returnButton.setOnAction(event->{
            this.shareProductLayoutBorderpane.setVisible(true);
            this.shopLayoutBorderpane.setVisible(false);
        });
    }
    private void onCloseShareList(){
        closeShareListButton.setOnAction(event->{
            BorderPane borderPane = (BorderPane) shareProductLayoutBorderpane.getParent().getParent();
            borderPane.setBottom(this.getToolbar());
        });
    }
    private void onShowProductShareConfirmBox(){
        this.shareProductButton.setOnAction(event->{
            BorderPane productBoxLayoutBorderpane = (BorderPane) shareProductLayoutBorderpane.getParent().getParent();
            productBoxLayoutBorderpane.setBottom(this.getProductShareConfirmBox());
        });
    }
    private StackPane getProductShareConfirmBox(){
        FXMLLoader productShareBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/ProductShareConfirmBox.fxml"));
        StackPane stackPane;
        try {
            stackPane = productShareBoxLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
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
    }}

