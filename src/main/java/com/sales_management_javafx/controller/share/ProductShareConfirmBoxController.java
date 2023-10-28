package com.sales_management_javafx.controller.share;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class ProductShareConfirmBoxController implements Initializable {
    @FXML
    private Button exit;
    @FXML
    private Button confirm;
    @FXML
    private StackPane productShareConfirmBox;
    @FXML
    private Label confirmShareLabel;
    private final ArticleService articleService;

    public ProductShareConfirmBoxController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onExit();
        this.onConfirm();
        this.initialize();
    }
    public void initialize(){
        ShopEntity shop = (ShopEntity) FileIO.readFrom("shop.dat");
        confirmShareLabel.setText(
            "Partage des produits vers " + shop.getName() + "\n" +
            "Remarque : les produits deja partager ne peut plus retourner dans l'inventaire.\n"+
            "Sinon , vous pouvez augmenter le quantité des produits.\n"+
            "Si vous etes sur , cliquer sur 'confirmer' sinon 'annuler.'"
        );
    }
    private void onExit(){
        this.exit.setOnAction(event->{
            System.out.println(productShareConfirmBox.getParent().getParent().getParent().getParent());
            BorderPane productBoxLayoutBorderpane = (BorderPane) productShareConfirmBox.getParent().getParent().getParent().getParent();
            ScrollPane productBoxLayoutScrollpane = (ScrollPane) productBoxLayoutBorderpane.lookup("#productBoxLayoutScrollpane");
            productBoxLayoutScrollpane.setContent(this.getShareProductLayout());
        });
    }
    private void onConfirm(){
        this.confirm.setOnAction(event->{
            ShopEntity shop = (ShopEntity) FileIO.readFrom("shop.dat");
            Collection<ArticleEntity> priceVariations = FileIO.readArticleFromFile("prices.dat");
            BorderPane borderPane = (BorderPane) productShareConfirmBox.getParent().getParent().getParent().getParent();
            borderPane.setBottom(this.getToolbar());
        });
    }
    private StackPane getShareProductLayout(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/shareProductLayout.fxml"));
        StackPane shareProductLayout;
        try {
            shareProductLayout = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shareProductLayout;
    }
    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/product/productToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}
