package com.sales_management_javafx.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sales_management.entity.AccountEntity;
import org.sales_management.service.AccountService;

public class  AccountData {
    private static AccountService accountService = null;

    public AccountData() {
        accountService = new AccountService();
    }

    public static ObservableList<AccountEntity> getAccounts() {
        return FXCollections.observableArrayList(accountService.getAll());
    }
}
