package com.sales_management_javafx.controller.arrival;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.ArrivalGridPane;
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
import org.sales_management.entity.UserEntity;
import org.sales_management.service.ArrivalService;
import org.sales_management.service.ArticleService;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;

public class ArrivalLayoutController implements Initializable {
    @FXML private Button exit;
    @FXML private Button save;
    @FXML private Button arrival;
    @FXML private Button listArrival;
    @FXML private StackPane arrivalLayout;
    @FXML private ScrollPane arrivalLayoutScrollpane;
    @FXML private ImageView arrivalIcon;
    @FXML private ImageView listArrivalIcon;
    @FXML private TextField arrivalDescriptionTextfield;
    private final ArticleService articleService;
    private final ArrivalService arrivalService;
    private final UserEntity user;

    public ArrivalLayoutController() {
        this.user = SessionManager.getSession().getCurrentUser();
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
        this.setArrival();
        this.setListArrival();
    }
    private void setExit(){
        arrivalLayout.getParent().setVisible(false);
        BorderPane stockistLayout = (BorderPane) arrivalLayout.getParent().getParent().getParent();
        stockistLayout.setBottom(getToolbar());
    }
    private void setArrival(){
        arrival.setOnAction(event->{
            GridPane gridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
            arrivalLayoutScrollpane.setContent(gridPane);
        });
    }
    private void setListArrival(){
        listArrival.setOnAction(event->{
            GridPane arrivalGridPane = new ArrivalGridPane().getGridPane(new ArrivalService().getAll(),4);
            arrivalLayoutScrollpane.setContent(arrivalGridPane);
        });
    }
    private void setSave(){
        save.setOnAction(event->{
            Collection<ArticleEntity> articles = FileIO.readArticleFromFile("arrivals.dat");
            ArrivalEntity arrival = new ArrivalEntity();
            arrival.setArrivalDate(LocalDateTime.now());
            arrival.setDescription(arrivalDescriptionTextfield.getText());
            if (user != null){
                arrival.setUser(user);
            }
            if (arrivalService.toSaveArrival(arrival,articles)!=null){
                articles.clear();
                FileIO.writeTo("arrivals.dat",articles);
                GridPane stockistArticleGridPane = new StockistArticleGridPane().getGridPane(new ArticleService().getAll(),4);
                GridPane articleInfoGridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
                getStockistBoxLayoutScrollpane().setContent(stockistArticleGridPane);
                arrivalLayoutScrollpane.setContent(articleInfoGridPane);
                setExit();
            }
        });
    }
    private void setArrivalLayoutScrollpane(){
        GridPane gridPane = new ArticleInfoGridPane().getGridPane(FileIO.readArticleFromFile("arrivals.dat"),2);
        arrivalLayoutScrollpane.setContent(gridPane);
    }
    private BorderPane getStockistLayout(){
        return (BorderPane) arrivalLayout.getParent().getParent().getParent();
    }
    private ScrollPane getStockistBoxLayoutScrollpane(){
        return (ScrollPane) getStockistLayout().lookup("#stockistBoxLayoutScrollpane");
    }
    public void putIcons(){
        arrivalIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ArrivalIcon.png"))));
        listArrivalIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ListIcon.png"))));
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
