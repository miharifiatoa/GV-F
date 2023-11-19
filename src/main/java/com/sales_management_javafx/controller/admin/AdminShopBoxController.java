package com.sales_management_javafx.controller.admin;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import com.sales_management_javafx.controller.shop.ConfirmDeleteShopController;
import com.sales_management_javafx.controller.shop.ShopEditFormController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.ShopEntity;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class AdminShopBoxController implements Initializable {
    @FXML
    private Label shopNameLabel;
    @FXML
    private Label shopAddresslabel;
    @FXML
    private Label shopContactlabel;
    @FXML
    private Label shopEmaillabel;
    @FXML
    private StackPane shopBoxStackpane;
    @FXML
    private ImageView editIcon;
    @FXML
    private ImageView deleteIcon;
    
    @FXML private Button edit;
    @FXML private Button delete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void initialize(ShopEntity shop){
        putIcons();
        this.shopNameLabel.setText(shop.getName());
        this.shopAddresslabel.setText(shop.getAddress());
        if(shop.getContact()!=null) this.shopContactlabel.setText(shop.getContact().toString());
        this.shopEmaillabel.setText(shop.getEmail());
        
        onUpdateShop(shop);
        onDeleteShop(shop);
        getEditShopForm(shop);
        getDeleteShopForm(shop);
    }
    public void putIcons(){
        deleteIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/DeleteIcon.png"))));
        editIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/EditIcon.png"))));
    }
    public void onSelectShop(ShopEntity shop){
        shopBoxStackpane.setOnMouseClicked(event->{
            GridPane gridPane = (GridPane) shopBoxStackpane.getParent();
            for (Node node : gridPane.getChildren()){
                node.getStyleClass().remove("active");
            }
            shopBoxStackpane.getStyleClass().add("active");
            FileIO.writeTo("shop.dat",shop);
            handleSelectShop();
        });
    }
    
    public void onUpdateShop(ShopEntity shop){
        this.edit.setOnAction(event->{
            BorderPane shop_borderPane = (BorderPane) this.shopBoxStackpane.getParent().getParent().getParent().getParent().getParent();
            
            shop_borderPane.setBottom(getEditShopForm(shop));
        });
        
    }
    
    public VBox getEditShopForm(ShopEntity shop){
            FXMLLoader shopEditFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopEditForm.fxml"));
            VBox shopEditFormVBox = null;
            try{
                shopEditFormVBox = shopEditFormLoader.load();
                ShopEditFormController shopEditFormController = shopEditFormLoader.getController();
                if(shop!=null){
                    shopEditFormController.initialize(shop);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return shopEditFormVBox;
    }
    
    public void onDeleteShop(ShopEntity shop){
        this.delete.setOnAction(event->{
            BorderPane shop_borderPane = (BorderPane) this.shopBoxStackpane.getParent().getParent().getParent().getParent().getParent();
            
            shop_borderPane.setBottom(getDeleteShopForm(shop));
        });
        
    }
    
    public VBox getDeleteShopForm(ShopEntity shop){
            FXMLLoader shopEditFormLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/confirmDeleteShop.fxml"));
            VBox deleteFormVBox = null;
            try{
                deleteFormVBox = shopEditFormLoader.load();
                ConfirmDeleteShopController confirmDeleteShopController = shopEditFormLoader.getController();
                if(shop!=null){
                    confirmDeleteShopController.initialize(shop);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return deleteFormVBox;
    }
    
    public void handleSelectShop(){
        BorderPane shopLayoutBorderpane = (BorderPane) shopBoxStackpane.getParent().getParent().getParent().getParent().getParent();
        Button shareProductButton = (Button) shopLayoutBorderpane.lookup("#shareProductButton");
        shareProductButton.setDisable(false);
    }
}