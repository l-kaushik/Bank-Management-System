package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners() {
        dashboard_btn.setOnAction(event -> onDashboard());
        transaction_btn.setOnAction(event -> onTransactions());
        accounts_btn.setOnAction(event -> onAccounts());
        logout_btn.setOnAction(event -> onLogOut());
    }

    private void onTransactions() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(ClientMenuOptions.TRANSACTIONS);
    }
    private void onDashboard() {
        Model.getInstance().getViewFactory().getSelectedMenuItem().set(ClientMenuOptions.DASHBOARD);
    }
    private void onAccounts() { Model.getInstance().getViewFactory().getSelectedMenuItem().set(ClientMenuOptions.ACCOUNTS); }

    private void onLogOut() {
        Model.getInstance().clientLogOutCleanups();
        Model.getInstance().getViewFactory().simulateLogOut((Stage)logout_btn.getScene().getWindow());
    }
}
