package com.sales_management_javafx.fxml.file;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.sales_management.entity.AccountEntity;
import org.sales_management.service.AccountService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountInformationController implements Initializable {
    @FXML
    private GridPane account_information;
    @FXML
    private Button close_account_information_button;
    @FXML
    private final AccountService accountService;

    public AccountInformationController() {
        this.accountService = new AccountService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showInformation();
        this.closeInformation();
    }
    public void showInformation(){
        try(FileInputStream fileInputStream = new FileInputStream(String.valueOf(SalesApplication.class.getResource("file/account_data.json")))) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            AccountEntity account = (AccountEntity) objectInputStream.readObject();
            account_information.getChildren().add(new Label(account.getUser().getPerson().getLastname()));
            account_information.getChildren().add(new Label(account.getUser().getPerson().getFirstname()));
            account_information.getChildren().add(new Label(account.getUser().getPerson().getAddress()));
            account_information.getChildren().add(new Label(account.getUser().getRole()));
            account_information.getChildren().add(new Label(String.valueOf(account.getUser().getCin())));
            account_information.getChildren().add(new Label(String.valueOf(account.getUser().getEmail())));
            account_information.getChildren().add(new Label(String.valueOf(account.getUser().getNumber())));
            account_information.getChildren().add(new Label(String.valueOf(account.getUser().getNumber())));
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeInformation(){
        close_account_information_button.setOnAction(actionEvent -> {
            FXMLLoader toolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountToolbar.fxml"));
            try {
                BorderPane parent = (BorderPane) account_information.getParent();
                GridPane toolbar = toolbarLoader.load();
                parent.setBottom(toolbar);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
