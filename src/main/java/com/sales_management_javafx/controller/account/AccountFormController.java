package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.AccountGridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.AccountService;
import org.sales_management.service.PersonService;
import org.sales_management.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccountFormController implements Initializable {
    @FXML
    private VBox account_form;
    @FXML
    private Button confirm_button;
    @FXML
    private Button previous_button;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private RadioButton StokisteRole;
    @FXML
    private RadioButton VendeurRole;
    @FXML
    private Label error;
    @FXML
    private Label Message;
    private ToggleGroup foctionToggleGroup = new ToggleGroup();
    private final PersonService personService;
    private final UserService userService;
    private final AccountService accountService;

    public AccountFormController() {
        this.accountService = new AccountService();
        this.personService = new PersonService();
        this.userService = new UserService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        VendeurRole.setToggleGroup(foctionToggleGroup);
        StokisteRole.setToggleGroup(foctionToggleGroup);
        
        username.textProperty().addListener(((observable, oldValue, newValue) -> formValidation()));
        password.textProperty().addListener(((observable, oldValue, newValue) -> formValidation()));
        confirmPassword.textProperty().addListener(((observable, oldValue, newValue) -> formValidation()));
        foctionToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> formValidation());
        this.setMessage();
        this.formValidation();
        this.createAccount();
        this.previous();
    }

    public void formValidation(){
            confirm_button.setDisable(username.getText().isEmpty() 
                    || accountRoleProperty().getValue().contains(" ")
                    || password.getText().isEmpty() 
                    || confirmPassword.getText().isEmpty() 
                    || (confirmPassword.getText() == null ? password.getText() != null : 
                !confirmPassword.getText().equals(password.getText())));
        
            setMessage();
        
    }
    
    public void setMessage(){
    
        if(username.getText().isEmpty()){
            Message.setText("Veuiller saisir une nom d'utilisateur !");
        } else if(accountRoleProperty().getValue().contains(" ")){
            Message.setText("Selectionner le fonction du nouveau utilisateur !");
        } else if(password.getText().isEmpty()){
            Message.setText("Vous devez fournir une nouveau mot de passe pour le nouveau utilisateur !");
        } else if(confirmPassword.getText().isEmpty()){
            Message.setText("Confirmer le mot de passe !");
        } else if(!confirmPassword.getText().equals(password.getText())){
            Message.setText("Le mot de passe saisie n'est pas identique !");
        } else if(confirmPassword.getText().equals(password.getText())){
            Message.setText("Donnee complet !");
        }        
    }
    
    public StringProperty accountRoleProperty() {
        String role = " ";
            if (StokisteRole.isSelected()) {
            role = "STOCKIST";
            } else if (VendeurRole.isSelected()) {
            role = "SELLER";
        }
        return new SimpleStringProperty(role);
    }
    
    public void putToolbarInBorderpane(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountToolbar.fxml"));
            GridPane toolbar = fxmlLoader.load();
            BorderPane parent = (BorderPane) account_form.getParent();
            parent.setBottom(toolbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void createAccount(){
        confirm_button.setOnAction(actionEvent -> {
            if (this.accountService.isUniqueValue(username.getText())){
                UserEntity user = (UserEntity) FileIO.readFrom("user.dat");
                PersonEntity person = user.getPerson();
                if (this.personService.create(person)!=null){
                    user.setPerson(person);
                    user.setRole(String.valueOf(accountRoleProperty().getValue()));
                    if (this.userService.create(user)!=null){
                        AccountEntity account = new AccountEntity();
                        account.setUsername(username.getText());
                        account.setPassword(DigestUtils.sha256Hex(password.getText()));
                        account.setUser(user);
                        if (this.accountService.create(account)!=null){
                        FXMLLoader accountLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/account2Layout.fxml"));
                    BorderPane accountLayout;
                    try { accountLayout = accountLayoutLoader.load();
                        
                    } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            BorderPane dashboardLayout = (BorderPane) account_form.getParent();
                            dashboardLayout.setBottom(accountLayout);    
                        }
                    }
                }
            }
            else {
                error.setText("Nom d'utilisateur deja existe");
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),action -> error.setText("")));
                timeline.play();
            }
        });
    }
    public void previous(){
        previous_button.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) account_form.getParent();
            dashboardLayout.setBottom(getDashboardToolbar());
        });
    }
    
    private BorderPane getDashboardToolbar(){
        FXMLLoader accountLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/account2Layout.fxml"));
                    BorderPane accountLayout;
                    try {
                        accountLayout = accountLayoutLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        return accountLayout;
    }
}