package com.sales_management_javafx.composent;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.account.AccountBoxController;
import com.sales_management_javafx.controller.article.ArticleInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import org.sales_management.entity.AccountEntity;
import org.sales_management.entity.ArticleEntity;

import java.io.IOException;
import java.util.Collection;

public class AccountGridPane {
    private final GridPane gridPane;

    public AccountGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(Collection<AccountEntity> accounts, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        for (AccountEntity account : accounts) {
            gridPane.add(this.getAccountBox(account), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("gridpane");
        return gridPane;
    }
    private StackPane getAccountBox(AccountEntity account){
        FXMLLoader accountBoxLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/accountBox.fxml"));
        StackPane accountBox;
        try {
            accountBox = accountBoxLoader.load();
            AccountBoxController accountBoxController = accountBoxLoader.getController();
            accountBoxController.initialize(account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accountBox;
    }
}
