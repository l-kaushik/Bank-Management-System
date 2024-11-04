package com.github.lkaushik.bankmanagement.Controllers.Admin;

import com.github.lkaushik.bankmanagement.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Border;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class CreateClientController implements Initializable {

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

    final String RED_BORDER = "-fx-border-color: red; -fx-border-width: 1px;";
    boolean hasError = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_client_btn.setOnAction(_ -> onCreateClient());

        resetFieldStyles(fName_fld, lName_fld, password_fld);
        resetAccountCheckBoxStyles(ch_acc_box, sv_acc_box);

        addListenerToAccountCheckBox(ch_acc_box, ch_amount_fld);
        addListenerToAccountCheckBox(sv_acc_box, sv_amount_fld);

        addNumericValueVerifier(ch_amount_fld);
    }

    private void resetFieldStyles(TextField... fields) {
        for (TextField field : fields) {
            field.setOnMouseClicked(event -> field.setStyle(""));
        }
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

    private void onCreateClient() {
        validateData();
        setPayeeAddress();
    }
    private void validateData() {
        int errorCode = 0;
        boolean checkBoxError = false;
        boolean addressBoxError = false;

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

        if(!ch_acc_box.isSelected() && !sv_acc_box.isSelected()) {
            setCheckBoxError(ch_acc_box);
            setCheckBoxError(sv_acc_box);
            checkBoxError = true;
        }

        updateErrorMessage(errorCode, checkBoxError, addressBoxError);
    }

    private void updateErrorMessage(int errorCode, boolean checkBoxError, boolean addressBoxError) {
        StringBuilder errorMsg = new StringBuilder();
        if(errorCode > 0) errorMsg.append("One or more field is empty!\n");
        if(checkBoxError) errorMsg.append("One account must be selected!\n");

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
        pAddress_lbl.setText("Generate address");
    }
}
