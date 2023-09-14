package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.ActionTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;
import org.sales_management.entity.AccountEntity;
import org.sales_management.service.AccountService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountTableController implements Initializable {
    @FXML
    private BorderPane account_table_borderpane;
    @FXML
    private TableView<AccountEntity> account_tableview;
    @FXML
    private TableColumn<AccountEntity,Long> id_column;
    @FXML
    private TableColumn<AccountEntity,String> username_column;
    @FXML
    private TableColumn<AccountEntity,Void> action_column;
    private final AccountService accountService;

    public AccountTableController() {
        this.accountService = new AccountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showAccounts();
    }
    public void getAccountInformation(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountInformation.fxml"));
            GridPane account_information = fxmlLoader.load();
            VBox.setVgrow(account_information, Priority.ALWAYS);
            BorderPane parent = (BorderPane) account_table_borderpane.getParent();
            parent.setBottom(account_information);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void showAccounts(){
        this.setAccountColumnValue();
        account_tableview.setFocusTraversable(false);
        account_tableview.setFixedCellSize(38.5);
        account_tableview.getItems().addAll(this.accountService.getAll());
    }
    public void setAccountColumnValue(){
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        username_column.setCellValueFactory(new PropertyValueFactory<>("username"));
        action_column.setCellFactory(param->new TableCell<>(){
            @Override
            protected void updateItem(Void unused, boolean empty) {
                super.updateItem(unused, empty);
                if (empty){
                    setText(null);
                    setGraphic(null);
                }
                else {
                    GridPane action_pane = new ActionTableCell(()-> {
                        onDeleteAccount(getTableRow());
                    },
                    ()-> {
                        TableRow<AccountEntity> tableRow = getTableRow();
                        onShowInformation(tableRow);
                    }).createActionPane();
                    setGraphic(action_pane);
                }
            }
        });
        action_column.setCellValueFactory(param->new SimpleObjectProperty<>());
    }
    public void onDeleteAccount(TableRow<AccountEntity> tableRow){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression!");
        alert.setHeaderText("Voulez vous supprimer");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                AccountEntity account = tableRow.getItem();
                if (this.accountService.deleteById(account.getId()) != null){
                    account_tableview.setItems(FXCollections.observableArrayList(new AccountService().getAll()));
                }
            }
        });
    }
    public void onShowInformation(@NotNull TableRow<AccountEntity> tableRow){
        AccountEntity account = tableRow.getItem();
        try(FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(SalesApplication.class.getResource("/file/data.json")))) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(account);
            objectOutputStream.close();
            getAccountInformation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
