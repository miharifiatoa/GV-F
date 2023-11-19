package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.user.UserEditFormController;
import java.io.IOException;
import javafx.fxml.Initializable;
import org.sales_management.entity.AccountEntity;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.PersonEntity;
import org.sales_management.entity.UserEntity;

public class AccountBoxController implements Initializable {
    @FXML private StackPane accountBox;
    @FXML private Label userNameLabel;
    @FXML private Label userAddresslabel;
    @FXML private Label userContactLabel;
    @FXML private Label userRoleLabel;
    @FXML private Label userCinLabel;
    @FXML private Label userEmailLabel;
    @FXML private Label lastname;
    @FXML private ImageView DeleteIcon;
    @FXML private ImageView EditIcon;
    @FXML private Button delete;
    @FXML private Button edit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(AccountEntity account){
        putIcons();

        if(account.getUser().getPerson() != null){
        userNameLabel.setText(account.getUser().getPerson().getLastname() + " " + 
                                account.getUser().getPerson().getFirstname());
        lastname.setText(account.getUser().getPerson().getLastname());
        userAddresslabel.setText(account.getUser().getPerson().getAddress());
        userContactLabel.setText(String.valueOf(account.getUser().getNumber()));
        userRoleLabel.setText(account.getUser().getRole());
        userCinLabel.setText(String.valueOf(account.getUser().getCin()));
        userEmailLabel.setText(account.getUser().getEmail());
        
        }
        
        admin(account);
        onClickUpdate_button(account);
        onClickDelete_button(account);
    }
    public void putIcons(){
        DeleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
        EditIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
    }
    public void admin(AccountEntity account){
        if(account.getUser().getRole().equals("ADMIN")){
        delete.setDisable(true);
        }
    }
    public void onClickDelete_button(AccountEntity account){
        delete.setOnAction(event->{
            BorderPane user_borderpane = (BorderPane) this.accountBox.getParent().getParent().getParent().getParent().getParent().getParent();
            user_borderpane.setBottom(getUserDeleteForm(account));
        });
    }
 private VBox getUserDeleteForm(AccountEntity account){
        FXMLLoader userDeleteFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/confirm.fxml"));
        VBox userDeleteForm;
        try {
            userDeleteForm = userDeleteFormLoader.load();
            ConfirmController confirmController = userDeleteFormLoader.getController();
            if(account!=null && account.getUser()!=null && account.getUser().getPerson()!=null){
                confirmController.initialize(account);
            }    
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userDeleteForm;
    }
 
    public void onClickUpdate_button(AccountEntity account){
        edit.setOnAction(event->{
            BorderPane user_borderpane = (BorderPane) this.accountBox.getParent().getParent().getParent().getParent().getParent().getParent();
            user_borderpane.setBottom(getUserEditForm(account));
        });
    }
    
    private VBox getUserEditForm(AccountEntity account){
        FXMLLoader userEditFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/userEditForm.fxml"));
        VBox userEditForm;
        try {
            userEditForm = userEditFormLoader.load();
            UserEditFormController userEditFormController = userEditFormLoader.getController();
            if(account!=null && account.getUser()!=null && account.getUser().getPerson()!=null){
                userEditFormController.initialize(account);
            } else {
                PersonEntity personne = new PersonEntity();
                personne.setLastname("ADMINISTRATEUR");
                personne.setGender('M');
                
                UserEntity user = new UserEntity();
                user.setPerson(personne);
                user.setRole("ADMIN");
                account.setUser(user);
            
                userEditFormController.initialize(account);
            }    
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userEditForm;
    }
}