package com.sales_management_javafx.composent;

import java.util.Collection;

public class GridPane extends javafx.scene.layout.GridPane {
    private javafx.scene.layout.GridPane gridPane;
    public GridPane() {
        GridPane gridPane = new GridPane();
    }
    public <T> javafx.scene.layout.GridPane getGridPane(Collection<T> collection){
        return gridPane;
    }
}
