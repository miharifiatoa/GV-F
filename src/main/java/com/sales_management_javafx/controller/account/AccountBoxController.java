package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.composent.AccountGridPane;
import com.sales_management_javafx.controller.user.UserEditFormController;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
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
import org.sales_management.service.AccountService;

public class AccountBoxController implements Initializable {
    @FXML private StackPane accountBox;
    @FXML private VBox accountVBox;
    @FXML private VBox deleteVBox;
    @FXML private Label userNameLabel;
    @FXML private Label userAddresslabel;
    @FXML private Label userContactLabel;
    @FXML private Label userRoleLabel;
    @FXML private Label userCinLabel;
    @FXML private Label userEmailLabel;
    @FXML private Label lastname;
    @FXML private Label deleteText;
    @FXML private ImageView DeleteIcon;
    @FXML private ImageView EditIcon;
    @FXML private Button delete;
    @FXML private Button edit;
    @FXML private Button exit;
    @FXML private Button save;
    private final AccountService accountService;

    public AccountBoxController(){
        this.accountService = new AccountService();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.setDelete();
        this.setExit();
        deleteText.setText("Voulez vous vraiment supprimer cet compte ?");
    }
    public void initialize(AccountEntity account){
        if(account.getUser().getPerson() != null){
            userNameLabel.setText(account.getUser().getPerson().getLastname() + " " + account.getUser().getPerson().getFirstname());
            if(account.getUser().getPerson() != null){
                userNameLabel.setText(account.getUser().getPerson().getLastname());
                lastname.setText(account.getUser().getPerson().getLastname());
                userAddresslabel.setText(account.getUser().getPerson().getAddress());
                userContactLabel.setText(String.valueOf(account.getUser().getNumber()));
                userRoleLabel.setText(account.getUser().getRole());
                userCinLabel.setText(String.valueOf(account.getUser().getCin()));
                userEmailLabel.setText(account.getUser().getEmail());
            }
            if (!account.getUser().getSales().isEmpty() || !account.getUser().getArrivals().isEmpty() || !account.getUser().getShares().isEmpty()){
                delete.setDisable(true);
            }
            if(!account.getUser().getArrivals().isEmpty() || !account.getUser().getSales().isEmpty() || !account.getUser().getShares().isEmpty()) {
                delete.setDisable(true);
            }
        }
        this.putIcons();
        this.admin(account);
        this.onClickUpdate_button(account);
        this.setSave(account);
        this.admin(account);
    }
    private void putIcons(){
        DeleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
        EditIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
    }
    private void admin(AccountEntity account){
        if(account.getUser().getRole().equals("ADMIN")){
        delete.setDisable(true);
        }
    }
    private void setDelete(){
        delete.setOnAction(event->{
            accountVBox.setVisible(false);
            deleteVBox.setVisible(true);
        });
    }
    private void setExit(){
        exit.setOnAction(event->{
            accountVBox.setVisible(true);
            deleteVBox.setVisible(false);
        });
    }
    private void setSave(AccountEntity account){
            save.setOnAction(event -> {
                if (accountService.deleteById(account.getId()) != null){
                    ScrollPane scrollPane = (ScrollPane) accountBox.getParent().getParent().getParent().getParent();
                    GridPane gridPane = new AccountGridPane().getGridPane(accountService.getAll(),3);
                    scrollPane.setContent(gridPane);
                }
            });
        }

    public void onClickUpdate_button(AccountEntity account){
        edit.setOnAction(event->{
            ScrollPane user_borderpane = (ScrollPane) this.accountBox.getParent().getParent().getParent().getParent();
            user_borderpane.setContent(getUserEditForm(account));
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
                assert account != null;
                account.setUser(user);
            
                userEditFormController.initialize(account);
            }    
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userEditForm;
    }
}