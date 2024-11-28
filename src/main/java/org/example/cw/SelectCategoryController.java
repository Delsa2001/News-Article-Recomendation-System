package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class SelectCategoryController {

    @FXML
    private RadioButton aiRadioButton, agricultureRadioButton, healthRadioButton, sportsRadioButton, technologyRadioButton, warRadioButton;
    @FXML
    private Button nextButton;
    @FXML
    private ToggleGroup categoryGroup;

    private int userId;
    private String userName;
    private String userEmail;

    public void initialize() {
        // Disable the "Next" button by default
        nextButton.setDisable(true);

        // Add listeners to radio buttons to check if at least two categories are selected
        categoryGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateNextButtonState());
    }

    private void updateNextButtonState() {
        // Check if at least two categories are selected
        long selectedCount = categoryGroup.getToggles().stream().filter(t -> ((RadioButton) t).isSelected()).count();
        nextButton.setDisable(selectedCount < 2);
    }

    @FXML
    public void handleNextButton(ActionEvent event) {
        // Check if at least two categories are selected
        long selectedCount = categoryGroup.getToggles().stream().filter(t -> ((RadioButton) t).isSelected()).count();

        if (selectedCount < 2) {
            // Display an error message if fewer than 2 categories are selected
            showAlert(Alert.AlertType.ERROR, "Selection Error", "Please select at least 2 categories.");
        } else {
            // Fetch the selected categories
            StringBuilder selectedCategories = new StringBuilder();
            for (javafx.scene.control.Toggle toggle : categoryGroup.getToggles()) {
                if (((RadioButton) toggle).isSelected()) {
                    selectedCategories.append(((RadioButton) toggle).getText()).append(", ");
                }
            }

            // Remove trailing comma and space
            if (selectedCategories.length() > 0) {
                selectedCategories.setLength(selectedCategories.length() - 2);
            }

            // Save the selected categories to the database
            try {
                saveSelectedCategoriesToDatabase(selectedCategories.toString());

                // Redirect to HomePage.fxml after successful selection
                redirectToHomePage(event);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save categories: " + e.getMessage());
            }
        }
    }

    private void saveSelectedCategoriesToDatabase(String selectedCategories) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/WorkBenchDatabase";  // Replace with your DB details
        String dbUsername = "root";
        String dbPassword = "admin";  // Replace with your DB password

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO userselectedcategory (user_id, user_name, user_email, selected_categories) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setString(2, userName);
            statement.setString(3, userEmail);
            statement.setString(4, selectedCategories);  // Store the selected categories as a string

            statement.executeUpdate();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void redirectToHomePage(ActionEvent event) {
        try {
            // Load HomePage.fxml
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Home Page: " + e.getMessage());
        }
    }

    // Set user data from previous stage (such as after account creation)
    public void setUserData(int userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }



}
