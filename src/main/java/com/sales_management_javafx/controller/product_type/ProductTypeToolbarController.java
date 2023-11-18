package com.sales_management_javafx.controller.product_type;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.MenuGridPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTypeToolbarController implements Initializable {
    @FXML
    private Button exitFromInventoryButton;

    @FXML
    private StackPane toolbar;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;
    @FXML
    private ImageView ExitInventoryIcon;
    public ProductTypeToolbarController() {
        this.menuGridPane = new MenuGridPane();
        this.menuIcon = new MenuIcon();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onExitFromInventoryButton();
        this.putIcons();
    }
    private void onExitFromInventoryButton(){
        exitFromInventoryButton.setOnAction(event->{
            System.out.println();
            BorderPane dashboard = (BorderPane)this.toolbar.getParent().getParent().getParent().getParent() ;
            dashboard.setLeft(this.menuGridPane.getGridPane(menuIcon.getMenuIcons(),1));
            dashboard.setCenter(null);
        });
    }
    private void putIcons(){
        ExitInventoryIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ExitInventoryIcon.png"))));
    }
}
