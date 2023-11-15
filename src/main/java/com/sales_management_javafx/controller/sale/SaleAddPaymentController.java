package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.controller.seller.SellerPaymentBoxController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.PaymentService;
import org.sales_management.service.SaleService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SaleAddPaymentController implements Initializable {
    @FXML private Label clientNameLabel;
    @FXML private Label clientContactLabel;
    @FXML private Label restToPay;
    @FXML private Label delivery;
    @FXML private Label pay;
    @FXML private Label rest;
    @FXML private Button newPayment;
    @FXML private Button savePayment;
    @FXML private Button saveSale;
    @FXML private Button exit;
    @FXML private VBox paymentVBox;
    @FXML private BorderPane sellerPayment;
    private final PaymentService paymentService;
    private final SaleService saleService;
    private final Collection<SellerPaymentBoxController> sellerPaymentBoxControllers = new HashSet<>();
    private SaleLayoutController saleLayoutController;

    public SaleAddPaymentController() {
        this.saleService = new SaleService();
        this.paymentService = new PaymentService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.saveSale.setDisable(true);
        this.savePayment.setDisable(true);
        this.setExit();
    }
    public void initialize(SaleEntity sale){
        clientNameLabel.setText("Client : " + sale.getClient().getName());
        clientContactLabel.setText("Contact : " + sale.getClient().getTelephone());
        restToPay.setText("Reste a paye : " + getRestToPay(sale) + "Ar");
        this.setSavePayment(sale);
        this.setSaveSale(sale);
        this.setPayTextField(sale);
        this.setNewPayment(sale);
    }
    public void setPayTextField(SaleEntity sale){
        boolean isEmpty = false;
        double pay = 0.0;
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
        if (isEmpty){
            if (pay == getRestToPay(sale)){
                saveSale.setDisable(false);
                savePayment.setDisable(true);
            }
            else {
                saveSale.setDisable(true);
                savePayment.setDisable(!(pay < getRestToPay(sale)));
            }
        }
        else {
            saveSale.setDisable(true);
            savePayment.setDisable(true);
        }
        rest.setText("Reste : " + DecimalFormat.format(pay - getRestToPay(sale)) + "Ar");
        this.pay.setText("Pay : " + DecimalFormat.format(pay) + "Ar");
    }
    private void setNewPayment(SaleEntity sale){
        newPayment.setOnAction(event->{
            paymentVBox.getChildren().add(this.getSellerPaymentBox(sale));
        });
    }

    private Collection<PaymentEntity> getPayments(){
        Collection<PaymentEntity> payments = new HashSet<>();
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers){
            payments.add(sellerPaymentBoxController.getPayment());
        }
        return payments;
    }
    private void setSavePayment(SaleEntity sale){
        savePayment.setOnAction(event->{
            savePayment(sale);
            refresh();
            saleLayoutController.initializeForUnPayed();
        });
    }
    private void setSaveSale(SaleEntity sale){
        saveSale.setOnAction(event->{
            this.saveSale(sale);
            refresh();
            saleLayoutController.initializeForPayed();
        });
    }
    private void refresh(){
        BorderPane sellerLayout = (BorderPane) sellerPayment.getParent();
        sellerLayout.setBottom(getSaleLayout());
    }
    private void savePayment(SaleEntity sale){
        for (PaymentEntity payment : getPayments()){
            payment.setSale(sale);
            paymentService.create(payment);
        }
    }
    private void saveSale(SaleEntity sale){
        sale.setPayed(true);
        SaleEntity saleEntity = saleService.update(sale);
        for (PaymentEntity payment : getPayments()){
            payment.setSale(saleEntity);
            paymentService.create(payment);
        }
    }
    private double getRestToPay(SaleEntity sale){
        return (getPriceTotal(sale) - sale.getDelivery()) - getPayed(sale);
    }
    private double getPayed(SaleEntity sale){
        double payed = 0.0;
        for (PaymentEntity payment : sale.getPayments()){
            payed += payment.getPay();
        }
        return payed;
    }
    private double getPriceTotal(SaleEntity sale){
        double priceTotal = 0.0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
            priceTotal += saleArticle.getArticle().getPrice();
        }
        return priceTotal;
    }
    private void setExit(){
        exit.setOnAction(event->{
            BorderPane sellerLayout = (BorderPane) sellerPayment.getParent();
            sellerLayout.setBottom(getSaleLayout());
            saleLayoutController.initializeForUnPayed();
        });
    }
    private GridPane getSellerPaymentBox(SaleEntity sale){
        FXMLLoader sellerPaymentBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerPaymentBox.fxml"));
        GridPane sellerPaymentBox;
        try {
            sellerPaymentBox = sellerPaymentBoxLoader.load();
            SellerPaymentBoxController sellerPaymentBoxController = sellerPaymentBoxLoader.getController();
            sellerPaymentBoxController.setSaleAddPaymentController(this);
            sellerPaymentBoxController.initialize(paymentVBox);
            sellerPaymentBoxController.initialize(sale);
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
            this.saleLayoutController = saleLayoutLoader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return saleLayout;
    }
}
