package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.AccountGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.sales_management.service.AccountService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountLayoutController implements Initializable {
    @FXML private BorderPane accountLayout;
    @FXML private ScrollPane accountLayoutScrollpane;
    private final AccountService accountService;

    public AccountLayoutController() {

        this.accountService = new AccountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.accountLayout.setBottom(this.getToolbar());
        this.setAccounts();
    }
    private void setAccounts(){
        GridPane gridPane = new AccountGridPane().getGridPane(accountService.getAll(),4);
        accountLayoutScrollpane.setContent(gridPane);
    }
    public GridPane getToolbar(){
        FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountToolbar.fxml"));
        GridPane toolbar;
        try {
            toolbar = toolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toolbar;
    }
}
