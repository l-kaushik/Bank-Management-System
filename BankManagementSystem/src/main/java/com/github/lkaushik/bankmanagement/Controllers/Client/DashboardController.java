package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.*;
import com.github.lkaushik.bankmanagement.Views.TransactionCellFactory;
import com.github.lkaushik.bankmanagement.Views.TransactionListener;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable, TransactionListener {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView<Transaction> transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    private SavingsAccount savingsAccount;
    private CheckingAccount checkingAccount;
    private List<Transaction> transactionsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // implement withdrawal limit / transaction limit check
        updateAccountsAndTransactionData();

        updateStaticData();
        updateDynamicData();

        Model.getInstance().addTransactionListener(this);
        send_money_btn.setOnAction(event -> sendMoney());
    }

    private void updateStaticData() {
        // Updates the unchangeable data.
        user_name.setText("Hi, " + Model.getInstance().getClient().firstNameProperty().getValue());
        login_date.setText(getCurrentDate());
        String[] savingsAccountNumberParts = savingsAccount.accountNumberProperty().getValue().split(" ");
        savings_acc_num.setText(savingsAccountNumberParts[savingsAccountNumberParts.length - 1]);
        String[] checkingAccountNumberParts = checkingAccount.accountNumberProperty().getValue().split(" ");
        checking_acc_num.setText(checkingAccountNumberParts[checkingAccountNumberParts.length - 1]);
    }

    private void updateDynamicData() {
        // Updates the changeable data.
        savings_bal.setText(CurrencyFormatter.formattedCurrencyIndian(savingsAccount.balanceProperty().getValue()));
        checking_bal.setText(CurrencyFormatter.formattedCurrencyIndian(checkingAccount.balanceProperty().getValue()));

        // income expenses
        income_lbl.setText(CurrencyFormatter.formattedCurrencyIndian(getIncome()));
        expense_lbl.setText(CurrencyFormatter.formattedCurrencyIndian(getExpenses()));

        // transactions
        transaction_listview.setCellFactory(param -> new TransactionCellFactory());
        transaction_listview.setItems(FXCollections.observableArrayList(transactionsList));
    }

    private String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return  today.format(formatter);
    }

    private double getIncome() {
        return Model.getInstance().getClientTransactionData().stream()
                .filter(transaction -> transaction.dateProperty().getValue().getMonth() == LocalDate.now().getMonth())
                .filter(transaction -> Objects.equals(transaction.receiverProperty().getValue(), Model.getInstance().getClient().payeeAddressProperty().getValue()))
                .mapToDouble(transaction -> transaction.amountProperty().getValue())
                .sum();
    }

    private double getExpenses() {
        return Model.getInstance().getClientTransactionData().stream()
                .filter(transaction -> transaction.dateProperty().getValue().getMonth() == LocalDate.now().getMonth())
                .filter(transaction -> Objects.equals(transaction.senderProperty().getValue(), Model.getInstance().getClient().payeeAddressProperty().getValue()))
                .mapToDouble(transaction -> transaction.amountProperty().getValue())
                .sum();
    }

    private void sendMoney() {
        Model.getInstance().initiateSendMoney(payee_fld.getText(), amount_fld.getText(), message_fld.getText());
    }

    private void updateAccountsAndTransactionData() {
        savingsAccount = (SavingsAccount) Model.getInstance().getClient().savingAccountProperty().getValue();
        checkingAccount = (CheckingAccount) Model.getInstance().getClient().checkingAccountProperty().getValue();
        transactionsList = Model.getInstance().getClientTransactionData().reversed().stream().limit(4).toList();
    }

    private void performCleanup() {
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }

    @Override
    public void onTransactionCompleted() {
        updateAccountsAndTransactionData();
        updateDynamicData();
        performCleanup();
    }
}
