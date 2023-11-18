package com.sales_management_javafx.controller.user;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.classes.NumberTextField;
import com.sales_management_javafx.controller.account.AccountEditFormController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;

public class UserEditFormController implements Initializable {
    @FXML
    private VBox user_edit_form;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.closeForm();
        
        updateNextButtonState();
        manSexType.setToggleGroup(sexeToggleGroup);
        womenSexType.setToggleGroup(sexeToggleGroup);
        
    }
    
    public void initialize(AccountEntity account){
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
    user_firstname.textProperty().addListener((observable, oldValue, newValue) -> { 
        updateNextButtonState(); 
        updatePerson(account.getUser().getPerson());
        loadData(account);
    });
    user_address.textProperty().addListener((observable, oldValue, newValue) -> { 
        
//        updateUser(account.getUser());
        loadData(account);
        
    });
    user_phone.textProperty().addListener((observable, oldValue, newValue) -> { 
        
//        updateUser(account.getUser());
        loadData(account);
        
    });
    user_email.textProperty().addListener((observable, oldValue, newValue) -> { 
        
//        updateUser(account.getUser());
        loadData(account);
        
    });
    user_cin.textProperty().addListener((observable, oldValue, newValue) -> { 
        updateNextButtonState();
//        updateUser(account.getUser());
        loadData(account);
//        
    });
    this.updateUser(account.getUser());
    this.next(loadData(account));
    }
    
    public void setDataOnForm(AccountEntity account){
        if(account.getUser() != null){
        user_lastname.setText(account.getUser().getPerson().getLastname());
        user_firstname.setText(account.getUser().getPerson().getFirstname());
        user_address.setText(account.getUser().getPerson().getAddress());
        user_email.setText(account.getUser().getEmail());
        if(account.getUser().getCin()!=null) { user_cin.setText(account.getUser().getCin().toString()); }
        if(account.getUser().getNumber()!=null) { user_phone.setText(account.getUser().getNumber().toString()); }
        if(account.getUser().getPerson().getGender()=='M'){
            manSexType.setSelected(true);
        } else if(account.getUser().getPerson().getGender()=='F'){
            womenSexType.setSelected(true);
        }
    }        manSexType.setSelected(true);

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
        
    public void closeForm(){
        cancel_button.setOnAction(actionEvent -> {
            BorderPane dashboardLayout = (BorderPane) user_edit_form.getParent();
            dashboardLayout.setBottom(getDashboardToolbar());
        });
    }
    public PersonEntity updatePerson(PersonEntity person){
            
            person.setLastname(user_lastname.getText());
            person.setFirstname(user_firstname.getText());
            person.setAddress(user_address.getText());
        
        if( Objects.equals(manSexType.selectedProperty().getValue(), "true")){
            person.setGender('M');
            } else if(Objects.equals(womenSexType.selectedProperty().getValue(), "true")){
            person.setGender('F');
            }
            
        return person;
    }
    public UserEntity updateUser(UserEntity user){
        if(user.getRole().equals("ADMIN")){
            user.setId(Long.valueOf("1"));
            user.setRole("ADMIN");
            }
            user.setEmail(user_email.getText());
            if(!user_phone.getText().isEmpty()) { user.setNumber(Long.valueOf(user_phone.getText())); }
            if(!user_cin.getText().isEmpty()) { user.setCin(Long.valueOf(user_cin.getText())); }
        
        return user;
    }
    public void next(AccountEntity account){
        next_button.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountEditForm.fxml"));
            try {
                VBox accountEditForm = fxmlLoader.load();
                AccountEditFormController accountEditFormController = fxmlLoader.getController();
                accountEditFormController.initialize(account);
                BorderPane parent = (BorderPane) user_edit_form.getParent();
                FileIO.writeTo("editUser.dat",account.getUser());
                parent.setBottom(accountEditForm);
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
