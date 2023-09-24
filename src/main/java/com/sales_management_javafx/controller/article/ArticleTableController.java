package com.sales_management_javafx.controller.article;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.ActionTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.sales_management.entity.ArticleEntity;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ArticleService;
import org.sales_management.service.ShopService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ArticleTableController implements Initializable {
    @FXML
    private TableView<ArticleEntity> articleTableView;
    @FXML
    private TableColumn<ArticleEntity,Long> articleIdTableColumn;
    @FXML
    private TableColumn<ArticleEntity,String> articleLabelTableColumn;
    @FXML
    private TableColumn<ArticleEntity,String> articleCodeTableColumn;
    private final ArticleService articleService;

    public ArticleTableController() {
        this.articleService = new ArticleService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showArticles();
    }
    public void showArticles(){
        this.setArticleColumnValue();
        articleTableView.setFocusTraversable(false);
        articleTableView.setFixedCellSize(40);
        articleTableView.getItems().addAll(this.articleService.getAll());
    }
    public void setArticleColumnValue(){
        articleIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        articleLabelTableColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        articleCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
    }
    public void onDeleteArticle(TableRow<ArticleEntity> tableRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression!");
        alert.setHeaderText("Voulez vous supprimer");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                ArticleEntity article = tableRow.getItem();
                if (this.articleService.deleteById(article.getId()) != null){
                    articleTableView.setItems(FXCollections.observableArrayList(new ArticleService().getAll()));
                }
            }
        });
    }
    public void onShowInformation(@NotNull TableRow<ArticleEntity> tableRow){
        ArticleEntity article = tableRow.getItem();
        try(FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(SalesApplication.class.getResource("/file/data.json")))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(article);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
