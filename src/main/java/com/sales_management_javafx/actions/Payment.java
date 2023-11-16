package com.sales_management_javafx.actions;

import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.PaymentModeEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.PaymentModeService;
import org.sales_management.service.PaymentService;
import org.sales_management.service.SaleService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    public static Double getPaymentByDay(LocalDate localDate){
        double money = 0.0;
        for (PaymentModeEntity paymentMode : new PaymentModeService().getAll()) {
            for (Object[] result : new PaymentService().getPaymentsByModeAndDate(paymentMode.getDescription(), localDate)) {
                Double d = (Double) result[1];
                money += d;
            }
        }
        return money;
    }
    public static Double getPayed(SaleEntity sale){
        double money = 0.0;
        for (PaymentEntity payment : sale.getPayments()){
            money += payment.getPay();
        }
        return money;
    }
    public static Double getTotalToPay(SaleEntity sale){
        double money = 0.0;
        for (SaleArticleEntity saleArticle : sale.getSaleArticles()){
            money += saleArticle.getArticle().getPrice() * saleArticle.getQuantity();
        }
        return money;
    }
}
