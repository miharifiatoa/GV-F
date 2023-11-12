package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.SaleArticlesGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import com.sales_management_javafx.controller.sale.SaleLayoutController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ClientEntity;
import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.*;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SellerPaymentController implements Initializable {
    @FXML private BorderPane sellerPayment;
    @FXML private VBox paymentVBox;
    @FXML private Button newPayment;
    @FXML private Button saveUnPayed;
    @FXML private Button savePayed;
    @FXML private Label priceTotal;
    @FXML private Label pay;
    @FXML private Label rest;
    @FXML private TextField clientNameTextfield;
    @FXML private TextField clientContactTextfield;
    @FXML private TextField deliveryTextfield;
    private final SaleService saleService;
    private final ClientService clientService;
    private final PaymentService paymentService;
    private final Collection<SellerPaymentBoxController> sellerPaymentBoxControllers = new HashSet<>();

    public SellerPaymentController() {
        this.clientService = new ClientService();
        this.paymentService = new PaymentService();
        this.saleService = new SaleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setNewPayment();
        savePayed.setDisable(true);
        this.priceTotal.setText(FileIO.getPriceTotal("sales.dat") + "Ar");
        NumberTextField.requireDouble(deliveryTextfield);
    }
    public void initialize(SaleEntity sale){

    }

    private void setNewPayment(){
        newPayment.setOnAction(event->{
            paymentVBox.getChildren().add(getSellerPaymentBox());
        });
    }
    private Collection<PaymentEntity> getPayments(){
        Collection<PaymentEntity> payments = new HashSet<>();
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers){
            payments.add(sellerPaymentBoxController.getPayment());
        }
        return payments;
    }
    public void updateTotal() {
        boolean isDeliveryDisabled = false;
        double totalMontant = 0.0;
        double priceTotal = FileIO.getPriceTotal("sales.dat");

        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers) {
            TextField payTextField = sellerPaymentBoxController.getPayTextField();
            if (!payTextField.getText().isEmpty()){
                isDeliveryDisabled = true;
                break;
            }
        }
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers) {
            totalMontant += sellerPaymentBoxController.getPay();
        }
        deliveryTextfield.setDisable(isDeliveryDisabled);
        if (!deliveryTextfield.getText().isEmpty()){
            rest.setText(totalMontant - (priceTotal - Double.parseDouble(deliveryTextfield.getText())) + "Ar");
        }
        else {
            rest.setText(totalMontant - priceTotal + "Ar");
        }
        pay.setText(totalMontant + "Ar");
    }
    public void setSaveTotally(){
        ClientEntity client = new ClientEntity();
        client.setName(clientNameTextfield.getText());
        client.setTelephone(clientContactTextfield.getText());
        ClientEntity clientResponse = clientService.create(client);
        SaleEntity sale = new SaleEntity();
        sale.setClient(clientResponse);
        sale.setCanceled(false);
        sale.setPayed(true);
        sale.setSaleDate(LocalDateTime.now());
        sale.setUser(SessionManager.getSession().getCurrentUser());
        SaleEntity saleResponse = saleService.toSaleArticles(sale, FileIO.readArticleFromFile("sales.dat"));
        if (saleResponse != null){
            for (PaymentEntity payment : getPayments()){
                payment.setSale(saleResponse);
                paymentService.create(payment);
            }
            Collection<ArticleEntity> articles = FileIO.readArticleFromFile("sales.dat");
            articles.clear();
            FileIO.writeTo("sales.dat",articles);
            BorderPane sellerLayoutBorderpane = (BorderPane) sellerPayment.getParent().getParent().getParent().getParent().getParent();
            sellerLayoutBorderpane.setBottom(getToolbar());
            GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(new ArticleService().getAll(),4);
            ScrollPane sellerArticleScrollpane = (ScrollPane) sellerLayoutBorderpane.lookup("#sellerArticleScrollpane");
            sellerArticleScrollpane.setContent(sellerArticleGridPane);
        }
    }
    public void setSavePartial(){
        ClientEntity client = new ClientEntity();
        client.setName(clientNameTextfield.getText());
        client.setTelephone(clientContactTextfield.getText());
        ClientEntity clientResponse = clientService.create(client);
        SaleEntity sale = new SaleEntity();
        sale.setClient(clientResponse);
        sale.setCanceled(false);
        sale.setPayed(false);
        sale.setSaleDate(LocalDateTime.now());
        sale.setUser(SessionManager.getSession().getCurrentUser());
        SaleEntity saleResponse = saleService.toSaleArticles(sale, FileIO.readArticleFromFile("sales.dat"));
        if (saleResponse != null){
            for (PaymentEntity payment : getPayments()){
                if (payment != null){
                    payment.setSale(saleResponse);
                    paymentService.create(payment);
                }
            }
            Collection<ArticleEntity> articles = FileIO.readArticleFromFile("sales.dat");
            articles.clear();
            FileIO.writeTo("sales.dat",articles);
            BorderPane sellerLayoutBorderpane = (BorderPane) sellerPayment.getParent().getParent().getParent().getParent().getParent();
            sellerLayoutBorderpane.setBottom(getToolbar());
            GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(new ArticleService().getAll(),4);
            ScrollPane sellerArticleScrollpane = (ScrollPane) sellerLayoutBorderpane.lookup("#sellerArticleScrollpane");
            sellerArticleScrollpane.setContent(sellerArticleGridPane);
        }
    }
    private GridPane getSellerPaymentBox(){
        FXMLLoader sellerPaymentBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerPaymentBox.fxml"));
        GridPane sellerPaymentBox;
        try {
            sellerPaymentBox = sellerPaymentBoxLoader.load();
            SellerPaymentBoxController sellerPaymentBoxController = sellerPaymentBoxLoader.getController();
            sellerPaymentBoxController.setSellerPaymentController(this);
            sellerPaymentBoxController.initialize(paymentVBox);
            sellerPaymentBoxControllers.add(sellerPaymentBoxController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sellerPaymentBox;
    }
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
