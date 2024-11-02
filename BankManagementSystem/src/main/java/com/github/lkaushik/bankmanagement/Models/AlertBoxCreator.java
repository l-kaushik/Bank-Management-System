package com.github.lkaushik.bankmanagement.Models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertBoxCreator {

    public static Alert createMessageAlert(Alert.AlertType alertType, String title, String alertMessage) {
        return  configureAlert(alertType, title, alertMessage, null, false,null);
    }

    public static Alert createAlert(Alert.AlertType alertType, String title, String alertMessage) {
        return  configureAlert(alertType, title, alertMessage, null, true, null);
    }

    private static Alert configureAlert(Alert.AlertType alertType, String title, String alertMessage, String headerText, boolean showDefaultGraphics, Node customGraphic) {
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
        alert.getDialogPane().getButtonTypes().clear();
        stage.setOnCloseRequest(event -> {
            stage.close();
        });
        alert.showAndWait();

        return alert;
    }
}
