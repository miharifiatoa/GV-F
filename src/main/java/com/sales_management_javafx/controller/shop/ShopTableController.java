package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.ActionTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.sales_management.entity.ShopEntity;
import org.sales_management.service.ShopService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ShopTableController implements Initializable {
    @FXML
    private BorderPane shopTableBorderpane;
    @FXML
    private TableView<ShopEntity> shopTableView;
    @FXML
    private TableColumn<ShopEntity,Long> shopIdTableColumn;
    @FXML
    private TableColumn<ShopEntity,String> shopNameTableColumn;
    @FXML
    private TableColumn<ShopEntity,Void> shopActionTableColumn;
    private final ShopService shopService;

    public ShopTableController() {
        this.shopService = new ShopService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showShops();
    }
    public void getShopInformation(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopInformation.fxml"));
            GridPane ShopInformationGridpane = fxmlLoader.load();
            VBox.setVgrow(ShopInformationGridpane, Priority.ALWAYS);
            BorderPane parent = (BorderPane) shopTableBorderpane.getParent();
            parent.setBottom(ShopInformationGridpane);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void showShops(){
        this.setShopColumnValue();
        shopTableView.setFocusTraversable(false);
        shopTableView.setFixedCellSize(38.5);
        shopTableView.getItems().addAll(this.shopService.getAll());
    }
    public void setShopColumnValue(){
        shopIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        shopNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        shopActionTableColumn.setCellFactory(param->new TableCell<>(){
            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty){
                    setText(null);
                    setGraphic(null);
                }
                else {
                    GridPane action_pane = new ActionTableCell(()-> {
                        onDeleteShop(getTableRow());
                    },
                            ()-> {
                                TableRow<ShopEntity> shopTableRow = getTableRow();
                                onShowInformation(shopTableRow);
                            }).createActionPane();
                    setGraphic(action_pane);
                }
            }
        });
        this.shopActionTableColumn.setCellValueFactory(param->new SimpleObjectProperty<>());
    }
    public void onDeleteShop(TableRow<ShopEntity> tableRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression!");
        alert.setHeaderText("Voulez vous supprimer");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                ShopEntity shop = tableRow.getItem();
                if (this.shopService.deleteById(shop.getId()) != null){
                    shopTableView.setItems(FXCollections.observableArrayList(new ShopService().getAll()));
                }
            }
        });
    }
    public void onShowInformation(@NotNull TableRow<ShopEntity> tableRow){
        ShopEntity shop = tableRow.getItem();
        try(FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(SalesApplication.class.getResource("/file/data.json")))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(shop);
            objectOutputStream.close();
            this.getShopInformation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
