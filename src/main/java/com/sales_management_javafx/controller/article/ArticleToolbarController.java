package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArticleToolbarController implements Initializable {
    @FXML
    private GridPane articleToolbarGridpane;
    @FXML
    private Button createArticleButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onShowForm();
    }
    public void onShowForm(){
        this.createArticleButton.setOnAction(event->{
            BorderPane parent = (BorderPane) this.articleToolbarGridpane.getParent();
            parent.setBottom(this.getForm());
        });
    }
    private VBox getForm(){
        VBox toolbarBox;
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleForm.fxml"));
        try {
            toolbarBox = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbarBox;
    }
}
