package com.sales_management_javafx.actions;

import org.sales_management.entity.PaymentEntity;
import org.sales_management.entity.SaleArticleEntity;
import org.sales_management.entity.SaleEntity;
import org.sales_management.service.SaleService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment {
    public static Double getPaymentByDay(LocalDate localDate){
        double money = 0.0;
        for (SaleEntity sale : new SaleService().getAcceptedSalesByDate(localDate)){
            for (PaymentEntity payment : sale.getPayments()){
                money += payment.getPay();
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
