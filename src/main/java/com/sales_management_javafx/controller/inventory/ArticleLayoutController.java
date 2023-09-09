package com.sales_management_javafx.controller.inventory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleLayoutController implements Initializable {
    @FXML
    private BorderPane articleLayout;
    private final ArticleService articleService;

    public ArticleLayoutController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showArticles();
    }
    public void showArticles(){
        GridPane gridPane = new GridPane();
        ColumnConstraints constraint1 = new ColumnConstraints();
        ColumnConstraints constraint2 = new ColumnConstraints();
        ColumnConstraints constraint3 = new ColumnConstraints();
        ColumnConstraints constraint4 = new ColumnConstraints();
        constraint1.setPercentWidth(25);
        constraint2.setPercentWidth(25);
        constraint3.setPercentWidth(25);
        constraint4.setPercentWidth(25);
        constraint1.setHgrow(Priority.ALWAYS);
        constraint2.setHgrow(Priority.ALWAYS);
        constraint3.setHgrow(Priority.ALWAYS);
        constraint4.setHgrow(Priority.ALWAYS);
        gridPane.setHgap(10);
        gridPane.getColumnConstraints().addAll(constraint1,constraint2,constraint3,constraint4);
        int row = 0;
        int col = 0;
        for (ArticleEntity article : articleService.getAll()){
            VBox vBox = new VBox();
            GridPane labelGridpane = new GridPane();
            ColumnConstraints constraint5 = new ColumnConstraints();
            ColumnConstraints constraint6 = new ColumnConstraints();
            constraint5.setPercentWidth(50);
            labelGridpane.getColumnConstraints().addAll(constraint5,constraint6);
            Label label = new Label(article.getLabel());
            Label code = new Label(article.getCode());
            Label family = new Label(article.getFamily());
            labelGridpane.add(new Label("Libellé"),0,0);
            labelGridpane.add(label,1,0);
            vBox.getStyleClass().add("article-box");
            vBox.getChildren().addAll(labelGridpane,code,family);
            gridPane.add(vBox,col,row);
            col++;
            if (col==4){
                col=0;
                row++;
            }
        }
        this.articleLayout.setCenter(gridPane);
    }
}
