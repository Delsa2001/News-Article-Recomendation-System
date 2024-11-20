package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node; // Make sure this is included
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

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Userdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";

    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Missing information", "Please enter both email and password.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT password FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                if (storedPassword.equals(password)) {
                    // Login successful, navigate to Home page
                    navigateToPage(event, "HomePage.fxml", "Home");
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

    private void navigateToPage(ActionEvent event, String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Node is used here
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
            // Load the Create Account FXML file
            Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));

            // Get the current stage and set the new scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Create an Account");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            // Show an error alert if there's an issue loading the FXML file
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Load Failed");
            alert.setContentText("Unable to load the Create Account page.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleBackToWelcome(ActionEvent event) {
        try {
            // Load the Welcome FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Welcome.fxml"));

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

            // Show an error alert if there's an issue loading the FXML file
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Page Load Failed");
            alert.setContentText("Unable to load the Welcome page.");
            alert.showAndWait();
        }
    }


}
