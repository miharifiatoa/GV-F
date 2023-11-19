package com.sales_management_javafx.controller.arrival;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.DateTimeFormatter;
import com.sales_management_javafx.classes.Printer;
import com.sales_management_javafx.composent.arrival.ArrivalGridPane;
import com.sales_management_javafx.composent.arrival.ArrivalInfoGridPane;
import com.sales_management_javafx.composent.stockist.StockistArticleTypeGridPane;
import com.sales_management_javafx.composent.admin.AdminArrivalInfoGridPane;
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
import org.sales_management.entity.ArrivalArticleEntity;
import org.sales_management.entity.ArrivalEntity;
import org.sales_management.service.ArrivalService;
import org.sales_management.service.ArticleTypeService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ArrivalBoxController implements Initializable {
    @FXML private Label text;
    @FXML private Label arrivalDateLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label cancelText;
    @FXML private Button cancel;
    @FXML private Button exit;
    @FXML private Button exitInfo;
    @FXML private Button save;
    @FXML private Button info;
    @FXML private Button print;
    @FXML private ImageView printIcon;
    @FXML private ImageView exitIcon;
    @FXML private VBox cancelArrivalVBox;
    @FXML private VBox arrivalVBox;
    @FXML private VBox infoVBox;
    @FXML private HBox arrivalHBox;
    @FXML private StackPane arrivalBox;
    @FXML private ScrollPane arrivalScrollpane;
    private final ArrivalService arrivalService;

    public ArrivalBoxController() {
        this.arrivalService = new ArrivalService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arrivalVBox.setVisible(true);
        cancelArrivalVBox.setVisible(false);
        infoVBox.setVisible(false);
        this.setCancel();
        this.setExit();
        this.setInfo();
        this.setExitInfo();
        this.putIcons();
        this.setPrint();
    }
    private void initialize(ArrivalEntity arrival){
        cancelText.setText("Voulez vous vraiment annuler cet arrivage ?");
        arrivalDateLabel.setText(DateTimeFormatter.format(arrival.getArrivalDate()));
        descriptionLabel.setText(arrival.getDescription());
        this.setSave(arrival);
        this.setArrivalScrollpane(arrival);
    }
    public void initializeForStockist(ArrivalEntity arrival){
        this.initialize(arrival);
        text.setText("Vous avez ajouté " + sum(arrival) + " produits au stock le : ");
    }
    public void initializeForAdmin(ArrivalEntity arrival){
        this.arrivalHBox.getChildren().remove(cancel);
        this.initialize(arrival);
        this.text.setText(arrival.getUser().getAccount().getUsername() + " a ajouté " + sum(arrival) + " produits au stock le : ");
        this.text.setOnMouseClicked(event->{
            GridPane arrivalInfoGridPane = new AdminArrivalInfoGridPane().getGridPane(arrival,1);
            ScrollPane dashboardLayoutScrollpane = (ScrollPane) arrivalBox.getParent().getParent().getParent().getParent();
            dashboardLayoutScrollpane.setContent(arrivalInfoGridPane);
        });
    }
    private int sum(ArrivalEntity arrival){
        int sum = 0;
        for (ArrivalArticleEntity arrivalArticle : arrival.getArrivalArticles()){
            sum += arrivalArticle.getQuantity();
        }
        return sum;
    }
    private void setCancel(){
        cancel.setOnAction(event->{
            arrivalVBox.setVisible(false);
            cancelArrivalVBox.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnAction(event->{
            arrivalVBox.setVisible(true);
            cancelArrivalVBox.setVisible(false);
        });
    }
    private void setSave(ArrivalEntity arrival){
        save.setOnAction(event->{
            if (arrivalService.toCancelArrival(arrival) != null){
                arrivalVBox.setVisible(false);
                cancelArrivalVBox.setVisible(true);
                ScrollPane stockistBoxLayoutScrollpane = (ScrollPane) getArrivalLayoutScrollpane().getParent().getParent().getParent().getParent().lookup("#stockistBoxLayoutScrollpane");
                GridPane stockistArticleGridPane = new StockistArticleTypeGridPane().getGridPane(new ArticleTypeService().getAll(),4);
                GridPane arrivalGridPane = new ArrivalGridPane().getGridPane(arrivalService.getArrivalsByDate(LocalDate.now()) , 4);
                stockistBoxLayoutScrollpane.setContent(stockistArticleGridPane);
                getArrivalLayoutScrollpane().setContent(arrivalGridPane);
            }
        });
    }
    private void setInfo(){
        info.setOnAction(event->{
            infoVBox.setVisible(true);
            arrivalVBox.setVisible(false);
        });
    }
    private void setExitInfo(){
        exitInfo.setOnAction(event->{
            infoVBox.setVisible(false);
            arrivalVBox.setVisible(true);
        });
    }
    private void setPrint(){
        print.setOnAction(event->{
            Printer.print(arrivalScrollpane);
        });
    }
    private void setArrivalScrollpane(ArrivalEntity arrival){
        GridPane arrivalInfoGridPane = new ArrivalInfoGridPane().getGridPane(arrival,1);
        arrivalScrollpane.setContent(arrivalInfoGridPane);
    }
    private ScrollPane getArrivalLayoutScrollpane(){
        return (ScrollPane) arrivalBox.getParent().getParent().getParent().getParent();
    }
    private void putIcons(){
        this.printIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PrintIcon.png"))));
        this.exitIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ExitIcon.png"))));
    }
}
