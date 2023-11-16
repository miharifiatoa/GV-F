package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.actions.Payment;
import com.sales_management_javafx.classes.DateTimeFormatter;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.composent.sale.SaleGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import com.sales_management_javafx.composent.admin.AdminSaleInfoGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.SaleService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SaleBoxController implements Initializable {
    @FXML private Label  saleText;
    @FXML private Label cancelText;
    @FXML private Label sum;
    @FXML private Label payed;
    @FXML private Label rest;
    @FXML private Label addPayment;
    @FXML private Button save;
    @FXML private Button exit;
    @FXML private Button cancel;
    @FXML private Button facture;
    @FXML private VBox saleVBox;
    @FXML private VBox cancelSaleVBox;
    @FXML private HBox addPaymentHBox;
    @FXML private HBox saleHBox;
    @FXML private StackPane saleBox;
    private final SaleService saleService;
    private final ArticleService articleService;

    public SaleBoxController() {
        this.articleService = new ArticleService();
        this.saleService = new SaleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.saleVBox.setVisible(true);
        this.cancelSaleVBox.setVisible(false);
        this.cancel.setOnAction(event->this.setCancel());
        this.exit.setOnAction(event->this.setExit());
    }
    public void initializeForSale(SaleEntity sale){
        this.setSave(sale);
        this.initialize(sale);
        this.setAddPayment(sale);
        this.saleText.setText("Vous avez vendu " + getTotalSize(sale) + " produit(s) au client , " + sale.getClient().getName() + " " + " le : " + DateTimeFormatter.format(sale.getSaleDate()));
        this.cancelText.setText("Voulez vous vraiment annuler cette vente des produits au client : " + sale.getClient().getName());
        if (sale.getPayed()){
            addPaymentHBox.getChildren().remove(addPayment);
        }
    }
    public void initializeForAdmin(SaleEntity sale){
        saleText.setOnMouseClicked(event->{
            GridPane adminSaleInfoGridPane = new AdminSaleInfoGridPane().getGridPane(sale,1);
            ScrollPane dashboardLayoutScrollpane = (ScrollPane) saleBox.getParent().getParent().getParent().getParent();
            dashboardLayoutScrollpane.setContent(adminSaleInfoGridPane);
        });
        saleHBox.getChildren().remove(cancel);
        addPaymentHBox.getChildren().remove(addPayment);
        initialize(sale);
        this.saleText.setText(sale.getUser().getAccount().getUsername() + " a vendu " + getTotalSize(sale) + " produit(s) au client , " + sale.getClient().getName() + " " + " le : " + DateTimeFormatter.format(sale.getSaleDate()));
    }
    private void initialize(SaleEntity sale){
        this.setFacture(sale);
        this.sum.setText(DecimalFormat.format(Payment.getTotalToPay(sale) - sale.getDelivery()) + "Ar");
        this.payed.setText(DecimalFormat.format(Payment.getPayed(sale)) + "Ar");
        this.rest.setText(DecimalFormat.format(Payment.getTotalToPay(sale) - sale.getDelivery() - Payment.getPayed(sale)) + "Ar");
    }
    private void setCancel(){
        this.saleVBox.setVisible(false);
        this.cancelSaleVBox.setVisible(true);
    }
    private void setFacture(SaleEntity sale){
        facture.setOnAction(event -> {
            saleBox.getChildren().add(getFacture(sale));
        });
    }
    private void setExit(){
        this.saleVBox.setVisible(true);
        this.cancelSaleVBox.setVisible(false);
    }
    private void setSave(SaleEntity sale){
        save.setOnAction(event->{
            if (saleService.toCancelSale(sale) != null){
                BorderPane sellerLayout = (BorderPane) getSaleLayoutScrollpane().getParent().getParent();
                ScrollPane sellerArticleScrollpane = (ScrollPane) sellerLayout.lookup("#sellerArticleScrollpane");
                Label payementLabel = (Label) sellerLayout.lookup("#payementLabel");
                Label saleNumberLabel = (Label) sellerLayout.lookup("#saleNumberLabel");
                payementLabel.setText("Versement : " + Payment.getPaymentByDay(LocalDate.now()) + " Ar");
                int size = new SaleService().getAcceptedSalesByDate(LocalDate.now()).size();
                if (size != 0){
                    saleNumberLabel.setText("Vous avez effectué "+ size + " Vente(s)");
                }
                else {
                    saleNumberLabel.setText(null);
                }
                GridPane gridPane;
                if (sale.getPayed()){
                    gridPane = new SaleGridPane().getGridPane(saleService.getAcceptedAndPayedSalesByDate(LocalDate.now()),4);
                }
                else {
                    gridPane = new SaleGridPane().getGridPane(saleService.getAcceptedAndUnPayedSales(),4);
                }
                GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleService.getAll(),4);
                sellerArticleScrollpane.setContent(sellerArticleGridPane);
                getSaleLayoutScrollpane().setContent(gridPane);
            }
        });
    }
    private int getTotalSize(SaleEntity sale){
        int total = 0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
            total += saleArticle.getQuantity();
        }
        return total;
    }
    private void setAddPayment(SaleEntity sale){
        addPayment.setOnMouseClicked(event->{
            BorderPane sellerLayoutBorderpane = (BorderPane) saleBox.getParent().getParent().getParent().getParent().getParent().getParent();
            sellerLayoutBorderpane.setBottom(getSaleAddPayment(sale));
        });
    }

    private ScrollPane getSaleLayoutScrollpane(){
        return (ScrollPane) saleBox.getParent().getParent().getParent().getParent();
    }
    private VBox getFacture(SaleEntity sale){
        FXMLLoader factureLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/facture.fxml"));
        VBox facture;
        try {
            facture = factureLoader.load();
            FactureController factureController = factureLoader.getController();
            factureController.initialize(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  facture;
    }
    private BorderPane getSaleAddPayment(SaleEntity sale){
        FXMLLoader saleAddPaymentLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/sale/saleAddPayment.fxml"));
        BorderPane saleAddPayment;
        try {
            saleAddPayment = saleAddPaymentLoader.load();
            SaleAddPaymentController saleAddPaymentController = saleAddPaymentLoader.getController();
            saleAddPaymentController.initialize(sale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return saleAddPayment;
    }
}
