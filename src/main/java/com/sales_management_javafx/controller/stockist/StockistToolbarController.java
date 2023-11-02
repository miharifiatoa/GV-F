package com.sales_management_javafx.controller.stockist;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StockistToolbarController implements Initializable {
    @FXML private StackPane toolbar;
    @FXML private Button share;
    @FXML private Button arrival;
    @FXML private Button newArticle;
    @FXML private ImageView shareIcon;
    @FXML private ImageView arrivalIcon;
    @FXML private ImageView articleIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.setShare();
        this.setArrival();
        this.setNewArticle();
    }
    private void setShare(){
        share.setOnAction(event->{
            getModal().setCenter(getShareProductLayout());
            getModal().setVisible(true);
            getStockistLayoutBorderpane().setBottom(null);
        });
    }
    private void setArrival(){
        arrival.setOnAction(event->{
            getModal().setCenter(getArrivalLayout());
            getModal().setVisible(true);
            getStockistLayoutBorderpane().setBottom(null);
        });
    }
    private void setNewArticle(){
        newArticle.setOnAction(event->{
            getModal().setCenter(getProductBoxLayout());
            getModal().setVisible(true);
            getStockistLayoutBorderpane().setBottom(null);
        });
    }

    private StackPane getShareProductLayout(){
        FXMLLoader shareProductLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/share/shareProductLayout.fxml"));
        StackPane stackPane;
        try {
            stackPane = shareProductLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
    private StackPane getArrivalLayout(){
        FXMLLoader shareProductLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/arrival/arrivalLayout.fxml"));
        StackPane shareProductLayout;
        try {
            shareProductLayout = shareProductLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shareProductLayout;
    }
    private StackPane getProductBoxLayout(){
        FXMLLoader productBoxLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productBoxLayout.fxml"));
        StackPane productBoxLayout;
        try {
            productBoxLayout = productBoxLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productBoxLayout;
    }
    private BorderPane getStockistLayoutBorderpane(){
        return (BorderPane) this.toolbar.getParent();
    }

    private BorderPane getModal(){
        return (BorderPane) getStockistLayoutBorderpane().lookup("#modal");
    }
    private void putIcons(){
        shareIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShowShareListIcon.png"))));
        arrivalIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ArrivalIcon.png"))));
        articleIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ArticleIcon.png"))));
    }
}
