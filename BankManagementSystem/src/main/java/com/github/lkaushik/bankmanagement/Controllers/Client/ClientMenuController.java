package com.github.lkaushik.bankmanagement.Controllers.Client;

import com.github.lkaushik.bankmanagement.Models.AlertBoxCreator;
import com.github.lkaushik.bankmanagement.Models.Model;
import com.github.lkaushik.bankmanagement.Views.ClientMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
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
        report_btn.setOnAction(event -> onReport());
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

    private void onReport() {
        String reportMessage = getReport();
        if(reportMessage != null) {
            Model.getInstance().sendReport(reportMessage);
        }
    }

    private String getReport() {
        String report = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report");
        alert.setHeaderText("Make sure your report is valid, in case of any unwanted report, \nlegal actions can be taken against you.");

        TextArea textArea = createTextArea();

        VBox vbox = new VBox();
        vbox.getChildren().add(textArea);
        alert.getDialogPane().setContent(vbox);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            report = textArea.getText();
            if (validateReport(report))
                return report;
        }
        return null;
    }

    private boolean validateReport(String report) {
        if(report.isEmpty()) {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Report status", "Your report message is empty. Failed to send the report.");
            return false;
        }

        if(report.equals("Mention here if its a report/suggestion/bug\n\nYour full name here\n\nEnter details here")) {
            AlertBoxCreator.createAlert(Alert.AlertType.ERROR, "Report status", "Your report message is unchanged. Failed to send the report.");
            return false;
        }

        return true;
    }

    private TextArea createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setText("Mention here if its a report/suggestion/bug\n\nYour full name here\n\nEnter details here");
        textArea.setWrapText(true);

        return textArea;
    }

}
