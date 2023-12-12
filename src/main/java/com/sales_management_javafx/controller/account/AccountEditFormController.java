package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.AccountGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.GridPane;

public class AccountEditFormController implements Initializable {
    @FXML
    private VBox account_edit_form;
    @FXML
    private Button confirm_button;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
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

    }
    
    public void initialize(AccountEntity account) {
        username.setText(account.getUsername());
        password.textProperty().addListener(((observable, oldValue, newValue) -> formValidation(account)));
        confirmPassword.textProperty().addListener(((observable, oldValue, newValue) -> formValidation(account)));
        this.formValidation(account);
        this.updateAccount(account);
    }

    private void formValidation(AccountEntity account){
        confirm_button.setDisable(username.getText().isEmpty() || !password.getText().equals(confirmPassword.getText()));
    }
    
    private void updateAccount(AccountEntity account){
        confirm_button.setOnAction(actionEvent -> {
            account.setUsername(username.getText());
            if(!password.getText().isEmpty()){
                account.setPassword(DigestUtils.sha256Hex(password.getText()));
            }
            PersonEntity new_person = updatePerson(account.getUser().getPerson());
            if(new_person!=null){
                account.getUser().setPerson(new_person);
                UserEntity new_user = updateUser(account.getUser());
                if(new_user!=null){
                    AccountEntity new_account;
                    new_account = this.accountService.update(account);
                    if (new_account!=null){
                        ScrollPane accountLayoutScrollpane = (ScrollPane) account_edit_form.getParent().getParent().getParent();
                        GridPane gridPane = new AccountGridPane().getGridPane(accountService.getAll(),3);
                        accountLayoutScrollpane.setContent(gridPane);
                    }
                }
            }
        });
    }
    private PersonEntity updatePerson(PersonEntity person){
        PersonEntity new_person = new PersonEntity();
        if(person.getId()==null){
            new_person = this.personService.create(person);
        } else {
            new_person = this.personService.update(person);
        }
        return new_person;
    }
    
    private UserEntity updateUser(UserEntity user){
        UserEntity new_user = new UserEntity();
        if(user.getId()==null){
            new_user = this.userService.create(user);
        } else {
            new_user = this.userService.update(user);
        }
        return new_user;
    }
}
