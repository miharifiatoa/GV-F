package com.sales_management_javafx.controller.share;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DateTimeFormatter;
import com.sales_management_javafx.classes.Printer;
import com.sales_management_javafx.composent.share.ShareInfoGridPane;
import com.sales_management_javafx.composent.share.ShareGridPane;
import com.sales_management_javafx.composent.StockistArticleTypeGridPane;
import com.sales_management_javafx.composent.admin.AdminShareInfoGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.ShareService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShareBoxController implements Initializable {
    @FXML private Label text;
    @FXML private Label cancelText;
    @FXML private Label dateLabel;
    @FXML private Button cancel;
    @FXML private Button exit;
    @FXML private Button exitInfo;
    @FXML private Button save;
    @FXML private Button info;
    @FXML private Button print;
    @FXML private StackPane shareBox;
    @FXML private VBox shareVBox;
    @FXML private VBox cancelShareVBox;
    @FXML private VBox infoVBox;
    @FXML private HBox shareHBox;
    @FXML private ImageView printIcon;
    @FXML private ImageView exitIcon;
    @FXML private ScrollPane shareScrollpane;
    private final ShareService shareService;

    public ShareBoxController() {
        this.shareService = new ShareService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shareVBox.setVisible(true);
        cancelShareVBox.setVisible(false);
        infoVBox.setVisible(false);
        this.setCancel();
        this.setExit();
        this.setInfo();
        this.setExitInfo();
        this.putIcons();
        this.setPrint();
    }
    private void initialize(ShareEntity share){
        dateLabel.setText(DateTimeFormatter.format(share.getShareDate()));
        cancelText.setText("Voulez vous vraiment annuler cet partage ?");
        this.setSave(share);
        this.setShareScrollpane(share);
    }
    public void initializeForStockist(ShareEntity share){
        this.initialize(share);
        this.text.setText("Vous avez partagé " + sum(share) + " produit(s) vers le boutique : " + share.getShop().getName() + " à " + share.getShop().getAddress() + " le : ");
    }
    public void initializeForAdmin(ShareEntity share){
        this.shareHBox.getChildren().remove(cancel);
        this.initialize(share);
        this.text.setText(share.getUser().getAccount().getUsername() + " a partagé " + sum(share) + " produit(s) vers le boutique : " + share.getShop().getName() + " à " + share.getShop().getAddress() + " le : ");
        this.text.setOnMouseClicked(event->{
            GridPane adminShareInfoGridPane = new AdminShareInfoGridPane().getGridPane(share,1);
            ScrollPane dashboardLayoutScrollpane = (ScrollPane) shareBox.getParent().getParent().getParent().getParent();
            dashboardLayoutScrollpane.setContent(adminShareInfoGridPane);
        });
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
    private void setInfo(){
        info.setOnAction(event->{
            shareVBox.setVisible(false);
            infoVBox.setVisible(true);
        });
    }
    private void setExitInfo(){
        exitInfo.setOnAction(event->{
            shareVBox.setVisible(true);
            infoVBox.setVisible(false);
        });
    }
    private void setSave(ShareEntity share){
        save.setOnAction(event->{
            if (shareService.toCancelShare(share) != null){
                shareVBox.setVisible(true);
                cancelShareVBox.setVisible(false);
                GridPane stockistArticleGridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getAll(),4);
                GridPane shareGridPane = new ShareGridPane().getGridPane(shareService.getSharesByDate(LocalDate.now()),4);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) getShareLayoutScrollpane().getParent().getParent().getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                getShareLayoutScrollpane().setContent(shareGridPane);
            }
        });
    }
    private void setPrint(){
        print.setOnAction(event->{
            Printer.print(shareScrollpane);
        });
    }
    private void setShareScrollpane(ShareEntity share){
        GridPane articleCodeGridPane = new ShareInfoGridPane().getGridPane(share,1);
        shareScrollpane.setContent(articleCodeGridPane);
    }
    private ScrollPane getShareLayoutScrollpane(){
        return (ScrollPane) shareBox.getParent().getParent().getParent().getParent();
    }
    private void putIcons(){
        this.printIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PrintIcon.png"))));
        this.exitIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ExitIcon.png"))));
    }
}
