package com.sales_management_javafx.controller.arrival;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArticleInfoGridPane;
import com.sales_management_javafx.composent.StockistArticleGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ArrivalEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArrivalService;
import org.sales_management.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;

public class ArrivalLayoutController implements Initializable {
    @FXML private Button exit;
    @FXML private Button save;
    @FXML private StackPane arrivalLayout;
    @FXML private ScrollPane arrivalLayoutScrollpane;
    @FXML private ImageView arrivalIcon;
    @FXML private TextField arrivalDescriptionTextfield;
    private final ArticleService articleService;
    private final ArrivalService arrivalService;

    public ArrivalLayoutController() {
        this.arrivalService = new ArrivalService();
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setSave();
        this.exit.setOnAction(event->setExit());
        this.setArrivalLayoutScrollpane();
        this.putIcons();
        this.formValidation();
    }
    private void setExit(){
        arrivalLayout.getParent().setVisible(false);
        BorderPane stockistLayout = (BorderPane) arrivalLayout.getParent().getParent().getParent();
        stockistLayout.setBottom(getToolbar());
    }
    private void setSave(){
        save.setOnAction(event->{
            Collection<ArticleEntity> articles = FileIO.readArticleFromFile("arrivals.dat");
            for (ArticleEntity article : articles){
                ArrivalEntity arrival = new ArrivalEntity();
                ArticleEntity articlePersisted = articleService.getById(article.getId());
                article.setQuantity(article.getQuantity() + articlePersisted.getQuantity());
                arrival.setArrivalDate(LocalDateTime.now());
                arrival.setDescription(arrivalDescriptionTextfield.getText());
                arrival.setQuantity(article.getQuantity());
                arrivalService.create(arrival);
                article.setArrival(arrival);
                articleService.update(article);
            }
            articles.clear();
            FileIO.writeTo("arrivals.dat",articles);
            GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(),4);
            GridPane articleInfoGridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
            getStockistBoxLayoutScrollpane().setContent(stockistArticleGridPane);
            arrivalLayoutScrollpane.setContent(articleInfoGridPane);
            setExit();
        });
    }
    private void setArrivalLayoutScrollpane(){
        try {
            GridPane gridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
            arrivalLayoutScrollpane.setContent(gridPane);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private BorderPane getStockistLayout(){
        return (BorderPane) arrivalLayout.getParent().getParent().getParent();
    }
    private ScrollPane getStockistBoxLayoutScrollpane(){
        return (ScrollPane) getStockistLayout().lookup("#stockistBoxLayoutScrollpane");
    }
    public void putIcons(){
        arrivalIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ArrivalIcon.png"))));
    }
    private StackPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistToolbar.fxml"));
        StackPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
    private void formValidation(){
        if (arrivalDescriptionTextfield.getText().isEmpty()){
            save.setDisable(true);
        }
        if (FileIO.readArticleFromFile("arrivals.dat").isEmpty()){
            save.setDisable(true);
        }
        arrivalDescriptionTextfield.textProperty().addListener(event->{
            if (!arrivalDescriptionTextfield.getText().isEmpty()){
                save.setDisable(FileIO.readArticleFromFile("arrivals.dat").isEmpty());
            }
        });
    }
}
