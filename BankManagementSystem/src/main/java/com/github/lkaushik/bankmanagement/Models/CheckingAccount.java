package com.github.lkaushik.bankmanagement.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account{
    // The number of transactions a client is allowed to do per day.
    private final IntegerProperty transactionLimit;

    public CheckingAccount() {
        this("", "0000 0000 0000 0000", 0.0, 0);
    }

    public CheckingAccount(String owner, String accountNumber, double balance, int tLimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "Transaction Limit", tLimit);
    }

    public IntegerProperty transactionLimitProperty() {return transactionLimit;}
}