package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.actions.Payment;
import com.sales_management_javafx.composent.sale.SaleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.service.SaleService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SaleLayoutController implements Initializable {
    @FXML private Button exit;
    @FXML private BorderPane saleLayout;
    @FXML private ScrollPane saleLayoutScrollpane;
    @FXML private ImageView saleIcon;
    @FXML private Label payementLabel;
    @FXML private Label saleNumberLabel;
    @FXML private Label unpayedSalesLabel;
    @FXML private Label payedSalesLabel;

    private final SaleService saleService;

    public SaleLayoutController() {
        this.saleService = new SaleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnAction(event->setExit());
        this.putIcons();
        payementLabel.setText("Versement : "+ Payment.getPaymentByDay(LocalDate.now()) + " Ar");
        int size = saleService.getAcceptedSalesByDate(LocalDate.now()).size();
        if (size != 0){
            saleNumberLabel.setText("Vous avez effectué "+ size + " Vente(s)");
        }
        else {
            saleNumberLabel.setText(null);
        }
        this.setPayedSales();
        this.setPayedSalesLabel();
        this.setUnPayedSalesLabel();
    }
    public void initializeForPayed(){
        setPayedSales();
    }
    public void initializeForUnPayed(){
        setUnPayedSales();
    }
    private void setPayedSalesLabel(){
        payedSalesLabel.setOnMouseClicked(event->{
            this.setPayedSales();
        });
    }
    private void setUnPayedSalesLabel(){
        unpayedSalesLabel.setOnMouseClicked(event->{
            this.setUnPayedSales();
        });
    }

    private void setPayedSales(){
        GridPane saleGridPane = new SaleGridPane().getGridPane(saleService.getAcceptedAndPayedSalesByDate(LocalDate.now()),4);
        saleLayoutScrollpane.setContent(saleGridPane);
    }
    private void setUnPayedSales(){
        GridPane saleGridPane = new SaleGridPane().getGridPane(saleService.getAcceptedAndUnPayedSales(),4);
        saleLayoutScrollpane.setContent(saleGridPane);
    }

    private void setExit(){
        BorderPane sellerLayout = (BorderPane) saleLayout.getParent();
        sellerLayout.setBottom(this.getToolbar());
    }
    private void putIcons(){
        this.saleIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/SaleIcon.png"))));
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
}
