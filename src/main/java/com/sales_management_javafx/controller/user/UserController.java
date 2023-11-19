package com.sales_management_javafx.controller.user;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.entity.AccountEntity;
import org.sales_management.service.AccountService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private BorderPane user_borderpane;
    private final AccountService accountService;

    public UserController() {
        this.accountService = new AccountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/toolbar.fxml"));
        try {
            GridPane toolbar = toolbarLoader.load();
            user_borderpane.setBottom(toolbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader tableLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/table.fxml"));
        try {
            TableView<AccountEntity> tableView = tableLoader.load();
            user_borderpane.setCenter(tableView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
