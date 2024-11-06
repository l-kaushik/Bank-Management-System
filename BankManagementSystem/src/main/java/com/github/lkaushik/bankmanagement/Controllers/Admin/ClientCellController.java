package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientCellController implements Initializable {
    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLabels();
    }

    private void updateLabels() {
        fName_lbl.setText(client.firstNameProperty().getValue());
        lName_lbl.setText(client.lastNameProperty().getValue());
        pAddress_lbl.setText(client.payeeAddressProperty().getValue());
        ch_acc_lbl.setText(client.checkingAccountProperty().getValue().accountNumberProperty().getValue());
        sv_acc_lbl.setText(client.savingAccountProperty().getValue().accountNumberProperty().getValue());
        date_lbl.setText(client.dateProperty().getValue().toString());
    }
}
