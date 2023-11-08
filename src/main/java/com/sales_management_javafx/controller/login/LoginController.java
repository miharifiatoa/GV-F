package com.sales_management_javafx.controller.login;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.apache.commons.codec.digest.DigestUtils;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.UserService;
import org.sales_management.session.HibernateUtil;
import org.sales_management.session.SessionManager;
import org.sales_management.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private GridPane loginGridpane;
    @FXML private Button connectionButton;
    @FXML private TextField passwordTextfield;
    @FXML private TextField usernameTextfield;
    private final UserService userService;

    public LoginController() {
        this.userService = new UserService();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.connect();
    }

    public void connect() {
        connectionButton.setOnAction(event->{
            String username = usernameTextfield.getText();
            String password = passwordTextfield.getText();
            AccountEntity account = this.authenticate(username,password);
            BorderPane salesManagementBorderpane = (BorderPane) loginGridpane.getParent();
            if (account != null){
                UserEntity user = account.getUser();
                if (user != null){
                    this.setUserInSession(username,password);
                    if (Objects.equals(user.getRole(), "ADMIN")){
                        salesManagementBorderpane.setCenter(this.getDashboard());
                    }
                    else if (Objects.equals(user.getRole(), "SELLER")){
                        salesManagementBorderpane.setCenter(this.getSellerLayout());
                    }
                    else if (Objects.equals(user.getRole(), "STOCKIST")){
                        salesManagementBorderpane.setCenter(this.getStockistLayout());
                    }
                    else {
                        System.out.println("no ...");
                    }
                }
                else SessionManager.clearSession();
            }
            else {
                System.out.println("username or password incorrect");
                SessionManager.clearSession();
            }
        });
    }
    private BorderPane getDashboard(){
        FXMLLoader dashboardLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardLayout.fxml"));
        BorderPane dashboard;
        try {
            dashboard = dashboardLoader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboard;
    }
    private StackPane getSellerLayout(){
        FXMLLoader sellerLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/seller/sellerLayout.fxml"));
        StackPane sellerLayout;
        try {
            sellerLayout = sellerLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sellerLayout;
    }
    private BorderPane getStockistLayout(){
        FXMLLoader stockistLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/stockist/stockistLayout.fxml"));
        BorderPane stockistLayout;
        try {
            stockistLayout = stockistLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stockistLayout;
    }
    private AccountEntity authenticate(String username , String password){
        AccountEntity account = new AccountEntity();
        try {
            AccountEntity accountEntity = new UserService().getAccountByUsername(username);
            if (accountEntity != null){
                if (DigestUtils.sha256Hex(password).equals(accountEntity.getPassword())){
                    account = accountEntity;
                }
            }
        } catch (Exception e) {
            System.out.println("Username or password incorrect");
        }
        return account;
    }
    private void setUserInSession(String username , String password){
        AccountEntity account = authenticate(username , password);
        UserEntity user = account.getUser();
        UserSession userSession = new UserSession();
        userSession.setCurrentUser(user);
        SessionManager.setSession(userSession);
    }
}
