package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DateTimeFormatter;
import com.sales_management_javafx.composent.SaleGridPane;
import com.sales_management_javafx.composent.SalePaymentBoxGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.SaleService;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SaleBoxController implements Initializable {
    @FXML private Label  saleText;
    @FXML private Label cancelText;
    @FXML private Button save;
    @FXML private Button exit;
    @FXML private Button cancel;
    @FXML private Button facture;
    @FXML private VBox saleVBox;
    @FXML private VBox cancelSaleVBox;
    @FXML private StackPane saleBox;
    @FXML private ScrollPane paymentBoxScrollpane;
    private final SaleService saleService;
    private final ArticleService articleService;
    private final UserEntity user;

    public SaleBoxController() {
        this.user = SessionManager.getSession().getCurrentUser();
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
    public void initialize(SaleEntity sale){
        this.setSave(sale);
//        sum.setText("Total : " + getSum(sale) + " Ar");
        saleText.setText("Vous avez vendu " + getTotalSize(sale)
                + " produit(s) au client , "
                + sale.getClient().getName()
                + " "
                + " le : "
                + DateTimeFormatter.format(sale.getSaleDate()));
        cancelText.setText("Voulez vous vraiment annuler cette vente des produits au client : "
                + sale.getClient().getName());
        this.setFacture(sale);
        this.setPaymentBoxScrollpane(sale);
    }
    private void setPaymentBoxScrollpane(SaleEntity sale){
        GridPane salePaymentBoxGridPane = new SalePaymentBoxGridPane().getGridPane(sale,1);
        paymentBoxScrollpane.setContent(salePaymentBoxGridPane);
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
                payementLabel.setText("Versement : " + this.getMoneyTotalByDay() + " Ar");
                int size = new SaleService().getAcceptedAndPayedOrUnPayedSalesByDate(LocalDate.now(),true).size();
                if (size != 0){
                    saleNumberLabel.setText("Vous avez effectué "+ size + " Vente(s)");
                }
                else {
                    saleNumberLabel.setText(null);
                }
                GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleService.getAll(),4);
                GridPane saleGridPane = new SaleGridPane().getGridPane(saleService.getAcceptedAndPayedOrUnPayedSalesByDate(LocalDate.now(),true),4);
                sellerArticleScrollpane.setContent(sellerArticleGridPane);
                getSaleLayoutScrollpane().setContent(saleGridPane);
            }
        });
    }
    private String getMoneyTotalByDay(){
        double money = 0;
        for (SaleEntity sale : saleService.getAcceptedAndPayedOrUnPayedSalesByDate(LocalDate.now(),true)){
            for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
                money += saleArticle.getArticle().getPrice() * saleArticle.getQuantity();
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(money);
    }
    private int getTotalSize(SaleEntity sale){
        int total = 0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
            total += saleArticle.getQuantity();
        }
        return total;
    }
    private String getSum(SaleEntity sale){
        double sum = 0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
            sum += saleArticle.getArticle().getPrice() * saleArticle.getQuantity();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(sum);
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
}
