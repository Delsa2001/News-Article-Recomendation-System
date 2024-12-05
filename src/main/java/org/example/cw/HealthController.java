package org.example.cw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthController {

    @FXML
    private TilePane articleTilePane;

    private int userId; // Store user ID
    private String fullName; // Store full name of the user

    @FXML
    public void initialize() {
        // Initialization logic if required
    }

    public void setUserIdAndName(int userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

    /**
     * Set the user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Load articles based on category ("Health") and display them in the TilePane.
     */
    public void loadArticles(String category) {
        System.out.println("Loading articles for category: " + category);
        articleTilePane.getChildren().clear();

        String query = "SELECT * FROM articles WHERE category_type = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Declare and initialize the articleId variable
                int articleId = Integer.parseInt(resultSet.getString("article_id")); // articleId is a String
                String articleTitle = resultSet.getString("title");
                String articleContent = resultSet.getString("article_content");
                String articleCategory = resultSet.getString("category_type");

                // Create the article box with the article details
                VBox articleBox = createArticleBox(articleId, articleTitle, articleContent, articleCategory);

                // Add the article box to the TilePane
                articleTilePane.getChildren().add(articleBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an article box using the Box.fxml template.
     */
    private VBox createArticleBox(int articleId, String title, String content, String category) {
        System.out.println("Creating article box for article ID: " + articleId);
        try {
            // Load the Box.fxml to create an article box
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Box.fxml"));
            VBox articleBox = loader.load();

            // Debugging: Track userId being passed to BoxController
            System.out.println("Passing user ID to BoxController: " + userId);

            // Set the user ID and article details in the BoxController
            BoxController boxController = loader.getController();
            boxController.setUserId(userId);  // Make sure the userId is passed correctly
            boxController.setArticleId(articleId);  // Set the article ID
            boxController.setTitle(title);  // Set the article title
            boxController.setContentPreview(content);  // Set the article content preview
            boxController.setCategory(category);  // Set the article category

            return articleBox;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Show the article details in a popup window.
     */
    public void showArticlePopup(String title, String content) {
        try {
            // Load Popup.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
            AnchorPane popupBox = loader.load();

            // Set the article details
            PopupController popupController = loader.getController();
            popupController.setArticle(title, content);

            // Create and show the popup window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            Scene popupScene = new Scene(popupBox);
            popupStage.setScene(popupScene);
            popupStage.setTitle("Article Details");
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackToHomeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            AnchorPane homePage = loader.load();

            // Pass both the user ID and full name to HomePageController
            HomePageController homePageController = loader.getController();
            homePageController.setId(userId); // Pass user ID
            homePageController.setWelcomeMessage("Back" ); // Set the welcome message with the full name

            Stage stage = (Stage) articleTilePane.getScene().getWindow();
            Scene scene = new Scene(homePage);
            stage.setScene(scene);
            stage.setTitle("Home Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
