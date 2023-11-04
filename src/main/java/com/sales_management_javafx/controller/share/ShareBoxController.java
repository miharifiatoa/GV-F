package com.sales_management_javafx.controller.share;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;
import org.sales_management.service.ShareService;

import java.net.URL;
import java.util.ResourceBundle;

public class ShareBoxController implements Initializable {
    @FXML private Label text;
    @FXML private Label cancelText;
    @FXML private Label dateLabel;
    @FXML private Button cancel;
    @FXML private Button exit;
    @FXML private Button save;
    @FXML private StackPane shareBox;
    @FXML private VBox shareVBox;
    @FXML private VBox cancelShareVBox;
    private final ShareService shareService;

    public ShareBoxController() {
        this.shareService = new ShareService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shareVBox.setVisible(true);
        cancelShareVBox.setVisible(false);
        this.setCancel();
        this.setExit();
    }
    public void initialize(ShareEntity share){
        text.setText("Vous avez partagé " + sum(share) + " produit(s) vers le boutique : " + share.getShop().getName() + " à " + share.getShop().getAddress());
        dateLabel.setText(String.valueOf(share.getShareDate()));
        cancelText.setText("Voulez vous vraiment annuler cet partage ?");
        this.setSave(share);
    }
    private int sum(ShareEntity share){
        int sum = 0;
        for (ShareArticleEntity shareArticle : share.getShareArticles()){
            sum += shareArticle.getQuantity();
        }
        return sum;
    }
    private void setCancel(){
        cancel.setOnAction(event->{
            shareVBox.setVisible(false);
            cancelShareVBox.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnAction(event->{
            shareVBox.setVisible(true);
            cancelShareVBox.setVisible(false);
        });
    }
    private void setSave(ShareEntity share){
        save.setOnAction(event->{
            if (shareService.toCancelShare(share) != null){
                shareVBox.setVisible(true);
                cancelShareVBox.setVisible(false);
            }
        });
    }
}
