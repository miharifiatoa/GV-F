package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.sales_management.entity.AccountEntity;
import org.sales_management.service.AccountService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class AccountTableController implements Initializable {
    @FXML
    private BorderPane table_borderpane;
    @FXML
    private TableView<AccountEntity> account_tableview;
    @FXML
    private TableColumn<AccountEntity,Long> id_column;
    @FXML
    private TableColumn<AccountEntity,String> username_column;
    @FXML
    private TableColumn<AccountEntity,String> delete_column;
    @FXML
    private TableColumn<AccountEntity,String> info_column;

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
            BorderPane parent = (BorderPane) table_borderpane.getParent();
            parent.setBottom(account_information);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void showAccounts(){
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        username_column.setCellValueFactory(new PropertyValueFactory<>("username"));
        username_column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(item);
                    label.setOnMouseClicked(event -> {
                        if (!isEmpty()){
                            TableRow<AccountEntity> tableRow = getTableRow();
                            AccountEntity account = tableRow.getItem();
                            try(FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(SalesApplication.class.getResource("/file/data.json")))) {
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                                objectOutputStream.writeObject(account);
                                getAccountInformation();
                                objectOutputStream.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    setGraphic(label);
                }
            }
        });
        Collection<AccountEntity> collection = this.accountService.getAll();
        account_tableview.getItems().addAll(collection);
        account_tableview.setFixedCellSize(37);
    }
}
