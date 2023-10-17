package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ShopEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductShareConfirmBoxController implements Initializable {
    @FXML
    private Button exit;
    @FXML
    private StackPane productShareConfirmBox;
    @FXML
    private Label confirmShareLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onExit();
        this.initialize();
    }
    public void initialize(){
        ShopEntity shop = (ShopEntity) FileIO.readFrom("ShopToSendProduct.dat");
        confirmShareLabel.setText(
            "Partage des produits vers " + shop.getName() + "\n" +
            "Remarque : les produits deja partager ne peut plus retourner dans l'inventaire.\n"+
            "Sinon , vous pouvez augmenter le quantité des produits.\n"+
            "Si vous etes sur , cliquer sur 'confirmer' sinon 'annuler.'"
        );
    }
    private void onExit(){
        this.exit.setOnAction(event->{
            BorderPane borderPane = (BorderPane) productShareConfirmBox.getParent();
            borderPane.setBottom(this.getShareProductLayout());
        });
    }
    private StackPane getShareProductLayout(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/inventory/shareProductLayout.fxml"));
        StackPane shareProductLayout;
        try {
            shareProductLayout = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shareProductLayout;
    }
}
