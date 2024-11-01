package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.CheckingAccount;
import com.github.lkaushik.bankmanagement.Models.CurrencyFormatter;
import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Models.SavingsAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public TextField amount_to_sv;
    public Button trans_to_sv_btn;
    public TextField amount_to_ch;
    public Button trans_to_ch_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSavingAccountDetails();
        addCheckingAccountDetails();
    }

    private void addCheckingAccountDetails() {
        CheckingAccount checkingAccount = (CheckingAccount) Model.getInstance().getClient().checkingAccountProperty().getValue();

        ch_acc_num.setText(checkingAccount.accountNumberProperty().getValue());
        ch_acc_bal.setText(CurrencyFormatter.formattedCurrency(checkingAccount.balanceProperty().getValue()));
        ch_acc_date.setText(Model.getInstance().getClient().dateProperty().getValue().toString());
        transaction_limit.setText(checkingAccount.transactionLimitProperty().getValue().toString());
    }

    private void addSavingAccountDetails() {
        SavingsAccount savingsAccount = (SavingsAccount) Model.getInstance().getClient().savingAccountProperty().getValue();

        sv_acc_num.setText(savingsAccount.accountNumberProperty().getValue());
        sv_acc_bal.setText(CurrencyFormatter.formattedCurrency(savingsAccount.balanceProperty().getValue()));
        sv_acc_date.setText(Model.getInstance().getClient().dateProperty().getValue().toString());
        withdrawal_limit.setText(CurrencyFormatter.formattedCurrency(savingsAccount.withdrawalLimitProperty().getValue()));
    }
}
