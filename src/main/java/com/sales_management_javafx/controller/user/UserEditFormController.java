package com.sales_management_javafx.controller.user;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.controller.account.AccountEditFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserEditFormController implements Initializable {
    @FXML
    private VBox user_edit_form;
    @FXML
    private TextField user_lastname;
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
    private Button next_button;
    private final ToggleGroup sexeToggleGroup = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateNextButtonState();
        manSexType.setToggleGroup(sexeToggleGroup);
        womenSexType.setToggleGroup(sexeToggleGroup);
    }
    
    public void initialize(AccountEntity account){
        this.updateNextButtonState();
        setDataOnForm(account);
        if(account.getUser().getPerson().getFirstname()==null){
            System.out.println("ADMINISTRATEUR");
            manSexType.setSelected(true);
        }
        NumberTextField.requireNumber(user_phone);
        NumberTextField.requireNumber(user_cin);

        user_lastname.textProperty().addListener((observable, oldValue, newValue) -> {
            updatePerson(account.getUser().getPerson());
            loadData(account);
        });
        user_address.textProperty().addListener((observable, oldValue, newValue) -> {
            loadData(account);
        });
        user_phone.textProperty().addListener((observable, oldValue, newValue) -> {
            loadData(account);
        });
        user_email.textProperty().addListener((observable, oldValue, newValue) -> {
            loadData(account);
        });
        user_cin.textProperty().addListener((observable, oldValue, newValue) -> {
            updateNextButtonState();
            loadData(account);
        });
        this.updateUser(account.getUser());
        this.next(loadData(account));
    }
    
    public void setDataOnForm(AccountEntity account){
        if(account.getUser() != null){
            user_lastname.setText(account.getUser().getPerson().getLastname());
            user_address.setText(account.getUser().getPerson().getAddress());
            user_email.setText(account.getUser().getEmail());
            user_cin.setText(String.valueOf(account.getUser().getCin()));
            user_phone.setText(account.getUser().getNumber());
            if(account.getUser().getPerson().getGender()=='M'){
                manSexType.setSelected(true);
            }
            else{
                womenSexType.setSelected(true);
            }
        }
    }
    
    private AccountEntity loadData(AccountEntity account){
        AccountEntity new_account = new AccountEntity();
            UserEntity user = account.getUser();
            user.setPerson(updatePerson(account.getUser().getPerson()));
            new_account.setUser(updateUser(user));
            new_account.setUsername(account.getUsername());
            new_account.setPassword(account.getPassword());
            new_account.setId(account.getId());
            return new_account;
    }
    
    private void updateNextButtonState() {
        boolean isUserLastnameFilled = !user_lastname.getText().isEmpty();
        boolean isUserCinFilled = !user_cin.getText().isEmpty();
        next_button.setDisable( !isUserLastnameFilled || !isUserCinFilled);
}
        
    public PersonEntity updatePerson(PersonEntity person){
        person.setLastname(user_lastname.getText());
        person.setAddress(user_address.getText());
        if(manSexType.selectedProperty().getValue()){
            person.setGender('M');
        }
        else{
            person.setGender('F');
        }
        return person;
    }
    public UserEntity updateUser(UserEntity user){
        user.setEmail(user_email.getText());
        user.setNumber(user_phone.getText());
        user.setCin(user_cin.getText());
        return user;
    }
    public void next(AccountEntity account){
        next_button.setOnAction(actionEvent -> {
            ScrollPane scrollPane = (ScrollPane) user_edit_form.getParent().getParent().getParent();
            scrollPane.setContent(getAccountForm(account));
            
        });
    }
    private VBox getAccountForm(AccountEntity account){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountEditForm.fxml"));
        VBox accountForm;
        try {
            accountForm = fxmlLoader.load();
            AccountEditFormController accountEditFormController = fxmlLoader.getController();
            accountEditFormController.initialize(account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accountForm;
    }
}
