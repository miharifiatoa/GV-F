package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.*;
import org.sales_management.service.*;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.ResourceBundle;

public class PannierLayoutController implements Initializable {
    @FXML private BorderPane pannierLayout;
    @FXML private Button exit;
    @FXML private Button payment;
    @FXML private Label priceTotal;

    @FXML private ImageView pannierIcon;
    @FXML private ScrollPane pannierLayoutScrollpane;
    private PaymentModeEntity paymentBy;
    private SellerPaymentController sellerPaymentController;

    public PannierLayoutController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.exit.setOnAction(event->this.setExit());
        this.setArticles();
        this.setSale();
        this.setPriceTotal();
    }
    private void setPriceTotal(){
        String price = DecimalFormat.format(FileIO.getPriceTotal("sales.dat"));
        this.priceTotal.setText("Prix total : " + price +"Ar");
    }
    private void setArticles(){
        GridPane gridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("sales.dat"),2);
        pannierLayoutScrollpane.setContent(gridPane);
    }
    private void setExit(){
        BorderPane borderPane = (BorderPane) this.pannierLayout.getParent();
        borderPane.setBottom(this.getToolbar());
    }
    private void setSale(){
        payment.setOnAction(event->{
            if (!FileIO.readArticleFromFile("sales.dat").isEmpty()){
                BorderPane sellerLayout = (BorderPane) pannierLayout.getParent();
                sellerLayout.setBottom(getSellerPayment());
            }
        });
    }

    private void putIcons(){
        pannierIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PannierIcon.png"))));
    }
    private GridPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerToolbar.fxml"));
        GridPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  toolbar;
    }
    private BorderPane getSellerPayment(){
        FXMLLoader sellerPaymentLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerPayment.fxml"));
        BorderPane sellerPayment;
        try {
            sellerPayment = sellerPaymentLoader.load();
            sellerPaymentController = sellerPaymentLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  sellerPayment;
    }
}
