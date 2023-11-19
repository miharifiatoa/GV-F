package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jetbrains.annotations.NotNull;
import org.sales_management.entity.ProductEntity;
import org.sales_management.service.ProductService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductTableController implements Initializable {
    @FXML
    private TableView<ProductEntity> articleTableView;
    @FXML
    private TableColumn<ProductEntity,Long> articleIdTableColumn;
    @FXML
    private TableColumn<ProductEntity,String> articleLabelTableColumn;
    private final ProductService productService;

    public ProductTableController() {
        this.productService = new ProductService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showArticles();
    }
    public void showArticles(){
        this.setArticleColumnValue();
        articleTableView.setFocusTraversable(false);
        articleTableView.setFixedCellSize(40);
        articleTableView.getItems().addAll(this.productService.getAll());
    }
    public void setArticleColumnValue(){
        articleIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        articleLabelTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    public void onDeleteArticle(TableRow<ProductEntity> tableRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression!");
        alert.setHeaderText("Voulez vous supprimer");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                ProductEntity article = tableRow.getItem();
                if (this.productService.deleteById(article.getId()) != null){
                    articleTableView.setItems(FXCollections.observableArrayList(new ProductService().getAll()));
                }
            }
        });
    }
    public void onShowInformation(@NotNull TableRow<ProductEntity> tableRow){
        ProductEntity article = tableRow.getItem();
        try(FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(SalesApplication.class.getResource("/file/data.dat")))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(article);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
