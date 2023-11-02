package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.FactureGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class FactureController implements Initializable {
    @FXML private VBox facture;
    @FXML private ScrollPane factureScrollpane;
    @FXML private Button exit;
    @FXML private Button print;
    @FXML private ImageView printIcon;
    @FXML private ImageView exitIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.setExit();
        this.setPrint();
    }
    public void initialize(SaleEntity sale){
        this.setArticles(sale);
    }
    private void setArticles(SaleEntity sale){
        GridPane factureGridPane = new FactureGridPane().getGridPane(sale,1);
            factureScrollpane.setContent(factureGridPane);
    }
    private void setExit(){
        exit.setOnAction(event->{
            facture.setVisible(false);
        });
    }
    private void putIcons(){
        this.printIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PrintIcon.png"))));
        this.exitIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/Exit" + "Icon.png"))));
    }

    private void setPrint(){
        print.setOnAction(event->{
            print(factureScrollpane);
        });
    }
    public static void print(ScrollPane scrollPane) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(null)) {
            boolean success = printerJob.printPage(scrollPane);
            if (success) {
                printerJob.endJob();
            }
        }
    }

}
