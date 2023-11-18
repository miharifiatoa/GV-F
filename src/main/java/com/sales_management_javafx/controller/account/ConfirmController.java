package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import java.io.IOException;
import javafx.fxml.Initializable;
import org.sales_management.entity.AccountEntity;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.AccountService;
import org.sales_management.service.PersonService;
import org.sales_management.service.UserService;

public class ConfirmController implements Initializable {
    @FXML VBox confirm_form;
    @FXML Label messLabel;
    @FXML Button confirmer;
    @FXML Button annuler;
    private final PersonService personService;
    private final UserService userService;
    private final AccountService accountService;
    public ConfirmController() {
        this.accountService = new AccountService();
        this.personService = new PersonService();
        this.userService = new UserService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(AccountEntity account){
        if(account!=null){
            messLabel.setText("Attention, l'utilisateur : "+account.getUsername()+" va etre supprimer !");
        }
        delete(account);
        closeForm();
    }
    public void delete(AccountEntity account){
        confirmer.setOnAction(actionEvent -> {
            PersonEntity pers = account.getUser().getPerson();
            UserEntity user = account.getUser();

                    try {
                        AccountEntity dropedAccount = this.accountService.deleteById(account.getId());
                        if(dropedAccount!=null){
                        FXMLLoader accountLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/account2Layout.fxml"));
                        BorderPane accountLayout;
                            try {
                                accountLayout = accountLayoutLoader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            BorderPane dashboardLayout = (BorderPane) confirm_form.getParent();
                            dashboardLayout.setBottom(accountLayout);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
               
        });
    }
    
    public void closeForm(){
        annuler.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) confirm_form.getParent();
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
