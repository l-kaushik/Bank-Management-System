package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.Client;
import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Views.ClientCellFactory;
import com.github.lkaushik.bankmanagement.Views.ClientDeletionListener;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientsController implements Initializable, ClientDeletionListener {
    public ListView<Client> clients_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().addClientDeletionListener(this);
        updateList();
    }

    private void updateList() {
        clients_listview.getItems().removeAll();
        clients_listview.setCellFactory(param -> new ClientCellFactory());
        List<Client> clients = Model.getInstance().getExistingClients().reversed();
        clients_listview.setItems(FXCollections.observableArrayList(clients));
    }

    @Override
    public void onClientDeletionCompleted() {
        updateList();
    }
}
