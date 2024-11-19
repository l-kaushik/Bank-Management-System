package com.github.lkaushik.bankmanagement.Controllers;

import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Views.AccountType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label payee_address_lbl;
    public TextField payee_address_fld;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;
    public TextField password_txt_fld;
    public Button pass_toggle_btn;
    public FontAwesomeIconView toggle_icon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue()));

        login_btn.setOnAction(event -> onLogin());
        acc_selector.setOnAction(_ -> onAccountSelector());
        pass_toggle_btn.setOnAction(_ -> onPasswordToggle());

        // adding change listener to both password fields
        addTextSyncListener(password_fld, password_txt_fld);
        addTextSyncListener(password_txt_fld, password_fld);

        // setting default visibility
        toggleVisibility(password_fld, true);
        toggleVisibility(password_txt_fld, false);
    }

    private void onPasswordToggle() {
        if(Objects.equals(toggle_icon.getGlyphName(), "EYE")) {
            toggle_icon.setGlyphName("EYE_SLASH");
            toggleVisibility(password_fld, true);
            toggleVisibility(password_txt_fld, false);

            password_fld.setText(password_txt_fld.getText());
        }
        else {
            toggle_icon.setGlyphName("EYE");
            toggleVisibility(password_fld, false);
            toggleVisibility(password_txt_fld, true);
            password_txt_fld.setText(password_fld.getText());
        }
    }

    private void toggleVisibility(TextInputControl field, boolean b) {
        field.setVisible(b);
        field.setManaged(b);    // include/exclude from layout
        field.setFocusTraversable(b);   // exclude from tab navigation
    }

    // add change listener to password fields
    private void addTextSyncListener(TextInputControl source, TextInputControl target) {
        source.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if(!target.getText().equals(newValue)) {
                target.setText(newValue);
            }
        });
    }

    private void onAccountSelector() {
        if(acc_selector.getValue() == AccountType.ADMIN) {
            payee_address_lbl.setText("Admin Username:");
        }
        else {
            payee_address_lbl.setText("Payee Address:");
        }
    }

    private void onLogin() {
        Stage stage = (Stage) error_lbl.getScene().getWindow();

        if(Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {
            performClientLogin(stage);
        }
        else {
            performAdminLogin(stage);
        }
    }

    private void performClientLogin(Stage stage) {
        // Evaluate Login Credentials
        Model.getInstance().evaluateClientCred(payee_address_fld.getText(), password_fld.getText());
        if(Model.getInstance().getClientLoginSuccessFlag()) {
            Model.getInstance().getViewFactory().showClientWindow();
            // Close the login stage
            Model.getInstance().getViewFactory().closeStage(stage);
        }
        else {
            performCleanupAndDisplayError();
        }
    }

    private void performAdminLogin(Stage stage) {
        Model.getInstance().evaluateAdminCreds(payee_address_fld.getText(), password_fld.getText());
        if(Model.getInstance().getAdminLoginSuccessFlag()) {
            Model.getInstance().getViewFactory().showAdminWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
        }
        else {
            performCleanupAndDisplayError();
        }
    }

    private void performCleanupAndDisplayError() {
        payee_address_fld.setText("");
        password_fld.setText("");
        error_lbl.setText("No Such Login Credentials");
    }
}
