package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Label welcomeLabel;

    private int userId; // Store user ID

    /**
     * Set the welcome message with the user's full name.
     */
    public void setWelcomeMessage(String username) {
        welcomeLabel.setText("Welcome " + username);
        // Debugging: Check the userId before using it
        System.out.println("User ID in HomePageController: " + userId);  // Debugging statement
    }

    /**
     * Set the user ID.
     */
    public void setUserId(int userId) {
        // Debugging: Check if userId is set correctly
        System.out.println("Setting User ID in HomePageController: " + userId);
        this.userId = userId;
    }

    public int getUserId() {
        System.out.println("Getting User ID in HomePageController: " + userId);  // Debugging statement
        return userId;
    }

    public void onTechnologyHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Technology page is accessed
            System.out.println("Navigating to Technology page with User ID: " + userId);

            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Technology.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and pass the user ID
            TechnologyController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the TechnologyController
            controller.loadArticles("Technology");  // Load articles for the Technology category

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHealthHyperlinkClick() {
        try {
            System.out.println("Navigating to Health page with User ID: " + userId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Health.fxml"));
            AnchorPane healthPage = loader.load();

            HealthController healthController = loader.getController();
            healthController.setUserId(userId); // Pass user ID to the HealthController
            healthController.loadArticles(); // Load Health articles

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(healthPage);
            stage.setScene(scene);
            stage.setTitle("Health Articles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAIHyperlinkClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AI.fxml"));
            AnchorPane aiPage = loader.load();

            AIController aiController = loader.getController();
            aiController.setUserId(userId); // Pass user ID to AIController
            aiController.loadArticles(); // Load AI articles

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(aiPage);
            stage.setScene(scene);
            stage.setTitle("AI Articles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAgricultureHyperlinkClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Agriculture.fxml"));
            AnchorPane agriculturePage = loader.load();

            AgricultureController agricultureController = loader.getController();
            agricultureController.setUserId(userId); // Pass user ID to AgricultureController
            agricultureController.loadArticles(); // Load Agriculture articles

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(agriculturePage);
            stage.setScene(scene);
            stage.setTitle("Agriculture Articles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSportsHyperlinkClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sports.fxml"));
            AnchorPane sportsPage = loader.load();

            SportsController sportsController = loader.getController();
            sportsController.setUserId(userId); // Pass user ID to SportsController
            sportsController.loadArticles(); // Load Sports articles

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(sportsPage);
            stage.setScene(scene);
            stage.setTitle("Sports Articles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onWarHyperlinkClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("War.fxml"));
            AnchorPane sportsPage = loader.load();

            WarController warController = loader.getController();
            warController.setUserId(userId); // Pass user ID to SportsController
            warController.loadArticles(); // Load Sports articles

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(sportsPage);
            stage.setScene(scene);
            stage.setTitle("Sports Articles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

