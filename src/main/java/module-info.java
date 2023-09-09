module com.sales_management_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires Sales.management;
    requires org.jetbrains.annotations;

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

    exports com.sales_management_javafx.enums;
    opens com.sales_management_javafx.enums to javafx.fxml;

    exports com.sales_management_javafx.controller.shop;
    opens com.sales_management_javafx.controller.shop to javafx.fxml;

    exports com.sales_management_javafx.controller.account;
    opens com.sales_management_javafx.controller.account to javafx.fxml;

    exports com.sales_management_javafx.controller.inventory;
    opens com.sales_management_javafx.controller.inventory to javafx.fxml;

}