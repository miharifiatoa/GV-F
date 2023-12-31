package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.AccountGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.service.AccountService;

public class AccountLayoutController implements Initializable {
    @FXML private BorderPane accountLayout;
    @FXML private Button exit;
    @FXML private Label newUser;
    @FXML private Label users;
    @FXML private ImageView AccountIcon;
    @FXML private ScrollPane accountLayoutScrollpane;
    private final AccountService accountService;
    public AccountLayoutController() {
        this.accountService = new AccountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.putIcons();
        this.exit.setOnAction(event->this.setExit());
        setAccounts();
        this.setUsers();
        setNewUser();
    }
    
    private void setAccounts(){
        GridPane gridPane = new AccountGridPane().getGridPane(accountService.getAll(),3);
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
    
    public void setNewUser(){
        newUser.setOnMouseClicked(event->{
            accountLayoutScrollpane.setContent(getUserForm());
        });
    }
    private void setUsers(){
        users.setOnMouseClicked(event->{
            this.setAccounts();
        });
    }
    
    public VBox getUserForm(){
        FXMLLoader userFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/userForm.fxml"));
        VBox userForm;
        try {
            userForm = userFormLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userForm;
    }
    
    private void setExit(){
        BorderPane borderPane = (BorderPane) this.accountLayout.getParent();
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPane.setBottom(dashboardToolbar);
    
    }
    
    private void putIcons(){
        AccountIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/NewAccountIcon.png"))));
    }
}
