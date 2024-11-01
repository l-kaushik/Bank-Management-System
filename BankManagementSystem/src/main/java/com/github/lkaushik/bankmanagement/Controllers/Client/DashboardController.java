package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.CheckingAccount;
import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Models.SavingsAccount;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label savings_bal;
    public Label savings_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    private SavingsAccount savingsAccount;
    private CheckingAccount checkingAccount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        savingsAccount = (SavingsAccount) Model.getInstance().getClient().savingAccountProperty().getValue();
        checkingAccount = (CheckingAccount) Model.getInstance().getClient().checkingAccountProperty().getValue();

        updateStaticData();
        updateDynamicData();
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
        NumberFormat indiaCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.of("en", "IN"));

        double balance = savingsAccount.balanceProperty().getValue();
        String formattedBalance = indiaCurrencyFormat.format(balance).replace("₹", "₹ ");
        savings_bal.setText(formattedBalance);
        balance = checkingAccount.balanceProperty().getValue();
        formattedBalance = indiaCurrencyFormat.format(balance).replace("₹", "₹ ");
        checking_bal.setText(formattedBalance);

        // income expenses
        // transactions
    }

    private String getCurrentDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return  today.format(formatter);
    }
}
