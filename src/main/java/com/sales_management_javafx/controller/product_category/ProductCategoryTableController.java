package com.sales_management_javafx.controller.product_category;

import com.sales_management_javafx.SalesApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;
import org.sales_management.entity.ProductCategoryEntity;
import org.sales_management.service.ProductCategoryService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductCategoryTableController implements Initializable {
    @FXML
    private TableView<ProductCategoryEntity> articleTableView;
    @FXML
    private TableColumn<ProductCategoryEntity,Long> articleIdTableColumn;
    @FXML
    private TableColumn<ProductCategoryEntity,String> articleLabelTableColumn;
    private final ProductCategoryService productCategoryService;

    public ProductCategoryTableController() {
        this.productCategoryService = new ProductCategoryService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showArticles();
    }
    public void showArticles(){
        this.setArticleColumnValue();
        articleTableView.setFocusTraversable(false);
        articleTableView.setFixedCellSize(40);
        articleTableView.getItems().addAll(this.productCategoryService.getAll());
    }
    public void setArticleColumnValue(){
        articleIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        articleLabelTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    public void onDeleteArticle(TableRow<ProductCategoryEntity> tableRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression!");
        alert.setHeaderText("Voulez vous supprimer");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                ProductCategoryEntity article = tableRow.getItem();
                if (this.productCategoryService.deleteById(article.getId()) != null){
                    articleTableView.setItems(FXCollections.observableArrayList(new ProductCategoryService().getAll()));
                }
            }
        });
    }
    public void onShowInformation(@NotNull TableRow<ProductCategoryEntity> tableRow){
        ProductCategoryEntity article = tableRow.getItem();
        try(FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(SalesApplication.class.getResource("/file/data.json")))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(article);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
