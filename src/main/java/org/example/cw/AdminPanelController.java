package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanelController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeLabel;

    private int userId; // Store user ID





    @FXML
    private void initialize() {
        // You can perform additional initialization if needed
        System.out.println("Welcome Label: " + welcomeLabel);  // Ensure it's initialized
    }



    public void setWelcomeMessage(String username) {
        // Setting the text to show "Welcome" followed by the username
        welcomeLabel.setText("Welcome\n" + username);  // \n adds a line break
        System.out.println("User ID in HomePageController: " + userId);
    }


    /**
     * Set the user ID.
     */
    public void setId(int userId) {
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

    public void onHealthHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Technology page is accessed
            System.out.println("Navigating to health page with User ID: " + userId);

            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Health.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and pass the user ID
            HealthController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the TechnologyController
            controller.loadArticles("Health");  // Load articles for the Technology category

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAIHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Technology page is accessed
            System.out.println("Navigating to AI page with User ID: " + userId);

            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AI.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and pass the user ID
            AIController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the TechnologyController
            controller.loadArticles("AI");  // Load articles for the Technology category

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAgricultureHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Technology page is accessed
            System.out.println("Navigating to Agriculture page with User ID: " + userId);

            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Agriculture.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and pass the user ID
            AgricultureController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the TechnologyController
            controller.loadArticles("Agriculture");  // Load articles for the Technology category

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSportsHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Technology page is accessed
            System.out.println("Navigating to Sports page with User ID: " + userId);

            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sports.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and pass the user ID
            SportsController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the TechnologyController
            controller.loadArticles("Sports");  // Load articles for the Technology category

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onWarHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Technology page is accessed
            System.out.println("Navigating to War page with User ID: " + userId);

            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("War.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and pass the user ID
            WarController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the TechnologyController
            controller.loadArticles("War");  // Load articles for the Technology category

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onRecommendHyperlinkClick(ActionEvent event) {
        try {
            // Debugging: Track when the Recommend page is accessed
            System.out.println("Navigating to Recommend page with User ID: " + userId);

            // Load the Recommend.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Recommend.fxml"));
            Parent root = loader.load();

            // Access the RecommendController and pass the user ID
            RecommendController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the RecommendController

            // Transition to Recommend.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading the Recommend page");
            e.printStackTrace();
        }
    }


    @FXML
    private void onReadHistoryClick(ActionEvent event) {
        try {

            System.out.println("Navigating to history page with User ID: " + userId);
            // Load the FXML file for the History page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("History.fxml"));
            Parent root = loader.load();

            // Get the controller for the History page
            HistoryController historyController = loader.getController();
            historyController.setUserId(userId); // Pass the user ID

            // Create a new scene and set it on the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onPrivacyButtonClick(ActionEvent event) {
        try {
            // Debugging: Track when the Privacy page is accessed
            System.out.println("Navigating to Privacy page with User ID: " + userId);

            // Load the Privacy.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPrivacy.fxml"));
            Parent root = loader.load();

            // Access the PrivacyController and pass the user ID (if necessary)
            AdminPrivacyController controller = loader.getController();
            controller.setUserId(userId);  // Pass the user ID to the PrivacyController

            // Transition to Privacy.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading the Privacy page");
            e.printStackTrace();
        }
    }




    public void handleLogout(ActionEvent event) {
        try {
            // Load the Login.fxml file
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log error
        }
    }



}
