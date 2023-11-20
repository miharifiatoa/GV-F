package com.sales_management_javafx.composent.share;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.controller.article_type.ArticleTypeCodeController;
import com.sales_management_javafx.controller.shop.ShopInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.sales_management.entity.ShareArticleEntity;
import org.sales_management.entity.ShareEntity;

import java.io.IOException;

public class ShareInfoGridPane {
    private final GridPane gridPane;

    public ShareInfoGridPane() {
        this.gridPane = new GridPane();
    }

    public GridPane getGridPane(ShareEntity share, int colSize){
        for (int i = 0 ; i < colSize ; i++){
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            constraints.setFillWidth(true);
            constraints.setPercentWidth((double) 100 /colSize);
            gridPane.getColumnConstraints().add(constraints);
        }
        int col = 0;
        int row = 0;
        gridPane.add(getShopInfo(share), col, row);
        row++;
        for (ShareArticleEntity shareArticle : share.getShareArticles()) {
            gridPane.add(this.getArticleCode(shareArticle), col, row);
            col++;
            if (col == colSize) {
                col = 0;
                row++;
            }
        }
        gridPane.getStyleClass().add("box");
        return gridPane;
    }
    private StackPane getArticleCode(ShareArticleEntity shareArticle){
        FXMLLoader articleCodeLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/article_type/articleCode.fxml"));
        StackPane articleCode;
        try {
            articleCode = articleCodeLoader.load();
            ArticleTypeCodeController articleTypeCodeController = articleCodeLoader.getController();
            articleTypeCodeController.initialize(shareArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return articleCode;
    }
    private VBox getShopInfo(ShareEntity share){
        FXMLLoader shopInfoLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopInfo.fxml"));
        VBox shopInfo;
        try {
            shopInfo = shopInfoLoader.load();
            ShopInfoController shopInfoController = shopInfoLoader.getController();
            shopInfoController.initialize(share);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shopInfo;
    }
}
