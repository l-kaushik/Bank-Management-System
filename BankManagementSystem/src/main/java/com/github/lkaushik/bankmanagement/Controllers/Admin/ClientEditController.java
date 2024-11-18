package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.*;
import com.github.lkaushik.bankmanagement.Views.AccountCreationListener;
import com.github.lkaushik.bankmanagement.Views.PasswordStatus;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientEditController implements Initializable, AccountCreationListener {
    public TextField pAddress_fld;
    public Button search_btn;
    public TextField fName_fld;
    public TextField lName_fld;
    public Label ch_acc_num;
    public Label ch_amt;
    public Button ch_add_btn;
    public Label sv_acc_num;
    public Label sv_amt;
    public Button sv_add_btn;
    public PasswordField password_fld1;
    public PasswordField password_fld2;
    public Label error_lbl;
    public Button pass_submit_btn;

    Client client = null;
    BooleanProperty isClientValid = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButtonsAction();
        addListeners();
        setFieldAndButtonDisabled(true);
        fName_fld.setDisable(true);
        lName_fld.setDisable(true);

        Model.getInstance().addAccountCreationListener(this);
    }

    private void addListeners() {
        addChangeListenerToAccNum(ch_acc_num, ch_add_btn);
        addChangeListenerToAccNum(sv_acc_num, sv_add_btn);

        isClientValid.addListener((ObservableValue, oldValue, newValue) -> {
            setPasswordsDisabled(!newValue);
        });
    }

    private void addChangeListenerToAccNum(Label label, Button button) {
        String nullAmount = "0000 0000 0000 0000";
        label.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            button.setDisable(!Objects.equals(label.getText(), nullAmount));
        });
    }

    private void addButtonsAction() {
        search_btn.setOnAction(_ -> onSearchButton());
        ch_add_btn.setOnAction(_ -> onCheckingButton());
        sv_add_btn.setOnAction(_ -> onSavingsButton());
        pass_submit_btn.setOnAction(_ -> onSubmitButton());
    }

    private void onSavingsButton() {
        if(showAccountConfirmationDialog()) {
            Model.getInstance().updateSavingsAccount(client.payeeAddressProperty().getValue());
        }
    }

    private void onCheckingButton() {
        if(showAccountConfirmationDialog()) {
            Model.getInstance().updateCheckingAccount(client.payeeAddressProperty().getValue());
        }
    }

    private void onSubmitButton() {
        if(!Objects.equals(password_fld1.getText(), password_fld2.getText())) {
            error_lbl.setText("Passwords are not matching!");
            return;
        }

        PasswordStatus status = PasswordManager.validate(password_fld1.getText());
        if(status == PasswordStatus.VALID) {
            Model.getInstance().updatePassword(client.payeeAddressProperty().getValue(), password_fld1.getText());
            error_lbl.setText("");
        }
        else if(status == PasswordStatus.TOO_SHORT){
            error_lbl.setText("Your password must be at least 8 characters long");
        }
        else {
            error_lbl.setText("Your password should only contains a-z, A-Z, 0-9 and !@#$%^&*()");
        }
    }

    private void onSearchButton() {
        clientSearchAndDataInsertion();
    }

    private void clientSearchAndDataInsertion() {
        List<Client> clients = Model.getInstance().getExistingClient(pAddress_fld.getText());
        if(clients.isEmpty()) {
            client = new Client();
            client.checkingAccountProperty().set(new CheckingAccount());
            client.savingAccountProperty().set(new SavingsAccount());
            error_lbl.setText("Client not found!");
            setFieldAndButtonDisabled(true);
            isClientValid.setValue(false);
        }
        else {
            client = clients.getFirst();
            error_lbl.setText("");
            setFieldAndButtonDisabled(false);
            isClientValid.setValue(true);
        }
        updateFieldsAndLabels();
    }

    private void updateFieldsAndLabels() {
        fName_fld.setText(client.firstNameProperty().getValue());
        lName_fld.setText(client.lastNameProperty().getValue());
        ch_acc_num.setText(client.checkingAccountProperty().getValue().accountNumberProperty().getValue());
        ch_amt.setText(client.checkingAccountProperty().getValue().balanceProperty().getValue().toString());
        sv_acc_num.setText(client.savingAccountProperty().getValue().accountNumberProperty().getValue());
        sv_amt.setText(client.savingAccountProperty().getValue().balanceProperty().getValue().toString());
    }

    private void setFieldAndButtonDisabled(boolean state) {
        ch_add_btn.setDisable(state);
        sv_add_btn.setDisable(state);
        setPasswordsDisabled(state);
    }

    private void setPasswordsDisabled(boolean state) {
        password_fld1.setDisable(state);
        password_fld2.setDisable(state);
        pass_submit_btn.setDisable(state);
    }

    private boolean showAccountConfirmationDialog() {
        Optional<ButtonType> result = AlertBoxCreator.createAlert(Alert.AlertType.CONFIRMATION, "Account creation confirmation", "Account creation requires minimum deposit of " + CurrencyFormatter.formattedCurrencyIndian(100) + ".",true);
        return (result.isPresent() && (result.get() == ButtonType.OK));
    }

    @Override
    public void onAccountCreationCompleted() {
        cleanup();
        clientSearchAndDataInsertion();
    }

    private void cleanup() {
        client = null;
        isClientValid.setValue(false);
        ch_acc_num.setText("");
        sv_acc_num.setText("");
        password_fld1.setText("");
        password_fld2.setText("");
    }
}
