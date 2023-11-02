package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.SaleArticleService;
import org.sales_management.service.SaleService;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;

public class PannierLayoutController implements Initializable {
    @FXML private BorderPane pannierLayout;
    @FXML private Button exit;
    @FXML private Button sale;
    @FXML private Label priceTotal;
    @FXML private Label reimbursementLabel;
    @FXML private TextField clientNameTextfield;
    @FXML private TextField totalPayedTextfield;

    @FXML private ImageView pannierIcon;
    @FXML private ScrollPane pannierLayoutScrollpane;

    private final ArticleService articleService;
    private final SaleService saleService;
    private final SaleArticleService saleArticleService;

    public PannierLayoutController() {
        this.saleArticleService = new SaleArticleService();
        this.saleService = new SaleService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NumberTextField.requireDouble(this.totalPayedTextfield);
        this.reimbursementLabel.setText(null);
        this.putIcons();
        this.exit.setOnAction(event->this.setExit());
        this.setArticles();
        this.formValidation();
        this.setSale();
        this.setPriceTotal();
    }
    private void setPriceTotal(){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String price = decimalFormat.format(FileIO.getPriceTotal("sales.dat"));
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
        sale.setOnAction(event->{
            SaleEntity saleEntity = new SaleEntity();
            saleEntity.setClientName(clientNameTextfield.getText());
            saleEntity.setSaleDate(LocalDateTime.now());
            saleEntity.setDescription("test");
            saleEntity.setCanceled(false);
            if (saleService.toSaleArticles(saleEntity,FileIO.readArticleFromFile("sales.dat")) != null){
                Collection<ArticleEntity> articles = FileIO.readArticleFromFile("sales.dat");
                articles.clear();
                FileIO.writeTo("sales.dat",articles);
                GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleService.getAll(),4);
                ScrollPane sellerArticleScrollpane = (ScrollPane) pannierLayout.getParent().lookup("#sellerArticleScrollpane");
                sellerArticleScrollpane.setContent(sellerArticleGridPane);
                this.setExit();
            }
        });
    }
    private void formValidation(){
        if (clientNameTextfield.getText().isEmpty() || totalPayedTextfield.getText().isEmpty()){
            sale.setDisable(true);
        }
        clientNameTextfield.textProperty().addListener(event->{
            if(!clientNameTextfield.getText().isEmpty() && !totalPayedTextfield.getText().isEmpty()){
                if (Double.parseDouble(totalPayedTextfield.getText())< FileIO.getPriceTotal("sales.dat")){
                    sale.setDisable(true);
                }
                else {
                    sale.setDisable(false);
                }
            }
            else {
                sale.setDisable(true);
            }
        });
        totalPayedTextfield.textProperty().addListener(event->{
            if(!totalPayedTextfield.getText().isEmpty() && !clientNameTextfield.getText().isEmpty() && FileIO.getPriceTotal("sales.dat") != 0){
                if (Double.parseDouble(totalPayedTextfield.getText())< FileIO.getPriceTotal("sales.dat")){
                    sale.setDisable(true);
                    reimbursementLabel.setText("Manque : " + (Double.parseDouble(totalPayedTextfield.getText()) - FileIO.getPriceTotal("sales.dat")) + "Ar");
                }
                else{
                    sale.setDisable(false);
                    reimbursementLabel.setText("Reste : " + (Double.parseDouble(totalPayedTextfield.getText()) - FileIO.getPriceTotal("sales.dat")) + "Ar");
                }
            }
            else {
                sale.setDisable(true);
                reimbursementLabel.setText(null);
            }
        });
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
    private void putIcons(){
        pannierIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PannierIcon.png"))));
    }
}
