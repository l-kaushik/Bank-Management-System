package com.github.lkaushik.bankmanagement.Models;

import com.github.lkaushik.bankmanagement.Views.AccountType;
import com.github.lkaushik.bankmanagement.Views.ClientAccountType;
import com.github.lkaushik.bankmanagement.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private final ObservableList<Transaction> clientTransactionData;
    // Admin Data Section

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client();
        this.clientTransactionData = FXCollections.observableArrayList();
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
    public ObservableList<Transaction> getClientTransactionData() {return clientTransactionData;}

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
                this.client.savingAccountProperty().set(getAccountsData(pAddress, ClientAccountType.SAVINGS));
                this.client.checkingAccountProperty().set(getAccountsData(pAddress, ClientAccountType.CHECKING));
                this.clientTransactionData.addAll(getTransactionData(pAddress));
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Account getAccountsData(String pAddress, ClientAccountType accountType) {
        Account account = null;
        ResultSet resultSet;
        try {
            resultSet = (accountType == ClientAccountType.SAVINGS) ?
                    databaseDriver.getClientSavingsAccountData(pAddress) :
                    databaseDriver.getClientCheckingAccountData(pAddress);

            if(resultSet.isBeforeFirst()) {
                String accountNumber = resultSet.getString("AccountNumber");
                double balance = resultSet.getDouble("Balance");

                if (accountType == ClientAccountType.SAVINGS) {
                    double withdrawalLimit = resultSet.getDouble("WithdrawalLimit");
                    account = new SavingsAccount(pAddress, accountNumber, balance, withdrawalLimit);
                } else if (accountType == ClientAccountType.CHECKING) {
                    int transactionLimit = resultSet.getInt("TransactionLimit");
                    account = new CheckingAccount(pAddress, accountNumber, balance, transactionLimit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    private ObservableList<Transaction> getTransactionData(String pAddress) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        ResultSet resultSet = databaseDriver.getClientTransactionData(pAddress);
        try{
            while (resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                double amount = resultSet.getDouble("Amount");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                String message = resultSet.getString("Message");

                transactions.add(new Transaction(sender, receiver, amount, date, message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }



}
