package com.sales_management_javafx.controller.product;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.composent.MenuGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductToolbarController implements Initializable {
    @FXML
    private Button exitFromInventoryButton;
    @FXML
    private Button shareProductButton;
    @FXML
    private Button createProductButton;
    @FXML
    private StackPane toolbar;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;
    @FXML
    private ImageView showShareListIcon;
    @FXML
    private ImageView ExitInventoryIcon;
    @FXML
    private ImageView createIcon;
    public ProductToolbarController() {
        this.menuGridPane = new MenuGridPane();
        this.menuIcon = new MenuIcon();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onExitFromInventoryButton();
        this.onShowShareProductLayout();
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
    private void onShowShareProductLayout(){
        shareProductButton.setOnAction(event->{
            BorderPane productBoxLayoutBorderpane = (BorderPane) this.toolbar.getParent();
            productBoxLayoutBorderpane.setBottom(getShareProductLayout());
        });
    }
    private StackPane getShareProductLayout(){
        FXMLLoader shareProductLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/inventory/shareProductLayout.fxml"));
        StackPane stackPane;
        try {
            stackPane = shareProductLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stackPane;
    }
    private void putIcons(){
        showShareListIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ShowShareListIcon.png"))));
        createIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/CreateIcon.png"))));
        ExitInventoryIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/ExitInventoryIcon.png"))));
    }
}
