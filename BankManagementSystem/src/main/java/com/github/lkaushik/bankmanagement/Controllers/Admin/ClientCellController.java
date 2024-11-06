package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.AlertBoxCreator;
import com.github.lkaushik.bankmanagement.Models.Client;
import com.github.lkaushik.bankmanagement.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Optional;
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
        delete_btn.setOnAction(event -> onDeleteButtonClicked());
    }

    private void updateLabels() {
        fName_lbl.setText(client.firstNameProperty().getValue());
        lName_lbl.setText(client.lastNameProperty().getValue());
        pAddress_lbl.setText(client.payeeAddressProperty().getValue());
        ch_acc_lbl.setText(client.checkingAccountProperty().getValue().accountNumberProperty().getValue());
        sv_acc_lbl.setText(client.savingAccountProperty().getValue().accountNumberProperty().getValue());
        date_lbl.setText(client.dateProperty().getValue().toString());
    }

    private void onDeleteButtonClicked() {
        Optional<ButtonType> result = AlertBoxCreator.createAlert(Alert.AlertType.CONFIRMATION, "Client removal",
                "Are you sure you want to remove " + pAddress_lbl.getText() + "?\n This can't be undone.",true);
       if(result.isPresent() && (result.get() == ButtonType.OK))
            Model.getInstance().removeClient(pAddress_lbl.getText());
    }

}
