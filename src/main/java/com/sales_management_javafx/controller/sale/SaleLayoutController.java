package com.sales_management_javafx.controller.sale;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.SaleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.service.SaleService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class SaleLayoutController implements Initializable {
    @FXML private Button exit;
    @FXML private BorderPane saleLayout;
    @FXML private ScrollPane saleLayoutScrollpane;
    @FXML private ImageView saleIcon;

    private final SaleService saleService;

    public SaleLayoutController() {
        this.saleService = new SaleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exit.setOnAction(event->setExit());
        this.setArticles();
        this.putIcons();
    }
    private void setArticles(){
        GridPane gridPane = new SaleGridPane().getGridPane(saleService.getSalesByDate(LocalDate.now()),5);
        saleLayoutScrollpane.setContent(gridPane);
    }
    private void setExit(){
        BorderPane sellerLayout = (BorderPane) saleLayout.getParent();
        sellerLayout.setBottom(this.getToolbar());
    }
    private void putIcons(){
        this.saleIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/SaleIcon.png"))));
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
}
