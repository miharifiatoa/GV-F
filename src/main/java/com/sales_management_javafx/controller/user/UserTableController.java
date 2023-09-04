package com.sales_management_javafx.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sales_management.entity.AccountEntity;
import org.sales_management.service.AccountService;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class UserTableController implements Initializable {
    @FXML
    TableView<AccountEntity> account_tableView;
    @FXML
    TableColumn<CheckBox,Boolean> check_column;
    @FXML
    private TableColumn<AccountEntity,Long> id_column;
    @FXML
    private TableColumn<AccountEntity,String> username_column;
    private final AccountService accountService;

    public UserTableController() {
        this.accountService = new AccountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showAccounts();
    }
    public void showAccounts(){
        check_column.setCellFactory(CheckBoxTableCell.forTableColumn(check_column));
        check_column.setCellValueFactory(new PropertyValueFactory<>("selected"));
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        username_column.setCellValueFactory(new PropertyValueFactory<>("username"));
        Collection<AccountEntity> collection = this.accountService.getAll();
        account_tableView.getItems().addAll(collection);
        account_tableView.setFixedCellSize(37);
    }
}
