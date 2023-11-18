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
import org.sales_management.session.SessionManager;
import org.sales_management.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class LoginController implements Initializable {
    @FXML private GridPane loginGridpane;
    @FXML private Button connectionButton;
    @FXML private Label errorLabel;
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
        BorderPane salesManagementBorderpane = (BorderPane) loginGridpane.getParent();
        AccountEntity account = new AccountEntity();
        if (username.isEmpty()){
            errorLabel.setText("Saisir une Nom d'utilisateur !");

            }
            else {
            AccountEntity inSession = this.userService.getAccountByUsername(username);
            if(inSession.getUsername()!=null){
                if(DigestUtils.sha256Hex(password).equals(inSession.getPassword())) {
                    String Role = inSession.getUser().getRole();
                    account = inSession;
                    setUserInSession(inSession.getUser());
                    switch (Role) {
                        case "ADMIN": 
                        salesManagementBorderpane.setCenter(this.getDashboard());    
                            break;

                        case "SELLER": 
                        salesManagementBorderpane.setCenter(this.getSellerLayout());
                            break;
                        case "STOCKIST": 
                        salesManagementBorderpane.setCenter(this.getStockistLayout());
                            break;    
                        default:
                            throw new AssertionError();
                        }
                    } else {
                    errorLabel.setText("Mot de passe incorrecte !");
                    }
                } else {
                errorLabel.setText("Utilisateur non trouver !");
                }
            }
        return account;
    }
    private void setUserInSession(UserEntity user){
        UserSession userSession = new UserSession();
        userSession.setCurrentUser(user);
        SessionManager.setSession(userSession);
    }
}