package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountSuccessController {

    @FXML
    private Button nextButton;

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        try {
            // Load the Login Page FXML
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

            // Get the current stage and change the scene to the Login Page
            Stage stage = (Stage) nextButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            // Show an error alert if there's an issue loading the FXML file
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Load Failed");
            alert.setContentText("Unable to load the Login Page.");
            alert.showAndWait();
        }
    }
}