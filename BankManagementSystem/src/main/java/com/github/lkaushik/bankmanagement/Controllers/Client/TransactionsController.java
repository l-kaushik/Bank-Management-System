package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Models.Transaction;
import com.github.lkaushik.bankmanagement.Views.TransactionCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public ListView<Transaction> transactions_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transactions_listview.setCellFactory(param -> new TransactionCellFactory());
        ObservableList<Transaction> transactions = Model.getInstance().getClientTransactionData();
        transactions_listview.setItems(transactions);
    }
}
