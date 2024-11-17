package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.Client;
import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Views.ClientCellFactory;
import com.github.lkaushik.bankmanagement.Views.ClientDeletionListener;
import com.github.lkaushik.bankmanagement.Views.TransactionListener;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class DepositController implements Initializable, ClientDeletionListener, TransactionListener {
    public TextField pAddress_fld;
    public Button search_btn;
    public ListView<Client> results_listview;
    public TextField amount_fld;
    public Button deposit_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().addClientDeletionListener(this);
        Model.getInstance().addTransactionListener(this);

        results_listview.getItems().addListener((ListChangeListener<Client>) change -> {
            boolean isEmpty = results_listview.getItems().isEmpty();
            amount_fld.setDisable(isEmpty);
            deposit_btn.setDisable(isEmpty);
        });
        amount_fld.setDisable(true);
        deposit_btn.setDisable(true);
        search_btn.setOnAction(_ -> onSearchClicked());
        deposit_btn.setOnAction(_ -> onDepositClicked());
    }

    private void updateList() {
        results_listview.getItems().clear();
        results_listview.setCellFactory(param -> new ClientCellFactory());
        List<Client> client = Model.getInstance().getExistingClient(pAddress_fld.getText());
        results_listview.getItems().addAll(client);
    }

    private void onSearchClicked() {
        if(pAddress_fld.getText().isEmpty()) {
            results_listview.getItems().clear();
            return;
        }
        updateList();
    }

    private void onDepositClicked() {
        Model.getInstance().sendMoneyToClient(results_listview.getItems().getFirst(), amount_fld.getText());
    }

    @Override
    public void onClientDeletionCompleted() {
        results_listview.getItems().clear();
        pAddress_fld.setText("");
    }

    @Override
    public void onTransactionCompleted() {
        updateList();
        amount_fld.setText("");
    }
}
