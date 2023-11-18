package com.sales_management_javafx.controller.account;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountToolbarController implements Initializable {
    @FXML
    private Button create_account_button;
    @FXML
    private GridPane account_toolbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onClickCreate_button();
    }

    public void onClickCreate_button(){
        create_account_button.setOnAction(event->{
            FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/userForm.fxml"));
            try {
                BorderPane user_borderpane = (BorderPane) account_toolbar.getParent();
                VBox userForm = fxmlLoader.load();
                user_borderpane.setBottom(userForm);
            } catch (IOException e) {
                System.out.println("Error: error while loading user userForm.fxml");
                e.printStackTrace();
            }
        });
    }
}
