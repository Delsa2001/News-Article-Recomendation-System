package org.example.cw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AIController {

    @FXML
    private TilePane articleTilePane;

    private int userId; // Store user ID for tracking

    @FXML
    public void initialize() {
        // Initialization logic if needed
    }

    /**
     * Set the user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User ID set in AIController: " + userId); // Debug statement
    }

    /**
     * Load articles for the AI category and display them in the TilePane.
     */
    public void loadArticles() {
        articleTilePane.getChildren().clear();

        String query = "SELECT * FROM articles WHERE category_type = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "AI");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Declare and initialize the articleId variable
                int articleId = Integer.parseInt(resultSet.getString("article_id"));
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
        try {
            // Load the Box.fxml to create an article box
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Box.fxml"));
            VBox articleBox = loader.load();

            // Set the user ID in the BoxController
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
            AnchorPane popupBox = loader.load();

            PopupController popupController = loader.getController();
            popupController.setArticle(title, content);

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

            HomePageController homePageController = loader.getController();
            homePageController.setUserId(userId); // Pass user ID back to HomePageController
            homePageController.setWelcomeMessage("Back!"); // Set a welcome message

            Stage stage = (Stage) articleTilePane.getScene().getWindow();
            Scene scene = new Scene(homePage);
            stage.setScene(scene);
            stage.setTitle("Home Page");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}