package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.commons.codec.digest.DigestUtils;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.AccountService;
import org.sales_management.service.PersonService;
import org.sales_management.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccountEditFormController implements Initializable {
    @FXML
    private VBox account_edit_form;
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
    private TextField adminPassword;
    @FXML
    private RadioButton StokisteRole;
    @FXML
    private RadioButton VendeurRole;
    @FXML
    private Label error;
    @FXML
    private Label admin;
    @FXML
    private Label Message;
    private ToggleGroup foctionToggleGroup = new ToggleGroup();
    private final PersonService personService;
    private final UserService userService;
    private final AccountService accountService;

    public AccountEditFormController() {
        this.accountService = new AccountService();
        this.personService = new PersonService();
        this.userService = new UserService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPasswdAdmin();
        VendeurRole.setToggleGroup(foctionToggleGroup);
        StokisteRole.setToggleGroup(foctionToggleGroup);
     
        this.setMessage();
        this.previous();
    }
    
    public void initialize(AccountEntity account) {
        username.setText(account.getUsername());
        if(null != account.getUser().getRole())switch (account.getUser().getRole()) {
            case "ADMIN" -> admin.setText("***ADMINISTRATEUR***");
            case "SELLER" -> VendeurRole.setSelected(true);
            case "STOCKIST" -> StokisteRole.setSelected(true);
            default -> {
            }
        }
        
        password.textProperty().addListener(((observable, oldValue, newValue) -> formValidation(account)));
        confirmPassword.textProperty().addListener(((observable, oldValue, newValue) -> formValidation(account)));
        adminPassword.textProperty().addListener(((observable, oldValue, newValue) -> formValidation(account)));
    this.formValidation(account);
    this.updateAccount(account);
    }

    public void formValidation(AccountEntity account){
            confirm_button.setDisable(username.getText().isEmpty() 
                    || !password.getText().equals(confirmPassword.getText())
                    || adminPassword.getText().isEmpty() 
                    || (adminPassword.getText() == null ? account.getPassword() != null : 
                !DigestUtils.sha256Hex(adminPassword.getText()).equals(getPasswdAdmin())));
            setMessage();
    }
    
    public void setMessage(){
        
        if(!password.getText().isEmpty() 
                && confirmPassword.getText().isEmpty() 
                && adminPassword.getText().isEmpty() ) {
            Message.setText("Confirmer le nouveau mot de passe !");
            } 
        else if(!confirmPassword.getText().isEmpty() 
                    && !confirmPassword.getText().equals(password.getText())) {
            Message.setText("Les deux mots de passe doivent etre fortement identique !");
            } 
        else if(password.getText().equals(confirmPassword.getText()) 
                    && confirmPassword.getText().equals(password.getText())
                    && adminPassword.getText().isEmpty()){
            Message.setText("Saisir le mot de passe administrateur pour confirmer !");
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
            BorderPane parent = (BorderPane) account_edit_form.getParent();
            parent.setBottom(toolbar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getPasswdAdmin(){
        String passwd = new String();
        System.out.println("");
        
        Collection<AccountEntity> accountCollection = (Collection<AccountEntity>) accountService.getAll();
        for(AccountEntity account : accountCollection){
            if(account.getUser().getRole().equals("ADMIN")){
                passwd = account.getPassword();
            }
        }
        return passwd;
    }
    public void updateAccount(AccountEntity account){
        
        confirm_button.setOnAction(actionEvent -> {
                
            UserEntity user = (UserEntity) FileIO.readFrom("editUser.dat");
                System.out.println("User :" + user.getId());
                account.setUsername(username.getText());
                if(!password.getText().isEmpty()){
                account.setPassword(DigestUtils.sha256Hex(password.getText()));
                }
                account.setUser(user);
                
    PersonEntity new_person = updatePerson(user.getPerson());
    
    if(new_person!=null){
        user.setPerson(new_person);
        UserEntity new_user = updateUser(user);

            if(new_user!=null){
            AccountEntity new_account = new AccountEntity();
                new_account.setUser(user);
                   new_account = this.accountService.update(account);    
                if (new_account!=null){
                    FXMLLoader accountLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountLayout.fxml"));
                    BorderPane accountLayout;
                    try {
                        accountLayout = accountLayoutLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    BorderPane dashboardLayout = (BorderPane) account_edit_form.getParent();
                    dashboardLayout.setBottom(accountLayout);
                    
                } else {
                System.out.println("new_account null " + new_account.getId());
            }
            } else {
                System.out.println("new_user null " + new_user.getId());
            }
        } else {
                System.out.println("new_person null " + new_person.getId());
            }
        });
    }
    public PersonEntity updatePerson(PersonEntity person){
        PersonEntity new_person = new PersonEntity();
        if(person.getId()==null){
            new_person = this.personService.create(person);
        } else {
            new_person = this.personService.update(person);
        }
        return new_person;
    }
    
    public UserEntity updateUser(UserEntity user){
        UserEntity new_user = new UserEntity();
        if(user.getId()==null){
            new_user = this.userService.create(user);
        } else {
            new_user = this.userService.update(user);
        }
        return new_user;
    }
    
    
    public void previous(){
        previous_button.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) account_edit_form.getParent();
            dashboardLayout.setBottom(getDashboardToolbar());
        });
    }
    private VBox getUserForm(){
        FXMLLoader userFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/userForm.fxml"));
        VBox userForm;
        try {
            userForm = userFormLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userForm;
    }
    private BorderPane getDashboardToolbar(){
        FXMLLoader accountLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountLayout.fxml"));
                    BorderPane accountLayout;
                    try {
                        accountLayout = accountLayoutLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
        return accountLayout;
    }
}
