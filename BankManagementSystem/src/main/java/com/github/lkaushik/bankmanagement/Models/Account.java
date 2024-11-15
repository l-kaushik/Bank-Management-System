package com.github.lkaushik.bankmanagement.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {
    private final StringProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;
    public static String nullAccountNumber = "0000 0000 0000 0000";

    public Account(String owner, String accountNumber, double balance) {
        this.owner = new SimpleStringProperty(this, "Owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "Account Number", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "Balance", balance);
    }

    // getters
    public StringProperty ownerProperty() {return owner;}
    public StringProperty accountNumberProperty() {return accountNumber;}
    public DoubleProperty balanceProperty() {return balance;}
}
