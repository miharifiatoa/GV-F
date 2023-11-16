package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import com.sales_management_javafx.controller.sale.SaleLayoutController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.sales_management.entity.ArticleTypeEntity;
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
    @FXML private Button exit;
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
    private SaleLayoutController saleLayoutController;
    private double delivery = 0.0;

    public SellerPaymentController() {
        this.clientService = new ClientService();
        this.paymentService = new PaymentService();
        this.saleService = new SaleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setNewPayment();
        this.priceTotal.setText("Prix total : "+FileIO.getPriceTotal("sales.dat") + "Ar");
        this.setSavePayed();
        this.setSaveUnPayed();
        this.setDelivery();
        this.setExit();
        savePayed.setDisable(true);
//        saveUnPayed.setDisable(true);
        NumberTextField.requireDouble(deliveryTextfield);
        NumberTextField.requireIntegerOnly(clientContactTextfield,999999999);
    }
    public void initialize(SaleEntity sale){
        deliveryTextfield.setText(String.valueOf(sale.getDelivery()));
        clientNameTextfield.setText(sale.getClient().getName());
        clientContactTextfield.setText(sale.getClient().getTelephone());
    }

    private void setNewPayment(){
        newPayment.setOnAction(event->{
            paymentVBox.getChildren().add(getSellerPaymentBox());
        });
    }
    private void setExit(){
        exit.setOnAction(event->{
            BorderPane sellerLayout = (BorderPane) sellerPayment.getParent();
            sellerLayout.setBottom(getPannierLayout());

        });
    }
    private Collection<PaymentEntity> getPayments(){
        Collection<PaymentEntity> payments = new HashSet<>();
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers){
            payments.add(sellerPaymentBoxController.getPayment());
        }
        return payments;
    }
    public void setPayTextField() {
        boolean isEmpty = false;
        double pay = 0.0;
        double priceTotal = FileIO.getPriceTotal("sales.dat");
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers) {
            TextField payTextField = sellerPaymentBoxController.getPayTextField();
            if (!payTextField.getText().isEmpty()){
                isEmpty = true;
                break;
            }
        }
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers) {
            pay += sellerPaymentBoxController.getPay();
        }
        double totalToPay = priceTotal - delivery;
        this.deliveryTextfield.setDisable(isEmpty);
        this.savePayed.setDisable(!(totalToPay == pay));
        this.saveUnPayed.setDisable(!(totalToPay >= pay));
        this.rest.setText("Reste : " + DecimalFormat.format(pay - (totalToPay)) + "Ar");
        this.pay.setText("Payé  : " + DecimalFormat.format(pay) + "Ar");
    }
    private void setSavePayed(){
        savePayed.setOnAction(event->{
            if (!clientNameTextfield.getText().isEmpty()){
                savePayed();
            }
            else {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO , e->{
                            clientNameTextfield.setPromptText("Ce champ ne peut pas etre vide");
                        }),
                        new KeyFrame(Duration.seconds(2), e -> {
                            clientNameTextfield.setPromptText(null);
                        })
                );
                timeline.play();
            }
        });
    }
    private void setSaveUnPayed(){
        saveUnPayed.setOnAction(event->{
            if (!clientNameTextfield.getText().isEmpty() && !clientContactTextfield.getText().isEmpty()){
                saveUnPayed();
            }
            else {
                if (clientNameTextfield.getText().isEmpty()){
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.ZERO , e->{
                                clientNameTextfield.setPromptText("Ce champ ne peut pas etre vide");
                            }),
                            new KeyFrame(Duration.seconds(2), e -> {
                                clientNameTextfield.setPromptText(null);
                            })
                    );
                    timeline.play();
                }
                if (clientContactTextfield.getText().isEmpty()){
                    Timeline timeline = new Timeline(
                            new KeyFrame(Duration.ZERO , e->{
                                clientContactTextfield.setPromptText("Ce champ ne peut pas etre vide");
                            }),
                            new KeyFrame(Duration.seconds(2), e -> {
                                clientContactTextfield.setPromptText(null);
                            })
                    );
                    timeline.play();
                }
            }
        });
    }
    private void setDelivery(){
        deliveryTextfield.textProperty().addListener(event->{
            if (!deliveryTextfield.getText().isEmpty()){
                try {
                    delivery = Double.parseDouble(deliveryTextfield.getText());
                } catch (NumberFormatException e) {
                    delivery = 0.0;
                }
            }
        });
    }

    public void savePayed(){
        SaleEntity sale = setSale();
        sale.setPayed(true);
        this.save(sale);
        saleLayoutController.initializeForPayed();
    }
    public void saveUnPayed(){
        SaleEntity sale = setSale();
        sale.setPayed(false);
        this.save(sale);
        saleLayoutController.initializeForUnPayed();
    }
    private void save(SaleEntity sale){
        SaleEntity saleResponse = saleService.toSaleArticles(sale, FileIO.readArticleFromFile("sales.dat"));
        if (saleResponse != null){
            ClientEntity client = clientService.getByPhone(getClient().getTelephone());
            if (client != null) {
                sale.setClient(client);
                saleService.update(sale);
            } else {
                ClientEntity clientEntity = clientService.create(getClient());
                sale.setClient(clientEntity);
                saleService.update(sale);
            }
            for (PaymentEntity payment : getPayments()){
                if (payment != null){
                    payment.setSale(sale);
                    paymentService.create(payment);
                }
            }
            this.refreshData();
        }
    }
    private SaleEntity setSale(){
        SaleEntity sale = new SaleEntity();
        sale.setCanceled(false);
        sale.setPayed(false);
        sale.setSaleDate(LocalDateTime.now());
        sale.setDelivery(delivery);
        sale.setUser(SessionManager.getSession().getCurrentUser());
        return sale;
    }
    private ClientEntity getClient(){
        ClientEntity client = new ClientEntity();
        client.setName(clientNameTextfield.getText());
        client.setTelephone(clientContactTextfield.getText());
        return client;
    }
    private void refreshData(){
        Collection<ArticleTypeEntity> articles = FileIO.readArticleFromFile("sales.dat");
        articles.clear();
        FileIO.writeTo("sales.dat",articles);
        BorderPane sellerLayoutBorderpane = (BorderPane) sellerPayment.getParent();
        sellerLayoutBorderpane.setBottom(getSaleLayout());
        GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(new ArticleTypeService().getAll(),4);
        ScrollPane sellerArticleScrollpane = (ScrollPane) sellerLayoutBorderpane.lookup("#sellerArticleScrollpane");
        sellerArticleScrollpane.setContent(sellerArticleGridPane);
    }
    private GridPane getSellerPaymentBox(){
        FXMLLoader sellerPaymentBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerPaymentBox.fxml"));
        GridPane sellerPaymentBox;
        try {
            sellerPaymentBox = sellerPaymentBoxLoader.load();
            SellerPaymentBoxController sellerPaymentBoxController = sellerPaymentBoxLoader.getController();
            sellerPaymentBoxController.setSellerPaymentController(this);
            sellerPaymentBoxController.initialize(paymentVBox);
            sellerPaymentBoxController.initialize(new SaleEntity());
            sellerPaymentBoxControllers.add(sellerPaymentBoxController);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sellerPaymentBox;
    }
    private BorderPane getSaleLayout(){
        FXMLLoader saleLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/saleLayout.fxml"));
        BorderPane saleLayout;
        try {
            saleLayout = saleLayoutLoader.load();
            saleLayoutController = saleLayoutLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return saleLayout;
    }
    private BorderPane getPannierLayout(){
        FXMLLoader pannierLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/pannierLayout.fxml"));
        BorderPane pannierLayout;
        try {
            pannierLayout = pannierLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pannierLayout;
    }
}
