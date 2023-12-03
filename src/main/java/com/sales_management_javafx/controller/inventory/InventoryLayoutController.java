package com.sales_management_javafx.controller.inventory;

import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.MenuGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import org.sales_management.service.AccountService;
import org.sales_management.service.UserService;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryLayoutController implements Initializable {
    @FXML
    private BorderPane inventoryLayoutBorderpane;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;
    private final AccountService accountService;
    private final UserService userService;

    public InventoryLayoutController() {
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
