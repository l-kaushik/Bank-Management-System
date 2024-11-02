package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.AlertBoxCreator;
import com.github.lkaushik.bankmanagement.Models.CurrencyFormatter;
import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Models.Transaction;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    public Button msg_btn;

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
        amount_lbl.setText(CurrencyFormatter.formattedCurrency(transaction.amountProperty().getValue()));

        String payeeAddress = Model.getInstance().getClient().payeeAddressProperty().getValue();

        if(Objects.equals(payeeAddress, receiver_lbl.getText())) {
            in_icon.setVisible(true);
            out_icon.setVisible(false);
        }
        else {
            in_icon.setVisible(false);
            out_icon.setVisible(true);
        }

        msg_btn.setOnAction(e -> showPopInfo());
    }

    private void showPopInfo() {
        String alertMessage = transaction.senderProperty().get() + " " + transaction.messageProperty().getValue();
        AlertBoxCreator.createMessageAlert(Alert.AlertType.INFORMATION, "Message", alertMessage);
    }
}
