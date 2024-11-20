package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreateAccountController {

    @FXML
    private TextField nameField, dobField, genderField, countryField, emailField;
    @FXML
    private PasswordField passwordField, confirmPasswordField;

    private String validateName(String name) {
        if (!name.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid name. Only letters and spaces are allowed.");
        }
        return name.trim();
    }

    private LocalDate validateDOB(String dob) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dob.trim(), formatter);

            int age = Period.between(date, LocalDate.now()).getYears();
            if (age < 10) {
                throw new IllegalArgumentException("Age should be over 10 to access this.");
            }

            return date;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use dd/MM/yyyy.");
        }
    }

    private String validateGender(String gender) {
        if (!gender.equalsIgnoreCase("male") &&
                !gender.equalsIgnoreCase("female") &&
                !gender.equalsIgnoreCase("other")) {
            throw new IllegalArgumentException("Invalid gender. Please enter 'male', 'female', or 'other'.");
        }
        return gender.toLowerCase();
    }

    private String validateCountry(String country) {
        if (!country.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid country. Only letters and spaces are allowed.");
        }
        return country.trim();
    }

    private String validateEmail(String email) {
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        return email.trim();
    }

    private String validatePasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match. Please try again.");
        }
        return password;
    }

    private void saveToDatabase(String name, LocalDate dob, String gender, String country, String email,
                                String password) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Userdatabase";
        String username = "root"; // Replace with your DB username
        String dbPassword = "admin"; // Replace with your DB password

        Connection connection = DriverManager.getConnection(url, username, dbPassword);
        String sql = "INSERT INTO Users (name, dob, gender, country, email, password) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setDate(2, java.sql.Date.valueOf(dob));
        statement.setString(3, gender);
        statement.setString(4, country);
        statement.setString(5, email);
        statement.setString(6, password);

        statement.executeUpdate();
        connection.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) {
        try {
            // Validate inputs (without saving them to the database)
            String name = validateName(nameField.getText());
            LocalDate dob = validateDOB(dobField.getText());
            String gender = validateGender(genderField.getText());
            String country = validateCountry(countryField.getText());
            String email = validateEmail(emailField.getText());
            String password = validatePasswords(passwordField.getText(), confirmPasswordField.getText());

            // Save to the database only once
            saveToDatabase(name, dob, gender, country, email, password);

            // Redirect to the success page AFTER saving the data
            redirectToSuccessPage(event);

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            showAlert(Alert.AlertType.ERROR, "Validation Error", e.getMessage());
        } catch (SQLException e) {
            // Handle SQL errors
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save data to the database: " + e.getMessage());
        }
    }

    private void redirectToSuccessPage(ActionEvent event) {
        try {
            // Load AccountSuccess.fxml (No saving done here)
            Parent root = FXMLLoader.load(getClass().getResource("AccountSuccess.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Account Created Successfully");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Success Page: " + e.getMessage());
        }
    }

}
