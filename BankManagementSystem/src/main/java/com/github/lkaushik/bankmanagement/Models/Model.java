package com.github.lkaushik.bankmanagement.Models;

import com.github.lkaushik.bankmanagement.Views.AccountType;
import com.github.lkaushik.bankmanagement.Views.ViewFactory;

import java.sql.ResultSet;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private AccountType loginAccountType = AccountType.CLIENT;
    // Client Data Section
    private final Client client;
    private boolean clientLoginSuccessFlag;
    // Admin Data Section

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client();
        // Admin Data Section
    }

    // synchronized make sure only 1 thread at a time can execute this method (Singleton pattern)
    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
    public DatabaseDriver getDatabaseDriver() {return databaseDriver;}
    public AccountType getLoginAccountType() {return loginAccountType;}
    public void setLoginAccountType(AccountType type) {this.loginAccountType = type;}

    /*
    * Client Method Section
    * */
    public boolean getClientLoginSuccessFlag() {return clientLoginSuccessFlag;}
    public void setClientLoginSuccessFlag(boolean flag) { this.clientLoginSuccessFlag = flag;}
    public Client getClient() {return client;}

    public void evaluateClientCred(String pAddress, String password) {
        ResultSet resultSet = getDatabaseDriver().getClientData(pAddress, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.client.firstNameProperty().set(resultSet.getString("FirstName"));
                this.client.lastNameProperty().set(resultSet.getString("LastName"));
                this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.client.dateProperty().set(date);
                this.client.savingAccountProperty().set(getSavingsAccountsData(pAddress));
//                this.client.checkingAccountProperty().set(get);
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SavingsAccount getSavingsAccountsData(String pAddress) {
        SavingsAccount savingsAccount = null;
        ResultSet resultSet = databaseDriver.getClientSavingsAccountData(pAddress);
        try {
            if(resultSet.isBeforeFirst()) {
                String accountNumber = resultSet.getString("AccountNumber");
                double balance = resultSet.getDouble("Balance");
                double withdrawalLimit = resultSet.getDouble("WithdrawalLimit");
                savingsAccount = new SavingsAccount(pAddress, accountNumber, balance, withdrawalLimit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savingsAccount;
    }

}
