package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.sales_management.entity.AccountEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountLayoutController implements Initializable {
    @FXML
    private BorderPane account_layout;

    public AccountLayoutController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTable();
        this.setToolbar();
    }
    public void setToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountToolbar.fxml"));
        try {
            GridPane toolbar = toolbarLoader.load();
            account_layout.setBottom(toolbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTable(){
        FXMLLoader tableLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountTable.fxml"));
        try {
            BorderPane table_borderpane = tableLoader.load();
            account_layout.setCenter(table_borderpane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
