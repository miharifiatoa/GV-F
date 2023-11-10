package com.sales_management_javafx.controller.user;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    @FXML
    private VBox user_form;
    @FXML
    private TextField user_lastname;
    @FXML
    private TextField user_firstname;
    @FXML
    private TextField user_address;
    @FXML
    private TextField user_cin;
    @FXML
    private TextField user_phone;
    @FXML
    private TextField user_email;
    @FXML
    private Button cancel_button;
    @FXML
    private Button next_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.closeForm();
        this.dataValidator();
        this.next();
        NumberTextField.requireIntegerOnly(user_phone,999999);
        NumberTextField.requireIntegerOnly(user_cin,999999);
    }
    public void dataValidator(){
        if (user_lastname.getText().isEmpty()){
            this.next_button.setDisable(true);
        }
        user_lastname.textProperty().addListener((observable, oldValue, newValue) -> next_button.setDisable(user_lastname.getText().isEmpty() || user_cin.getText().isEmpty()));
        user_cin.textProperty().addListener((observable, oldValue, newValue) -> next_button.setDisable(user_lastname.getText().isEmpty() || user_cin.getText().isEmpty()));
    }
    public void closeForm(){
        cancel_button.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) user_form.getParent();
            dashboardLayout.setBottom(getDashboardToolbar());
        });
    }
    public PersonEntity createPerson(){
        PersonEntity person = new PersonEntity();
        person.setLastname(user_lastname.getText());
        person.setFirstname(user_firstname.getText());
        person.setAddress(user_address.getText());
        person.setGender('M');
        return person;
    }
    public UserEntity createUser(){
        UserEntity user = new UserEntity();
        if (!user_phone.getText().isEmpty()){
            user.setNumber(Long.valueOf(user_phone.getText()));
        }
        if (!user_cin.getText().isEmpty()){
            user.setCin(Long.valueOf(user_cin.getText()));
        }
        user.setEmail(user_email.getText());
        user.setRole("SELLER");
        user.setPerson(this.createPerson());
        return user;
    }
    public void next(){
        next_button.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountForm.fxml"));
            try {
                VBox accountForm = fxmlLoader.load();
                BorderPane parent = (BorderPane) user_form.getParent();
                FileIO.writeTo("user.dat",this.createUser());
                parent.setBottom(accountForm);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
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
