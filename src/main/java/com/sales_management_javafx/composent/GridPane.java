package com.sales_management_javafx.composent;

import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;

import java.util.Collection;

public class GridPane extends javafx.scene.layout.GridPane {
    private final javafx.scene.layout.GridPane gridPane;
    public GridPane() {
        this.gridPane = new javafx.scene.layout.GridPane();
    }
    public javafx.scene.layout.GridPane getGridPane(int colSize,int dataSize,Node node){
        int row = 0;
        int col = 0;
        for (int i=0 ; i<dataSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setPercentWidth((double) 100 /colSize);
            constraints.setHgrow(Priority.ALWAYS);
            this.gridPane.getColumnConstraints().add(constraints);
            this.gridPane.add(node,col,row);
            col++;
            if (i==colSize){
                col=0;
                row++;
            }
        }
        return gridPane;
    }
}
