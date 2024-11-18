package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Models.PasswordManager;
import com.github.lkaushik.bankmanagement.Views.ClientCreationListener;
import com.github.lkaushik.bankmanagement.Views.PasswordStatus;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class CreateClientController implements Initializable, ClientCreationListener {

    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public Label pAddress_lbl;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;
    public TextField password_text_fld;
    public Button pass_toggle_btn;
    public FontAwesomeIconView pass_toggle_icon;

    final String RED_BORDER = "-fx-border-color: red; -fx-border-width: 1px;";
    public StackPane pass_stack;
    boolean hasError = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(_ -> onCreateClient());
        pass_toggle_btn.setOnAction(_ -> onPassToggle());

        Model.getInstance().addClientCreationListener(this);

        resetFieldStyles(fName_fld, lName_fld);
        resetPasswordField();
        resetAccountCheckBoxStyles(ch_acc_box, sv_acc_box);

        addListenerToAccountCheckBox(ch_acc_box, ch_amount_fld);
        addListenerToAccountCheckBox(sv_acc_box, sv_amount_fld);

        addNumericValueVerifier(ch_amount_fld);

        addTextSyncListener(password_fld, password_text_fld);
        addTextSyncListener(password_text_fld, password_fld);

        toggleVisibility(password_fld, true);
        toggleVisibility(password_text_fld, false);

    }

    // called when create client button is pressed
    private void onCreateClient() {
        validateData();
        validatePassword();
        setPayeeAddress();
        initializeClientCreation();
    }

    private void onPassToggle() {
        if(Objects.equals(pass_toggle_icon.getGlyphName(), "EYE")) {
            pass_toggle_icon.setGlyphName("EYE_SLASH");
            toggleVisibility(password_fld, true);
            toggleVisibility(password_text_fld, false);

            password_fld.setText(password_text_fld.getText());
        }
        else {
            pass_toggle_icon.setGlyphName("EYE");
            toggleVisibility(password_fld, false);
            toggleVisibility(password_text_fld, true);
            password_text_fld.setText(password_fld.getText());
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

    // client creation process initializer
    private void initializeClientCreation() {
        if(hasError) return;

        boolean hasSavingsAccount = sv_acc_box.isSelected();
        boolean hasCheckingAccount = ch_acc_box.isSelected();


        Model.getInstance().initializeClientCreation(
                fName_fld.getText(),
                lName_fld.getText(),
                pAddress_lbl.getText(),
                password_fld.getText(),
                hasCheckingAccount, extractValue(ch_amount_fld),
                hasSavingsAccount, extractValue(sv_amount_fld));
    }

    // style re-setters and listeners
    private void resetFieldStyles(TextField... fields) {
        for (TextField field : fields) {
            field.setOnMouseClicked(event -> field.setStyle(""));
        }
    }

    private void resetPasswordField() {
        password_text_fld.setOnMouseClicked(mouseEvent -> {
            password_text_fld.setStyle("");
            password_fld.setStyle("");
        });

        password_fld.setOnMouseClicked(mouseEvent -> {
            password_fld.setStyle("");
            password_text_fld.setStyle("");
        });
    }

    private void resetAccountCheckBoxStyles(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setOnMouseClicked(event -> {
                if(ch_acc_box.isSelected() || sv_acc_box.isSelected()) {
                    ch_acc_box.setStyle("");
                    sv_acc_box.setStyle("");
                }
            });
        }
    }

    private void addListenerToAccountCheckBox(CheckBox checkBox, TextField textField) {
        checkBox.selectedProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue) {
                textField.setEditable(true);
                textField.setStyle("-fx-background-color: #FFFFFF;");
            }
            else {
                textField.setEditable(false);
                textField.setText("");
                textField.setStyle("");
            }
        } ));
    }

    // input value verifiers
    private void addNumericValueVerifier(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        textField.setTextFormatter(new TextFormatter<>(filter));
    }

    // Error handling

    private void validateData() {
        int errorCode = 0;
        boolean checkBoxError = false;
        boolean addressBoxError = false;
        boolean chAmountError = false;
        boolean svAmountError = false;

        if(fName_fld.getText().isEmpty()) {
            setFieldError(fName_fld);
            errorCode++;
        }

        if(lName_fld.getText().isEmpty()) {
            setFieldError(lName_fld);
            errorCode++;
        }

        if(password_fld.getText().isEmpty()) {
            setFieldError(password_fld);
            errorCode++;
        }

        if(password_text_fld.getText().isEmpty()) {
            setFieldError(password_text_fld);
            errorCode++;
        }

        if(!ch_acc_box.isSelected() && !sv_acc_box.isSelected()) {
            setCheckBoxError(ch_acc_box);
            setCheckBoxError(sv_acc_box);
            checkBoxError = true;
        }

        if(ch_acc_box.isSelected() && extractValue(ch_amount_fld) < 100) {
            chAmountError = true;
            ch_amount_fld.setStyle(RED_BORDER + " -fx-background-color: #FFFFFF;");
        }
        else if(ch_acc_box.isSelected()) {
            ch_amount_fld.setStyle(" -fx-background-color: #FFFFFF;");
        }

        if(sv_acc_box.isSelected() && extractValue(sv_amount_fld) < 100) {
            svAmountError = true;
            sv_amount_fld.setStyle(RED_BORDER + " -fx-background-color: #FFFFFF;");
        }
        else if(sv_acc_box.isSelected()) {
            sv_amount_fld.setStyle(" -fx-background-color: #FFFFFF;");
        }

        updateErrorMessage(errorCode, checkBoxError, addressBoxError, chAmountError, svAmountError);
    }

    private void validatePassword() {
        if(hasError) return;

        PasswordStatus status = PasswordManager.validate(password_fld.getText());
        if(status == PasswordStatus.VALID) return;
        else if(status == PasswordStatus.TOO_SHORT) {
            error_lbl.setText("Your password must be at least 8 characters long");
        }
        else {
            error_lbl.setText("Your password should only contains a-z, A-Z, 0-9 and !@#$%^&*()");
        }

        hasError = true;
    }

    private void updateErrorMessage(int errorCode, boolean checkBoxError, boolean addressBoxError, boolean chAmountError, boolean svAmountError) {
        StringBuilder errorMsg = new StringBuilder();
        if(errorCode > 0) errorMsg.append("One or more field is empty!\n");
        if(checkBoxError) errorMsg.append("One account must be selected!\n");
        if(chAmountError || svAmountError) errorMsg.append("Minimum amount should be 100 rupees.");

        hasError = !errorMsg.isEmpty();
        error_lbl.setText(errorMsg.toString());
    }

    private void setFieldError(TextField field) {
        field.setStyle(RED_BORDER);
    }

    private void setCheckBoxError(CheckBox checkbox) {
        checkbox.setStyle(RED_BORDER);
    }

    private void setPayeeAddress() {
        if(hasError) return;
        pAddress_lbl.setText(Model.getInstance().generatePayeeAddress(fName_fld.getText(), lName_fld.getText()));
    }

    private double extractValue(TextField textField) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public void onClientCreationCompleted() {
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        password_text_fld.setText("");
        pAddress_lbl.setText("");
        ch_acc_box.setSelected(false);
        sv_acc_box.setSelected(false);
    }
}
