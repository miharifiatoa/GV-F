package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardLayoutController implements Initializable {
    @FXML
    private BorderPane dashboard;
    @FXML
    private Button inventory_button;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onGoToInventory();
        dashboard.setLeft(getMenuBar());
    }
    public VBox getMenuBar(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/menu.fxml"));
        VBox menuBar;
        try {
            menuBar = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return menuBar;
    }
    public void onGoToInventory(){
        inventory_button.setText("Aller dans l' inventaire");
        inventory_button.setOnAction(actionEvent -> {
            if (Objects.equals(inventory_button.getText(), "Aller dans l' inventaire")){
                inventory_button.setText("Quitter l' inventaire");
                inventory_button.getStyleClass().remove("inventory-button");
                inventory_button.getStyleClass().add("exit-inventory-button");
                FXMLLoader loadInventory = new FXMLLoader(SalesApplication.class.getResource("fxml/inventory/inventoryLayout.fxml"));
                try {
                    BorderPane inventoryLayout = loadInventory.load();
                    dashboard.setLeft(null);
                    dashboard.setCenter(inventoryLayout);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                inventory_button.setText("Aller dans l' inventaire");
                inventory_button.getStyleClass().remove("exit-inventory-button");
                inventory_button.getStyleClass().add("inventory-button");
                dashboard.setLeft(this.getMenuBar());
                dashboard.setCenter(null);
            }

        });
    }
}
