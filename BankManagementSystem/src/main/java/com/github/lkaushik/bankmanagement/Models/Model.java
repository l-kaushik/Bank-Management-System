package com.github.lkaushik.bankmanagement.Models;

import com.github.lkaushik.bankmanagement.Views.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
    private boolean adminLoginSuccessFlag;

    // Listener
    private final List<TransactionListener> transactionListeners = new ArrayList<>();
    private final List<ClientCreationListener> clientCreationListeners = new ArrayList<>();
    private final List<ClientDeletionListener> clientDeletionListeners = new ArrayList<>();

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        // Client Data Section
        this.clientLoginSuccessFlag = false;
        this.client = new Client();
        this.clientTransactionData = FXCollections.observableArrayList();
        // Admin Data Section
        this.adminLoginSuccessFlag = false;
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
                extractClientData(resultSet);
                getAccountsAndTransactionData(pAddress);
                this.clientLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initiateSendMoney(String receiver, String amount, String message) {
        double parsedAmount = validateCreds(receiver, amount, message);
        if(parsedAmount >= 1)
            Model.getInstance().transferFunds(receiver, parsedAmount, message);
    }

    // self funds transferring
    public void moveToChecking(String amount) {
        selfTransferChecks(amount, ClientAccountType.CHECKING);
    }

    public void moveToSavings(String amount) {
        selfTransferChecks(amount, ClientAccountType.SAVINGS);
    }

    private void selfTransferChecks(String amount, ClientAccountType receiverAccountType) {
        if(!isAccountExists(receiverAccountType)) {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Account not found", "You do not have a " + receiverAccountType.toString().toLowerCase() + " account.");
            return;
        }

        double parsedAmount = validateAmount(amount);
        if(parsedAmount < 1) {
            return;
        }

        performSelfTransfer(amount, parsedAmount, receiverAccountType);
    }

    private void performSelfTransfer(String amount, double parsedAmount, ClientAccountType receiverAccountType) {
        double checkingBalance = client.checkingAccountProperty().getValue().balanceProperty().getValue();
        double savingBalance = client.savingAccountProperty().getValue().balanceProperty().getValue();
        String owner = client.payeeAddressProperty().getValue();

        if(receiverAccountType == ClientAccountType.CHECKING && parsedAmount <= savingBalance) {
            databaseDriver.performSelfTransfer(owner, checkingBalance + parsedAmount,savingBalance - parsedAmount );
        }
        else if(receiverAccountType == ClientAccountType.SAVINGS &&  parsedAmount <= checkingBalance) {
            databaseDriver.performSelfTransfer(owner, checkingBalance - parsedAmount, savingBalance + parsedAmount);
        }
        else {
            String account = (receiverAccountType == ClientAccountType.SAVINGS) ? "Checking" : "Savings";
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Insufficient amount", "Your " + account + " account balance is insufficient for a transfer of " + parsedAmount + ".");
            return;
        }
        updateClientDataAndNotify(owner);
        AlertBoxCreator.createAlert(Alert.AlertType.INFORMATION, "Transaction Completed", CurrencyFormatter.formattedCurrency(parsedAmount) + " has transferred to your " + receiverAccountType.toString().toLowerCase() + " account.");
    }

    private boolean isAccountExists(ClientAccountType accountType) {
        String nullAccountNumber = "0000 0000 0000 0000";
        if (Objects.requireNonNull(accountType) == ClientAccountType.CHECKING) {
            return !Objects.equals(client.checkingAccountProperty().getValue().accountNumberProperty().getValue(), nullAccountNumber);
        }
        return !Objects.equals(client.savingAccountProperty().getValue().accountNumberProperty().getValue(), nullAccountNumber);
    }

    private void extractClientData(ResultSet resultSet) throws SQLException {
        this.client.firstNameProperty().set(resultSet.getString("FirstName"));
        this.client.lastNameProperty().set(resultSet.getString("LastName"));
        this.client.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));
        String[] dateParts = resultSet.getString("Date").split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
        this.client.dateProperty().set(date);
    }

    private void getAccountsAndTransactionData(String pAddress) {
        this.client.savingAccountProperty().set(getAccountsData(pAddress, ClientAccountType.SAVINGS));
        this.client.checkingAccountProperty().set(getAccountsData(pAddress, ClientAccountType.CHECKING));
        this.clientTransactionData.clear();
        this.clientTransactionData.addAll(getTransactionData(pAddress));
    }

    private double validateCreds(String receiver, String amount, String message) {
        if(receiver.isEmpty() || amount.isEmpty()) {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Empty field detected", "Payee address or amount field cannot be empty.");
        }
        else if(Objects.equals(receiver, Model.getInstance().getClient().payeeAddressProperty().getValue())) {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Invalid payee address", "Cannot send money to yourself!");
        }
        else if(!databaseDriver.isClient(receiver)){
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Invalid payee address", "Client not found, \ncheck the payee address.");
        }
        else {
            return validateAmount(amount);
        }

        return -1;
    }

    private double validateAmount(String amount) {
        try {
            double parsedAmount = Double.parseDouble(amount);
            if(parsedAmount <= 0 ) {
                AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Invalid amount ", "Amount must be greater than zero");
            }
            else  if(parsedAmount > 0 && parsedAmount < 1){
                AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Transaction failed", "Amount is too small to be send");
            }
            else {
                return parsedAmount;
            }
        } catch (NumberFormatException e) {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Invalid amount", "The amount is not valid.");
        }
        return -1;
    }

    private void transferFunds(String receiver, double amount, String message) {
        String sender = client.payeeAddressProperty().getValue();
        double senderCheckingBalance = client.checkingAccountProperty().getValue().balanceProperty().getValue();
        double senderSavingBalance = client.savingAccountProperty().getValue().balanceProperty().getValue();
        double receiverCheckingBalance = getAccountsData(receiver, ClientAccountType.CHECKING).balanceProperty().getValue();

        if (amount <= senderCheckingBalance) {
            databaseDriver.transferFundsFromChecking(sender, receiver, amount, message, senderCheckingBalance, receiverCheckingBalance);
        } else if (amount <= (senderCheckingBalance + senderSavingBalance)) {
            databaseDriver.transferFundsFromSavings(sender, receiver, amount, message, senderCheckingBalance, senderSavingBalance, receiverCheckingBalance);
        } else {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Insufficient amount", "Insufficient balance in accounts");
            return;
        }
        updateClientDataAndNotify(sender);
        AlertBoxCreator.createAlert(Alert.AlertType.INFORMATION, "Transaction Completed", CurrencyFormatter.formattedCurrency(amount) + " has transferred to " + receiver);
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

    public void clientLogOutCleanups() {
        viewFactory.resetClientSelectedMenuItem();
        client.cleanup();
        clientLoginSuccessFlag = false;
        clientTransactionData.clear();
        transactionListeners.clear();
    }

    // Transaction Listener's Methods

    public void addTransactionListener(TransactionListener listener) {
        transactionListeners.add(listener);
    }

    public void removeTransactionListener(TransactionListener listener) {
        transactionListeners.remove(listener);
    }

    private void notifyTransactionListeners() {
        for(TransactionListener listener : new ArrayList<>(transactionListeners)) {
            listener.onTransactionCompleted();
        }
    }

    private void updateClientDataAndNotify(String pAddress) {
        getAccountsAndTransactionData(pAddress);
        notifyTransactionListeners();
    }

    // Client Creation Listener's Methods

    public void addClientCreationListener(ClientCreationListener listener) {
        clientCreationListeners.add(listener);
    }

    public void removeClientCreationListener(ClientCreationListener listener) {
        clientCreationListeners.remove(listener);
    }

    private void notifyClientCreationListeners() {
        for(ClientCreationListener listener : new ArrayList<>(clientCreationListeners)) {
            listener.onClientCreationCompleted();
        }
    }

    // Client Deletion Listener's Methods

    public void addClientDeletionListener(ClientDeletionListener listener) {
        clientDeletionListeners.add(listener);
    }

    private void notifyClientDeletionListeners() {
        for(ClientDeletionListener listener : new ArrayList<>(clientDeletionListeners)) {
            listener.onClientDeletionCompleted();
        }
    }

    /*
    * Admin methods section
    * */

    public boolean getAdminLoginSuccessFlag() {return adminLoginSuccessFlag;}
    public void setAdminLoginSuccessFlag(boolean flag) { this.adminLoginSuccessFlag = false;}

    public void evaluateAdminCreds(String username, String password) {
        ResultSet resultSet = getDatabaseDriver().getAdminData(username, password);
        try {
            if(resultSet.isBeforeFirst()) {
                this.adminLoginSuccessFlag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            databaseDriver.closeResources(resultSet);
        }
    }

    public void initializeClientCreation(String firstName, String lastName, String address, String password,
                                         boolean hasCheckingAccount, double checkingBalance, boolean hasSavingsAccount,
                                         double savingsBalance) {

        // accounts generation
        SavingsAccount savingsAccount = null;
        CheckingAccount checkingAccount = null;
        int transactionLimit = 10;
        double withdrawalLimit = 2000;
        String checkingAccountNumber = AccountNumberGenerator.generateAccountNumber();
        String savingsAccountNumber = AccountNumberGenerator.generateUniqueSavingsAccountNumber(checkingAccountNumber);

        checkingAccount = (hasCheckingAccount) ?
                new CheckingAccount(address, checkingAccountNumber, checkingBalance, transactionLimit)
                : new CheckingAccount(address, "0000 0000 0000 0000", 0.0, 0);

        savingsAccount = (hasSavingsAccount) ?
                new SavingsAccount(address, savingsAccountNumber, savingsBalance, withdrawalLimit)
                : new SavingsAccount(address, "0000 0000 0000 0000", 0.0, 0.0);

        // client generation
        Client client = new Client(firstName, lastName, address, checkingAccount, savingsAccount, LocalDate.now());

        // hash password

        // insert into database
        if(databaseDriver.pushClientData(client, password)) {
            notifyClientCreationListeners();
            AlertBoxCreator.createAlert(Alert.AlertType.INFORMATION, "Account Creation", "Your account has been created.\n Payee Address: " + address);
        }
    }

    public String generatePayeeAddress(String firstName, String secondName) {
        StringBuilder pAddress = new StringBuilder();

        pAddress.append("@");
        pAddress.append(firstName.substring(0, 1).toLowerCase());
        pAddress.append(secondName.substring(0, 1).toUpperCase());
        pAddress.append(secondName.substring(1).toLowerCase());
        Random random = new Random();

        while (databaseDriver.isClient(pAddress.toString())) {
            pAddress.append(random.nextInt(10));
        }

        return pAddress.toString();
    }

    public List<Client> getExistingClients() {
        List<Client> clients = new ArrayList<>();
        try(ResultSet resultSet = databaseDriver.fetchAllClientsData()) {
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String payeeAddress = resultSet.getString("PayeeAddress");
                String password = resultSet.getString("Password");
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                CheckingAccount checkingAccount =  (CheckingAccount) getAccountsData(payeeAddress, ClientAccountType.CHECKING);
                SavingsAccount savingsAccount = (SavingsAccount) getAccountsData(payeeAddress, ClientAccountType.SAVINGS);
                clients.add(new Client(firstName, lastName, payeeAddress, checkingAccount, savingsAccount, date));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return clients;
    }

    public void adminLogOutCleanups() {
        viewFactory.resetAdminSelectedMenuItem();
        adminLoginSuccessFlag = false;
        clientCreationListeners.clear();
    }

    public void removeClient(String address) {
        if(databaseDriver.deleteClientData(address)) {
            notifyClientDeletionListeners();
        }
    }
}
