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
            // Load the SelectCategory.fxml file (ensure the path is correct)
            Parent root = FXMLLoader.load(getClass().getResource("SelectCategory.fxml"));

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) nextButton.getScene().getWindow();  // Get the stage from the button
            Scene scene = new Scene(root);  // Create a new scene with the loaded FXML
            stage.setScene(scene);  // Set the new scene on the stage
            stage.setTitle("Select Category");  // Optionally, set a new title for the window
            stage.show();  // Display the new scene

        } catch (IOException e) {
            e.printStackTrace();

            // Show an error alert if there's an issue loading the FXML file
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Load Failed");
            alert.setContentText("Unable to load the Select Category page.");
            alert.showAndWait();
        }
    }
}
