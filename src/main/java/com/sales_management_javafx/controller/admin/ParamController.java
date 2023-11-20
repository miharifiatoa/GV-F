package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import java.io.IOException;

import com.sales_management_javafx.composent.admin.AdminStoryGridPane;
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
    @FXML private Label codeLabel;
    @FXML private Label mouvement;
    @FXML private TextField Desc;
    @FXML private Button Confirmer;
    @FXML private ScrollPane story;
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
            codeLabel.setText(typeArticle.getArticle().getCode());
            nomProduit.setText(typeArticle.getArticle().getProductTypeEntity().getProduct().getName());
            reference.setText(typeArticle.getArticle().getProductTypeEntity().getReference());
            type.setText(typeArticle.getArticle().getProductTypeEntity().getName());
            category.setText(typeArticle.getArticle().getProductTypeEntity().getProduct().getProductCategory().getName());
            marque.setText(typeArticle.getArticle().getProductTypeEntity().getBrand());
            color.setText(typeArticle.getColor());
            size.setText(typeArticle.getSize());
            prix.setText(typeArticle.getArticle().getPrice().toString());
            if(typeArticle.getQuantity()>0){
                quantite.setText(String.valueOf(typeArticle.getQuantity()));
            }
            else {quantite.setText(String.valueOf(0));}
        }
        this.setMoins(typeArticle);
        this.setPlus(typeArticle);
        this.setStory();
        this.updateButtonState();
    }
    private void setStory(){
        GridPane gridPane = new AdminStoryGridPane().getGridPane(stockHistoryService.getAll(),1);
        story.setContent(gridPane);
    }
    
    private void setMoins(ArticleTypeEntity articleType){
        moins.setOnMouseClicked(event->{
            mouvement.setText("Retrait");
            int qte = articleType.getQuantity();
            articleType.setQuantity(qte-1);
        
            stockHistory(articleType);
        });
    }
    
    private void setPlus(ArticleTypeEntity articleType){
        plus.setOnMouseClicked(event->{
            mouvement.setText("Decouverte");
            int qte = articleType.getQuantity();
            articleType.setQuantity(qte+1);
            stockHistory(articleType);
        });
    }
    private void stockHistory(ArticleTypeEntity articleType){
        Confirmer.setOnMouseClicked(event->{
            if (!mouvement.getText().isEmpty()){
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
                        Confirmer.setDisable(true);
                    }
                    this.setStory();
                }
            }
        });
    }
    private void updateButtonState() {
        if (Desc.getText().isEmpty()){
            Confirmer.setDisable(true);
        }
        Desc.textProperty().addListener(event->{
            Confirmer.setDisable(Desc.getText().isEmpty());
        });
    }
}
