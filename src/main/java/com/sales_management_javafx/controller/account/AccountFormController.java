package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.composent.AccountGridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

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
    private Label error;
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
        this.formValidation();
        this.createAccount();
        this.previous();
    }
    public void formValidation(){
        if (username.getText().isEmpty() || password.getText().isEmpty()){
            confirm_button.setDisable(true);
        }
        username.textProperty().addListener(((observableValue, s, t1) -> confirm_button.setDisable(username.getText().isEmpty() || password.getText().isEmpty())));
        password.textProperty().addListener(((observableValue, s, t1) -> confirm_button.setDisable(username.getText().isEmpty() || password.getText().isEmpty())));
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
                    if (this.userService.create(user)!=null){
                        AccountEntity account = new AccountEntity();
                        account.setUsername(username.getText());
                        account.setPassword(DigestUtils.sha256Hex(password.getText()));
                        account.setUser(user);
                        if (this.accountService.create(account)!=null){
                            BorderPane dashboardLayout = (BorderPane) this.account_form.getParent();
                            ScrollPane dashboardLayoutScrollpane = (ScrollPane) dashboardLayout.lookup("#dashboardLayoutScrollpane");
                            GridPane accountGridPane = new AccountGridPane().getGridPane(new AccountService().getAll(),4);
                            dashboardLayoutScrollpane.setContent(accountGridPane);
                            dashboardLayout.setBottom(this.getDashboardToolbar());
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
            dashboardLayout.setBottom(getUserForm());
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
    private StackPane getDashboardToolbar(){
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboardToolbar;
    }
}
