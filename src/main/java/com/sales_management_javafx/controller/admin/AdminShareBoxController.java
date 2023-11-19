package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.classes.DateTimeFormatter;
import com.sales_management_javafx.composent.admin.AdminShareInfoGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminShareBoxController implements Initializable {
    @FXML private StackPane shareBox;
    @FXML private VBox shareVBox;
    @FXML private Label articleNumbersLabel;
    @FXML private Label shareDateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ShareEntity share){
        if (share.isCanceled()){
            articleNumbersLabel.setDisable(true);
            articleNumbersLabel.getStyleClass().add("canceled-text");
            articleNumbersLabel.setText(
                    share.getUser().getAccount().getUsername()
                    + " a annulé le partage de "
                    + getTotalSize(share)
                    + " produit(s) vers le boutique : "
                    + share.getShop().getName() + " à "
                    + share.getShop().getAddress() + " qui a partagé le : ");
        }
        else {
            articleNumbersLabel.setText(share.getUser().getAccount().getUsername() + " a partagé " + getTotalSize(share) + " produit(s) vers le boutique : " + share.getShop().getName() + " à " + share.getShop().getAddress() + " le : ");
        }
        shareDateLabel.setText(DateTimeFormatter.format(share.getShareDate()));
        this.setShareVBox(share);
    }
    private void setShareVBox(ShareEntity share){
        shareVBox.setOnMouseClicked(event->{
            GridPane adminShareInfoGridPane = new AdminShareInfoGridPane().getGridPane(share,2);
            getDashboardLayoutScrollpane().setContent(adminShareInfoGridPane);
        });
    }
    private ScrollPane getDashboardLayoutScrollpane(){
        return (ScrollPane) shareBox.getParent().getParent().getParent().getParent();
    }
    private int getTotalSize(ShareEntity share){
        int total = 0;
        for (ShareArticleEntity shareArticle : share.getShareArticles()){
            total += shareArticle.getQuantity();
        }
        return total;
    }
}
