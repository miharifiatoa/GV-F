package com.sales_management_javafx.controller.dashboard;

import com.sales_management_javafx.SalesApplication;
import com.sales_management_javafx.actions.Payment;
import com.sales_management_javafx.classes.DecimalFormat;
import com.sales_management_javafx.classes.MenuIcon;
import com.sales_management_javafx.classes.Printer;
import com.sales_management_javafx.composent.MenuGridPane;
import com.sales_management_javafx.composent.admin.AdminArrivalGridPane;
import com.sales_management_javafx.composent.admin.AdminPaymentGridPane;
import com.sales_management_javafx.composent.admin.AdminSaleGridPane;
import com.sales_management_javafx.composent.admin.AdminShareGridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.sales_management.entity.ArrivalEntity;
import org.sales_management.entity.ShareEntity;
import org.sales_management.service.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DashboardLayoutController implements Initializable {
    @FXML private BorderPane dashboardLayout;
    @FXML private BorderPane dashboardLayoutBorderpane;
    @FXML private ScrollPane dashboardLayoutScrollpane;
    @FXML private DatePicker datePicker;
    @FXML private ImageView printIcon;
    @FXML private Button print;
    private final MenuGridPane menuGridPane;
    private final MenuIcon menuIcon;
    private final ArrivalService arrivalService;
    private final SaleService saleService;
    private final ShareService shareService;
    private final PaymentService paymentService;

    public DashboardLayoutController() {
        this.paymentService = new PaymentService();
        this.saleService = new SaleService();
        this.shareService = new ShareService();
        this.arrivalService = new ArrivalService();
        this.menuIcon = new MenuIcon();
        this.menuGridPane = new MenuGridPane();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dashboardLayoutBorderpane.setTop(this.menuGridPane.getGridPane(menuIcon.getMenuIcons(),7));
        dashboardLayoutBorderpane.setBottom(getDashboardToolbar());
        datePicker.setValue(LocalDate.now());
        this.setDatePicker();
        this.putIcons();
        this.setPrint();
    }

    private StackPane getDashboardToolbar(){
        FXMLLoader dashboardToolbarLoader = new FXMLLoader(SalesApplication.class.getResource("fxml/dashboard/dashboardToolbar.fxml"));
        StackPane dashboardToolbar;
        try {
            dashboardToolbar = dashboardToolbarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dashboardToolbar;
    }
    private Collection<LocalDate> getArrivalsDate(){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ArrivalEntity arrival : arrivalService.getAll()){
            localDates.add(arrival.getArrivalDate().toLocalDate());
        }
        return localDates;
    }
    private Collection<LocalDate> getSharesDate(){
        Collection<LocalDate> localDates = new HashSet<>();
        for (ShareEntity share : shareService.getAll()){
            localDates.add(share.getShareDate().toLocalDate());
        }
        return localDates;
    }
    private void setDatePicker(){
        datePicker.setOnAction(event->{
            LocalDate date = datePicker.getValue();
            Node node = dashboardLayoutScrollpane.getContent();
            if (node != null){
                if(Objects.equals(node.getId(), "arrival")){
                    GridPane adminArrivalGridPane = new AdminArrivalGridPane().getGridPane(arrivalService.getAllArrivalsByDate(date),4);
                    dashboardLayoutScrollpane.setContent(adminArrivalGridPane);
                }
                else if(Objects.equals(node.getId(), "sale")){
                    GridPane adminSaleGridPane = new AdminSaleGridPane().getGridPane(saleService.getAllSalesByDate(date),4);
                    dashboardLayoutScrollpane.setContent(adminSaleGridPane);
                }
                else if(Objects.equals(node.getId(), "share")){
                    GridPane adminShareGridPane = new AdminShareGridPane().getGridPane(shareService.getAllSharesByDate(date),4);
                    dashboardLayoutScrollpane.setContent(adminShareGridPane);
                }
            }
            initialize(date);
        });
        datePicker.setDayCellFactory(new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        Node node = dashboardLayoutScrollpane.getContent();
                        if (node != null){
                            if(Objects.equals(node.getId(), "arrival")){
                                if (getArrivalsDate().contains(item)) {
                                    setStyle("-fx-background-color: lightgreen;");
                                    System.out.println("arrival");
                                }
                            }
                            else if(Objects.equals(node.getId(), "share")){
                                if (getSharesDate().contains(item)) {
                                    setStyle("-fx-background-color: #ff2ceabc;");
                                    System.out.println("arrival");
                                }
                            }
                        }
                    }

                };
            }
        });
    }
    private void initialize(LocalDate localDate){
        Label payment = (Label) dashboardLayout.lookup("#payment");
        payment.setText("Versement : " + DecimalFormat.format(Payment.getPaymentByDay(localDate)) + "Ar");
        ScrollPane paymentScrollPane = (ScrollPane) dashboardLayout.lookup("#paymentScrollPane");
        GridPane adminPaymentGridPane = new AdminPaymentGridPane().getGridPane(localDate);
        paymentScrollPane.setContent(adminPaymentGridPane);
    }
    private void putIcons(){
        this.printIcon.setImage(new Image(String.valueOf(SalesApplication.class.getResource("icon/PrintIcon.png"))));
    }
    private void setPrint(){
        print.setOnAction(event->{
            Printer.print(dashboardLayoutScrollpane);
        });
    }
}
