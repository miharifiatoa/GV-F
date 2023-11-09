package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    @FXML private VBox paymentVBox;
    @FXML private Button newPayment;
    @FXML private Button save;
    @FXML private TextField clientNameTextfield;
    @FXML private TextField clientContactTextfield;
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
        this.setSave();
    }

    private void setNewPayment(){
        newPayment.setOnAction(event->{
            paymentVBox.getChildren().add(getSellerPaymentBox());
        });
    }
    private GridPane getSellerPaymentBox(){
        FXMLLoader sellerPaymentBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerPaymentBox.fxml"));
        GridPane sellerPaymentBox;
        try {
            sellerPaymentBox = sellerPaymentBoxLoader.load();
            SellerPaymentBoxController sellerPaymentBoxController = sellerPaymentBoxLoader.getController();
            sellerPaymentBoxControllers.add(sellerPaymentBoxController);
            sellerPaymentBoxController.initialize(paymentVBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sellerPaymentBox;
    }
    private Collection<PaymentEntity> getPayments(){
        Collection<PaymentEntity> payments = new HashSet<>();
        for (SellerPaymentBoxController sellerPaymentBoxController : sellerPaymentBoxControllers){
            payments.add(sellerPaymentBoxController.getPayment());
        }
        return payments;
    }
    private void setSave(){
        save.setOnAction(event->{
            ClientEntity client = new ClientEntity();
            client.setName(clientNameTextfield.getText());
            client.setTelephone(clientContactTextfield.getText());
            ClientEntity clientResponse = clientService.create(client);
            SaleEntity sale = new SaleEntity();
            sale.setClient(clientResponse);
            sale.setCanceled(false);
            sale.setSaleDate(LocalDateTime.now());
            sale.setUser(SessionManager.getSession().getCurrentUser());
            SaleEntity saleResponse = saleService.toSaleArticles(sale, FileIO.readArticleFromFile("sales.dat"));
            for (PaymentEntity payment : getPayments()){
                payment.setSale(saleResponse);
                paymentService.create(payment);
            }
        });
    }
}
