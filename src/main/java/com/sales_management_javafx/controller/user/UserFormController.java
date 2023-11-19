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
import javafx.scene.layout.VBox;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

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
    private RadioButton manSexType;
    @FXML
    private RadioButton womenSexType;
    @FXML
    private Button cancel_button;
    @FXML
    private Button next_button;
    private ToggleGroup sexeToggleGroup = new ToggleGroup();
    private BooleanProperty isNextButtonDisabled = new SimpleBooleanProperty(true);
    private char sexe;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.closeForm();
        this.next();
    
    manSexType.setToggleGroup(sexeToggleGroup);
    womenSexType.setToggleGroup(sexeToggleGroup);
    
    NumberTextField.requireNumber(user_phone);
    NumberTextField.requireNumber(user_cin);

    user_lastname.textProperty().addListener((observable, oldValue, newValue) -> updateNextButtonState());
    user_cin.textProperty().addListener((observable, oldValue, newValue) -> updateNextButtonState());
    sexeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateNextButtonState());
    accountRoleProperty();
    updateNextButtonState();
        
    }
    public char accountRoleProperty() {
        char sex = 0;
            if (manSexType.isSelected()) {
            sex = 'M';
            } else if (womenSexType.isSelected()) {
            sex = 'F';
        }
        return sex;
    }

    private void updateNextButtonState() {
    boolean isRadioSelected = sexeToggleGroup.getSelectedToggle() != null;
    boolean isUserLastnameFilled = !user_lastname.getText().isEmpty();
    boolean isUserCinFilled = !user_cin.getText().isEmpty();
    accountRoleProperty();
    next_button.setDisable(!isRadioSelected || !isUserLastnameFilled || !isUserCinFilled);
}

    
    public BooleanProperty nextButtonDisabledProperty() {
    return isNextButtonDisabled;
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
        person.setGender(accountRoleProperty());
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