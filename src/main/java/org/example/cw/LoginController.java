package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    // Database configuration details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/OOPCW"; // Replace with your DB URL
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASSWORD = ""; // Replace with your DB password

    /**
     * Handle the login button click.
     */
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Make sure email and password are not empty
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Email or password cannot be empty.");
            return;
        }

        String query = "SELECT * FROM users WHERE email = ? AND password = ?"; // Use 'id' instead of 'user_id'

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the parameters
            statement.setString(1, email);
            statement.setString(2, password);  // Assuming the password is already hashed

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Login successful, retrieve the user details
                int userId = resultSet.getInt("id");  // Corrected to use 'id'
                String fullName = resultSet.getString("full_name");

                // Pass the user details to the next page
                navigateToPage(userId, fullName); // Update with userId
            } else {
                // Invalid credentials
                showAlert("Error", "Invalid email or password.");
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred while connecting to the database.");
            e.printStackTrace();
        }
    }


    /**
     * Show an alert with a given title and message.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handle creating a new account.
     */
    @FXML
    public void handleCreateAccountButton(ActionEvent event) {
        // Transition to the CreateAccount.fxml page
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            AnchorPane createAccountPage = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
            stage.setScene(new Scene(createAccountPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle going back to the welcome page.
     */
    @FXML
    public void handleBackToWelcome(ActionEvent event) {
        // Navigate back to the Welcome.fxml page
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            AnchorPane welcomePage = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
            stage.setScene(new Scene(welcomePage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void navigateToPage(int userId, String fullName) {
        try {
            // Load the HomePage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent root = loader.load();

            // Get the controller of the HomePage
            HomePageController homePageController = loader.getController();

            // Debugging: Check if the userId is correct before passing it
            System.out.println("Passing User ID to HomePageController: " + userId);

            // Pass the user ID to the HomePageController
            homePageController.setUserId(userId); // Set the user ID first

            // Set the welcome message on the HomePage with the user's full name
            homePageController.setWelcomeMessage(fullName); // Set welcome message after setting user ID

            // Get the current stage and navigate to HomePage
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while loading the homepage.");
        }
    }






}
