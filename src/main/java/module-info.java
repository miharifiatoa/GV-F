module com.sales_management_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires Sales.management;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.codec;

    exports com.sales_management_javafx;
    opens com.sales_management_javafx to javafx.fxml;

    exports com.sales_management_javafx.controller;
    opens com.sales_management_javafx.controller to javafx.fxml;

    exports com.sales_management_javafx.controller.login;
    opens com.sales_management_javafx.controller.login to javafx.fxml;

    exports com.sales_management_javafx.controller.user;
    opens com.sales_management_javafx.controller.user to javafx.fxml;

    exports com.sales_management_javafx.controller.dashboard;
    opens com.sales_management_javafx.controller.dashboard to javafx.fxml;

    exports com.sales_management_javafx.controller.article;
    opens com.sales_management_javafx.controller.article to javafx.fxml;

    exports com.sales_management_javafx.enums;
    opens com.sales_management_javafx.enums to javafx.fxml;

    exports com.sales_management_javafx.controller.shop;
    opens com.sales_management_javafx.controller.shop to javafx.fxml;

    exports com.sales_management_javafx.controller.account;
    opens com.sales_management_javafx.controller.account to javafx.fxml;

    exports com.sales_management_javafx.controller.inventory;
    opens com.sales_management_javafx.controller.inventory to javafx.fxml;

    exports com.sales_management_javafx.controller.product;
    opens com.sales_management_javafx.controller.product to javafx.fxml;

    exports com.sales_management_javafx.controller.product_type;
    opens com.sales_management_javafx.controller.product_type to javafx.fxml;

    exports com.sales_management_javafx.controller.article_type;
    opens com.sales_management_javafx.controller.article_type to javafx.fxml;

    exports com.sales_management_javafx.controller.seller;
    opens com.sales_management_javafx.controller.seller to javafx.fxml;

    exports com.sales_management_javafx.controller.arrival;
    opens com.sales_management_javafx.controller.arrival to javafx.fxml;

    exports com.sales_management_javafx.controller.stockist;
    opens com.sales_management_javafx.controller.stockist to javafx.fxml;

    exports com.sales_management_javafx.controller.share;
    opens com.sales_management_javafx.controller.share to javafx.fxml;

    exports com.sales_management_javafx.controller.sale;
    opens com.sales_management_javafx.controller.sale to javafx.fxml;

    exports com.sales_management_javafx.controller.admin;
    opens com.sales_management_javafx.controller.admin to javafx.fxml;

    exports com.sales_management_javafx.controller.product_category;
    opens com.sales_management_javafx.controller.product_category to javafx.fxml;
}