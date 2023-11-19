package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.actions.Payment;
import com.sales_management_javafx.classes.DecimalFormat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.sales_management.entity.UserEntity;
import org.sales_management.service.PaymentModeService;
import org.sales_management.service.PaymentService;
import org.sales_management.session.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardToolbarController implements Initializable {
    @FXML private StackPane dashboardToolbar;
    @FXML private ImageView newAccountIcon;
    @FXML private ImageView newShopIcon;
    @FXML private ImageView MoneyIcon;
    @FXML private Button newAccount;
    @FXML private Button newShop;
    @FXML private Button newPayment;
    @FXML private Button logout;
    @FXML private Button exit;
    @FXML private Label payment;
    @FXML private GridPane dashboardToolbarGridpane;
    @FXML private GridPane paymentGridpane;
    @FXML private ScrollPane paymentScrollPane;
    private final UserEntity user;
    private final PaymentService paymentService;
    private final PaymentModeService paymentModeService;
    public DashboardToolbarController() {
        this.paymentService = new PaymentService();
        this.paymentModeService = new PaymentModeService();
        this.user = SessionManager.getSession().getCurrentUser();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setShopLayout();
        this.setAccountLayout();
        this.setPaymentLayout();
        this.putIcons();
        this.logout.setOnAction(event->setLogout());
        this.payment.setText("Versement : " + DecimalFormat.format(Payment.getPaymentByDay(LocalDate.now()))  + "Ar");
        this.dashboardToolbarGridpane.setVisible(true);
        this.paymentGridpane.setVisible(false);
        this.setPayment();
        this.setExit();
//        this.setPaymentScrollPane();
        if (user == null){
            this.setLogout();
        }
    }
    
    private void setPayment(){
        this.payment.setOnMouseClicked(event->{
            this.dashboardToolbarGridpane.setVisible(false);
            this.paymentGridpane.setVisible(true);
        });
    }
    private void setExit(){
        this.exit.setOnMouseClicked(event->{
            this.dashboardToolbarGridpane.setVisible(true);
            this.paymentGridpane.setVisible(false);
        });
    }
    private void setShopLayout(){
        newShop.setOnAction(event->{
            BorderPane dashboardLayout = (BorderPane) dashboardToolbar.getParent();
            dashboardLayout.setBottom(getShopLayout());
        });
    }
    private void setAccountLayout(){
        newAccount.setOnAction(event->{
            BorderPane dashboardLayout = (BorderPane) dashboardToolbar.getParent();
            dashboardLayout.setBottom(getAccountLayout());
        });
    }
    private void setPaymentLayout(){
        newPayment.setOnAction(event->{
            BorderPane dashboardLayout = (BorderPane) dashboardToolbar.getParent();
            dashboardLayout.setBottom(getPaymentLayout());
        });
    }
    private void putIcons(){
        this.newAccountIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/NewAccountIcon.png"))));
        this.newShopIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/NewShopIcon.png"))));
        this.MoneyIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/MoneyIcon.png"))));
    }
    private BorderPane getShopLayout(){
        FXMLLoader shopLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/shop/shopLayout.fxml"));
        BorderPane shoplayout;
        try {
            shoplayout = shopLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return shoplayout;
    }
    private BorderPane getAccountLayout(){
        FXMLLoader accountLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/account/account2Layout.fxml"));
        BorderPane accountLayout;
        try {
            accountLayout = accountLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accountLayout;
    }
    private BorderPane getPaymentLayout(){
        FXMLLoader paymentModeLayoutLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/admin/PaymentLayout.fxml"));
        BorderPane paymentModeLayout;
        try {
            paymentModeLayout = paymentModeLayoutLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return paymentModeLayout;
    }

    private GridPane getLogin(){
        FXMLLoader fxmlLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/login/login.fxml"));
        GridPane login;
        try {
            login = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return login;
    }
    private void setLogout(){
        SessionManager.clearSession();
        BorderPane salesManagementBorderpane = (BorderPane) dashboardToolbar.getParent().getParent().getParent().getParent();
        salesManagementBorderpane.setCenter(this.getLogin());
    }

}
