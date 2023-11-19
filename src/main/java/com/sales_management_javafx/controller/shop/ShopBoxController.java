package com.sales_management_javafx.controller.shop;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.classes.FileIO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.sales_management.entity.ShopEntity;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public class ShopBoxController implements Initializable {
    @FXML
    private Label shopNameLabel;
    @FXML
    private Label shopAddresslabel;
    @FXML
    private Label shopContactlabel;
    @FXML
    private Label shopEmaillabel;
    @FXML private Label deleteText;
    @FXML private StackPane shopBoxStackpane;
    @FXML private VBox shopVBox;
    @FXML private VBox deleteVBox;
    @FXML
    private ImageView editIcon;
    @FXML private ImageView deleteIcon;
    @FXML private HBox shopHBox;
    @FXML private Button edit;
    @FXML private Button delete;
    @FXML private Button exit;
    @FXML private Button save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initialize(ShopEntity shop){
        this.shopNameLabel.setText(shop.getName());
        this.shopAddresslabel.setText(shop.getAddress());
        if(shop.getContact()!=null) this.shopContactlabel.setText(shop.getContact().toString());
        this.shopEmaillabel.setText(shop.getEmail());
    }
    public void initializeForAdmin(ShopEntity shop){
        this.putIcons();
        shopVBox.setVisible(true);
        deleteVBox.setVisible(false);
        this.initialize(shop);
        onUpdateShop(shop);
        getEditShopForm(shop);
        this.setDelete();
        this.setExit();
        this.deleteText.setText("Voulez vous vraiment supprimer ce boutique : " + shop.getName());
    }
    public void initializeForStockist(ShopEntity shop){
        this.shopHBox.setVisible(false);
        this.initialize(shop);
        this.onSelectShop(shop);
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
            ScrollPane scrollPane = (ScrollPane) shopBoxStackpane.getParent().getParent().getParent().getParent();
            scrollPane.setContent(getEditShopForm(shop));
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
    
    private void setDelete(){
        this.delete.setOnAction(event->{
            shopVBox.setVisible(false);
            deleteVBox.setVisible(true);
        });
    }
    private void setExit(){
        this.exit.setOnAction(event->{
            shopVBox.setVisible(true);
            deleteVBox.setVisible(false);
        });
    }
    
    public void handleSelectShop(){
        BorderPane shopLayoutBorderpane = (BorderPane) shopBoxStackpane.getParent().getParent().getParent().getParent().getParent();
        Button shareProductButton = (Button) shopLayoutBorderpane.lookup("#shareProductButton");
        shareProductButton.setDisable(false);
    }
}