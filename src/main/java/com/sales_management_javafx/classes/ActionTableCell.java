package com.sales_management_javafx.classes;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.interfaces.ActionButtonClick;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

public class ActionTableCell {
    private final ActionButtonClick deleteAction;
    private final ActionButtonClick infoAction;
    public ActionTableCell(ActionButtonClick deleteAction , ActionButtonClick infoAction) {
        this.deleteAction = deleteAction;
        this.infoAction = infoAction;
    }

    public GridPane createActionPane(){
        GridPane action_pane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        action_pane.setAlignment(Pos.CENTER);
        action_pane.setHgap(10);
        action_pane.getColumnConstraints().addAll(col1, col2);
        action_pane.add(this.createDeleteButton(),0,0);
        action_pane.add(this.createInfoButton(),1,0);
        return  action_pane;
    }
    public @NotNull Button createDeleteButton(){
        Button delete_button = new Button();
        delete_button.setOnAction(actionEvent -> deleteAction.onClick());
        delete_button.getStyleClass().add("action-btn");
        delete_button.setGraphic(this.createImageDeleteView());
        return delete_button;
    }
    public @org.jetbrains.annotations.NotNull Button createInfoButton(){
        Button info_button = new Button();
        info_button.setOnAction(actionEvent -> infoAction.onClick());
        info_button.getStyleClass().add("action-btn");
        info_button.setGraphic(this.createImageInfoView());
        return info_button;
    }
    private @NotNull ImageView createImageDeleteView(){
        Image delete_icon = new Image(String.valueOf(SalesApplication.class.getResource("icone/poubelle (3).png")));
        ImageView delete_view = new ImageView(delete_icon);
        delete_view.setFitWidth(15);
        delete_view.setFitHeight(15);
        return delete_view;
    }
    private @NotNull ImageView createImageInfoView(){
        Image info_icon = new Image(String.valueOf(SalesApplication.class.getResource("icone/liste.png")));
        ImageView info_view = new ImageView(info_icon);
        info_view.setFitWidth(15);
        info_view.setFitHeight(15);
        return info_view;
    }
}
