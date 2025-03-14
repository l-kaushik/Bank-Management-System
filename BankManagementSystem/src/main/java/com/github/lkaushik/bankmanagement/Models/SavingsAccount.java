package com.github.lkaushik.bankmanagement.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account{
    // The withdrawal limit from the savings
    private final DoubleProperty withdrawalLimit;

    public SavingsAccount() {
        this("", "0000 0000 0000 0000", 0.0, 0.0);
    }

    public SavingsAccount(String owner, String accountNumber, double balance, double withdrawalLimit) {
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "Withdrawal Limit", withdrawalLimit);
    }

    public DoubleProperty withdrawalLimitProperty()  {return withdrawalLimit;}
}
