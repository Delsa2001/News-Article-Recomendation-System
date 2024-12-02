package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Stage stage;
    private Scene scene;

    // Update these constants to match your phpMyAdmin database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/OOPCW"; // Replace OOPCW with your database name
    private static final String DB_USER = "root"; // Default username for phpMyAdmin
    private static final String DB_PASSWORD = ""; // Default password (leave empty if not set)

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Missing Information", "Please enter both email and password.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Update the query to match your table and column names
            String query = "SELECT full_name, password FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String name = resultSet.getString("full_name"); // Ensure this matches your column name for the full name

                if (storedPassword.equals(password)) {
                    // Login successful, navigate to Home page
                    navigateToPage(event, "HomePage.fxml", "Home", name);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect Password", "The password you entered is incorrect.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Account Not Found", "No account found with the provided email.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Database Error", "An error occurred while connecting to the database.");
        }
    }

    private void navigateToPage(ActionEvent event, String fxmlFile, String title, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        // Pass username to HomePageController
        HomePageController homePageController = loader.getController();
        homePageController.setWelcomeMessage(username);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleCreateAccountButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Create an Account");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Page Load Failed", "Unable to load the Create Account page.");
        }
    }

    @FXML
    public void handleBackToWelcome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Page Load Failed", "Unable to load the Welcome page.");
        }
    }
}
