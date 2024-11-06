package com.github.lkaushik.bankmanagement.Models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertBoxCreator {

    public static Optional<ButtonType> createMessageAlert(Alert.AlertType alertType, String title, String alertMessage) {
        return  configureAlert(alertType, title, alertMessage, null, false,null, false);
    }

    public static Optional<ButtonType> createMessageAlert(Alert.AlertType alertType, String title, String alertMessage, boolean withButtons) {
        return  configureAlert(alertType, title, alertMessage, null, false,null, withButtons);
    }

    public static Optional<ButtonType> createAlert(Alert.AlertType alertType, String title, String alertMessage) {
        return  configureAlert(alertType, title, alertMessage, null, true, null, false);
    }

    public static Optional<ButtonType> createAlert(Alert.AlertType alertType, String title, String alertMessage, boolean withButtons) {
        return  configureAlert(alertType, title, alertMessage, null, true, null, withButtons);
    }

    private static Optional<ButtonType> configureAlert(Alert.AlertType alertType, String title, String alertMessage, String headerText, boolean showDefaultGraphics, Node customGraphic, boolean withButtons) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        if(!showDefaultGraphics || customGraphic != null)
            alert.setGraphic(customGraphic);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(String.valueOf(AlertBoxCreator.class.getResource("/Images/bank.png"))));
        Text content = new Text(alertMessage);
        HBox hBox = new HBox(content);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20, 20, 20, 20));
        alert.getDialogPane().setContent(hBox);
        if(!withButtons)
            alert.getDialogPane().getButtonTypes().clear();
        stage.setOnCloseRequest(event -> {
            stage.close();
        });
        return alert.showAndWait();
    }
}
