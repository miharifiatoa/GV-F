package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.sales_management.entity.ArticleTypeEntity;
import org.sales_management.entity.StockHistoryEntity;
import org.sales_management.service.ArticleTypeService;
import org.sales_management.service.StockHistoryService;

public class ParamController implements Initializable {
    
    @FXML private AnchorPane bottomPane;
    @FXML private Label nomProduit;
    @FXML private Label reference;
    @FXML private Label type;
    @FXML private Label category;
    @FXML private Label marque;
    @FXML private Label color;
    @FXML private Label size;
    @FXML private Label prix;
    @FXML private Label quantite;
    @FXML private Label plus;
    @FXML private Label moins;
    @FXML private Label journal;
    @FXML private Label mouvement;
    @FXML private Label retour;
    @FXML private TextField Desc;
    @FXML private Button Annuller;
    @FXML private Button Confirmer;
    @FXML private BorderPane liste;
    @FXML private BorderPane ArticleDetail;
    @FXML private ScrollPane contenu;
    private final GridPane gridPane;
    private ArticleTypeEntity typeArticle;
    private final ArticleTypeService articleTypeService;
    private final StockHistoryService stockHistoryService;
    public ParamController(ArticleTypeEntity typeArticle) {
        this.gridPane = new GridPane();
        this.typeArticle = typeArticle;
        this.articleTypeService = new ArticleTypeService();
        this.stockHistoryService = new StockHistoryService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(typeArticle!=null){
        nomProduit.setText(typeArticle.getArticle().getProductTypeEntity().getProduct().getName());
        reference.setText(typeArticle.getArticle().getProductTypeEntity().getReference());
        type.setText(typeArticle.getArticle().getProductTypeEntity().getName());
        category.setText(typeArticle.getArticle().getProductTypeEntity().getProduct().getProductCategory().getName());
        marque.setText(typeArticle.getArticle().getProductTypeEntity().getBrand());
        color.setText(typeArticle.getColor());
        size.setText(typeArticle.getSize());
        prix.setText(typeArticle.getArticle().getPrice().toString());
        if(typeArticle.getQuantity()>0) quantite.setText(String.valueOf(typeArticle.getQuantity()));
        else {quantite.setText(String.valueOf(0));}
        }
        Desc.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState());
        this.setMoins(typeArticle);
        this.setPlus(typeArticle);
        this.journalPane();
        this.getGridPane();
        this.getParam();
        this.cancel();
        }
    
    public void getParam(){
        retour.setOnMouseClicked(ev3->{
            ArticleDetail.setVisible(true);
            liste.setVisible(false);
        });
    }

    public void getGridPane(){
        
        Collection<StockHistoryEntity> newCollection = this.stockHistoryService.getAll();
                
        for (int i = 0 ; i < 2 ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /2);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        if(newCollection!=null){
        for (StockHistoryEntity history : newCollection) {
            gridPane.add(this.getAdminArticleHistoryListe(history), col, row);
                col++;
                if (col == 2) {
                    col = 0;
                    row++;
                }
            }
        
        gridPane.getStyleClass().add("gridpane");
        contenu.setContent(gridPane);
        }
    }
    
    private StackPane getAdminArticleHistoryListe(StockHistoryEntity history){
        FXMLLoader historyBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/historyBox.fxml"));
        StackPane historyBox;
        try {
            historyBox = historyBoxLoader.load();
            historyBoxController historyBoxController = historyBoxLoader.getController();
            historyBoxController.initialize(history);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return historyBox;
    }
    
    private void journalPane(){
        journal.setOnMouseClicked(ev2->{
            ArticleDetail.setVisible(false);
            liste.setVisible(true);
        });
    }
    
    private void setMoins(ArticleTypeEntity articleType){
        moins.setOnMouseClicked(event->{
            bottomPane.setDisable(false);
            mouvement.setText("Retrait");
            int qte = articleType.getQuantity();
            articleType.setQuantity(qte-1);
        
            stockHistory(articleType);
        });
    }
    
    private void setPlus(ArticleTypeEntity articleType){
        plus.setOnMouseClicked(event->{
            bottomPane.setDisable(false);
            mouvement.setText("Decouverte");
            int qte = articleType.getQuantity();
            articleType.setQuantity(qte+1);
        
            stockHistory(articleType);
        });
    }
    private void stockHistory(ArticleTypeEntity articleType){
        Confirmer.setOnMouseClicked(event->{
            String description = Desc.getText();
            StockHistoryEntity history = new StockHistoryEntity();
            history.setAction(mouvement.getText());
            history.setArticleType(articleType);
            history.setDate(LocalDateTime.now());
            history.setDescription(description);
            history.setQuantity(1);
        
        StockHistoryEntity History = this.stockHistoryService.create(history);
                if(History.getId()>0){
            ArticleTypeEntity newType = this.articleTypeService.update(History.getArticleType());
           if(newType!=null) {
               Desc.setText("");
                mouvement.setText("");
                Confirmer.setVisible(false);
                Annuller.setVisible(false);
                bottomPane.setDisable(true);
                }
            }
    
        });
    }
    private void updateButtonState() {
    boolean isDescriptionFieldEmpty = !Desc.getText().isEmpty();
    Annuller.setVisible(isDescriptionFieldEmpty);
    Confirmer.setVisible(isDescriptionFieldEmpty);
    }
    
    private void cancel(){
            Annuller.setOnAction(ev1->{
            mouvement.setText("");
            Desc.setText("");
            Confirmer.setVisible(false);
            Annuller.setVisible(false);
            bottomPane.setDisable(true);
        });
    }
    
}
