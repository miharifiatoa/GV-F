package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.SalesApplication;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ArticleFormController implements Initializable {
    @FXML
    private VBox articleFormBox;
    @FXML
    private TextField articleLabelTextfield;
    @FXML
    private TextField articleCodeTextfield;
    @FXML
    private TextField articleFamilyTextfield;
    @FXML
    private Button confirmCreateButton;
    @FXML
    private Button cancelCreateButton;
    @FXML
    private Label notUniqueWarning;
    private final ArticleService articleService;

    public ArticleFormController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onCancelCreateArticle();
        this.formValidation();
        this.onCreateArticle();
    }
    private void onCancelCreateArticle(){
        cancelCreateButton.setOnAction(event->{
            BorderPane parent = (BorderPane) articleFormBox.getParent();
            parent.setBottom(this.getToolbar());
        });
    }
    private GridPane getToolbar(){
        GridPane toolbarGridpane;
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article/articleToolbar.fxml"));
        try {
            toolbarGridpane = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbarGridpane;
    }
    public void formValidation(){
        if (this.articleLabelTextfield.getText().isEmpty() || this.articleCodeTextfield.getText().isEmpty()){
            confirmCreateButton.setDisable(true);
        }
        this.articleLabelTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateButton.setDisable(this.articleLabelTextfield.getText().isEmpty() || this.articleCodeTextfield.getText().isEmpty())));
        this.articleCodeTextfield.textProperty().addListener(((observableValue, s, t1) -> confirmCreateButton.setDisable(this.articleLabelTextfield.getText().isEmpty() || this.articleCodeTextfield.getText().isEmpty())));
    }
    private void onCreateArticle(){
        this.confirmCreateButton.setOnAction(event->{
            if (this.articleService.isUniqueValue(this.articleCodeTextfield.getText())){
                if (this.createArticle()!=null){
                    BorderPane parent = (BorderPane) articleFormBox.getParent();
                    BorderPane account_layout = (BorderPane) this.articleFormBox.getParent();
                    BorderPane account_table_borderpane = (BorderPane) account_layout.getCenter();
                    @SuppressWarnings("unchecked")
                    TableView<ArticleEntity> articleTable = (TableView<ArticleEntity>) account_table_borderpane.getCenter();
                    articleTable.setItems(FXCollections.observableArrayList(this.articleService.getAll()));
                    parent.setBottom(this.getToolbar());
                }
            }
            else {
                notUniqueWarning.setText("Code d' article deja existe");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), action -> notUniqueWarning.setText("")));
                timeline.play();
            }
        });
    }
    private ArticleEntity createArticle(){
        ArticleEntity article = new ArticleEntity();
        if (!this.articleLabelTextfield.getText().isEmpty() || !this.articleCodeTextfield.getText().isEmpty()){
            article.setLabel(this.articleLabelTextfield.getText());
            article.setCode(this.articleCodeTextfield.getText());
            article.setFamily(this.articleFamilyTextfield.getText());
            return this.articleService.create(article);
        }
        else {
            return null;
        }
    }
}
