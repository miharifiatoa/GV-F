package com.sales_management_javafx.controller.seller;

import com.sales_management_javafx.SalesApplication;
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
import java.text.DecimalFormat;
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

    private final ArticleService articleService;
    private final SaleService saleService;
    private final SaleArticleService saleArticleService;
    private final PaymentModeService paymentModeService;
    private final PersonService personService;
    private final ClientService clientService;
    private final UserEntity user;
    private PaymentModeEntity paymentBy;

    public PannierLayoutController() {
        this.personService = new PersonService();
        this.clientService = new ClientService();
        this.paymentModeService = new PaymentModeService();
        this.user = SessionManager.getSession().getCurrentUser();
        this.saleArticleService = new SaleArticleService();
        this.saleService = new SaleService();
        this.articleService = new ArticleService();
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
        payment.setOnAction(event->{
            pannierLayoutScrollpane.setContent(getSellerPayment());
            pannierLayout.setBottom(null);

//            if (Objects.equals(sale.getText(), "Vente a credit")){
//
//            }
//            else {
//                PersonEntity person = new PersonEntity();
//                person.setLastname(String.valueOf(clientNameTextfield));
//                personService.create(person);
//                SaleEntity saleEntity = new SaleEntity();
//                saleEntity.setSaleDate(LocalDateTime.now());
//                saleEntity.setDescription("");
//                saleEntity.setCanceled(false);
//                if (user != null){
//                    saleEntity.setUser(user);
//                }
//                SaleEntity response = saleService.toSaleArticles(saleEntity,FileIO.readArticleFromFile("sales.dat"));
//                if (response != null){
//                    ClientEntity client = new ClientEntity();
//                    client.setName(clientNameTextfield.getText());
//                    Collection<ArticleEntity> articles = FileIO.readArticleFromFile("sales.dat");
//                    articles.clear();
//                    FileIO.writeTo("sales.dat",articles);
//                    GridPane sellerArticleGridPane = new SellerArticleGridPane().getGridPane(articleService.getAll(),4);
//                    ScrollPane sellerArticleScrollpane = (ScrollPane) pannierLayout.getParent().lookup("#sellerArticleScrollpane");
//                    sellerArticleScrollpane.setContent(sellerArticleGridPane);
//                    this.setExit();
//                }
//            }

        });
    }
//    private void formValidation(){
//        sale.setText("Vente a credit");
//        clientNameTextfield.textProperty().addListener(event->{
//
//        });
//        totalPayedTextfield.textProperty().addListener(event->{
//            if(!totalPayedTextfield.getText().isEmpty() && FileIO.getPriceTotal("sales.dat") != 0){
//                if (Double.parseDouble(totalPayedTextfield.getText())< FileIO.getPriceTotal("sales.dat")){
//                    reimbursementLabel.setText("Manque : " + (Double.parseDouble(totalPayedTextfield.getText()) - FileIO.getPriceTotal("sales.dat")) + "Ar");
//                    sale.setText("Vente a credit");
//                }
//                else{
//                    sale.setText("Vendez les produits");
//                    reimbursementLabel.setText("Reste : " + (Double.parseDouble(totalPayedTextfield.getText()) - FileIO.getPriceTotal("sales.dat")) + "Ar");
//                }
//            }
//            else {
//                reimbursementLabel.setText(null);
//            }
//        });
    //    }
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
    private StackPane getSellerPayment(){
        FXMLLoader sellerPaymentLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerPayment.fxml"));
        StackPane sellerPayment;
        try {
            sellerPayment = sellerPaymentLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  sellerPayment;
    }
}
