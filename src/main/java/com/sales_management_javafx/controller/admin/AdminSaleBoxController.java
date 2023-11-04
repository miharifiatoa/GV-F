package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.composent.SaleGridPane;
import com.sales_management_javafx.composent.SellerArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.SaleService;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminSaleBoxController implements Initializable {
    @FXML private Label saleDateLabel;
    @FXML private Label articleNumbersLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label sum;
    @FXML private VBox saleVBox;
    @FXML private StackPane saleBox;
    private final SaleService saleService;
    private final ArticleService articleService;

    public AdminSaleBoxController() {
        this.articleService = new ArticleService();
        this.saleService = new SaleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.saleVBox.setVisible(true);
    }
    public void initialize(SaleEntity sale){
        sum.setText("Prix total : " + getSum(sale) + " Ar");
        saleDateLabel.setText(String.valueOf(sale.getSaleDate()));
        descriptionLabel.setText("Payement par : " + sale.getDescription());
        articleNumbersLabel.setText(sale.getUser().getAccount().getUsername() + " a vendu " + getTotalSize(sale) + " produit(s) au client : " + sale.getClientName());
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
}
