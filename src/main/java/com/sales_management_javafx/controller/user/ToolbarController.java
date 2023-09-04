package com.sales_management_javafx.controller.user;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ToolbarController implements Initializable {
    @FXML
    private Button create_user_button;
    @FXML
    private GridPane user_toolbar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onClickCreate_button();
    }

    public void onClickCreate_button(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/user/userForm.fxml"));
        create_user_button.setOnAction(event->{
            try {
                BorderPane user_borderpane = (BorderPane) user_toolbar.getParent();
                VBox userForm = fxmlLoader.load();
                user_borderpane.setBottom(userForm);
            } catch (IOException e) {
                System.out.println("Error: error while loading user userForm.fxml");
            }
        });
    }
}
