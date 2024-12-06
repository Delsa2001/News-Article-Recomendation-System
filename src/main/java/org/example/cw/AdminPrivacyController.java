package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminPrivacyController {

    @FXML
    private TextField userIdField;

    @FXML
    private TextField roleField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label adminUserIdLabel;

    private int UserId; // Variable to store admin's user ID

    /**
     * Method to handle saving the updated role of a user.
     * This method is called when the "Save" button is clicked.
     */
    @FXML
    private void onSaveButtonClick(ActionEvent event) {
        String userIdText = userIdField.getText();
        String newRole = roleField.getText();

        if (userIdText.isEmpty() || newRole.isEmpty()) {
            showAlert("Error", "Both fields are required!", AlertType.ERROR);
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText); // Convert the user ID to integer

            // Update role in the database
            if (updateUserRole(userId, newRole)) {
                showAlert("Success", "User role updated successfully!", AlertType.INFORMATION);
                statusLabel.setText("Role updated for User ID: " + userId);
            } else {
                showAlert("Error", "Failed to update role. Please try again.", AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid User ID format.", AlertType.ERROR);
        }
    }

    /**
     * Method to update the user role in the database.
     * @param userId The user ID.
     * @param newRole The new role to be assigned.
     * @return true if the update is successful, false otherwise.
     */
    private boolean updateUserRole(int userId, String newRole) {
        String query = "UPDATE users SET role = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newRole);
            preparedStatement.setInt(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to show an alert dialog.
     * @param title The title of the alert.
     * @param message The message to display in the alert.
     * @param alertType The type of alert (ERROR, INFORMATION, etc.).
     */
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Setter method to set the admin's user ID.
     * This method can be used to display or use the admin's ID in the controller.
     * @param UserId The admin's user ID.
     */
    public void setUserId(int UserId) {
        this.UserId = UserId;
        adminUserIdLabel.setText("Admin User ID: " + UserId);  // Optionally display the admin's user ID on the UI
    }
}
