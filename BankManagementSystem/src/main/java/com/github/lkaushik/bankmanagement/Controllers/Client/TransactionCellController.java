package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {
    public FontAwesomeIconView in_icon;
    public FontAwesomeIconView out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_lbl;

    private final Transaction transaction;

    public TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateLabels();
    }

    private void updateLabels() {
        trans_date_lbl.setText(transaction.dateProperty().getValue().toString());
        sender_lbl.setText(transaction.senderProperty().getValue());
        receiver_lbl.setText(transaction.receiverProperty().getValue());
        amount_lbl.setText(transaction.amountProperty().getValue().toString());

        String payeeAddress = Model.getInstance().getClient().payeeAddressProperty().getValue();

        if(Objects.equals(payeeAddress, receiver_lbl.getText())) {
            in_icon.setVisible(true);
            out_icon.setVisible(false);
        }
        else {
            in_icon.setVisible(false);
            out_icon.setVisible(true);
        }
    }
}
