package org.example.cw;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoxController {


    @FXML
    private Label titleLabel;

    @FXML
    private Label contentPreview;

    private int articleId;
    private int userId;
    private String fullContent; // Store full content for display

    @FXML
    private Label category; // Label for displaying the category

    // Setter methods for passing values from TechnologyController
    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    // Set the article category
    public void setCategory(String category) {
        this.category.setText(category);  // Set the category label
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContentPreview(String content) {
        if (content.length() > 50) {
            contentPreview.setText(content.substring(0, 50) + "...");
        } else {
            contentPreview.setText(content);
        }
        this.fullContent = content; // Store full content for later use
    }


    private void insertOrUpdateUserPreference(String preference) {
        String queryCheck = "SELECT * FROM user_likes WHERE user_id = ? AND article_id = ?";
        String queryDelete = "DELETE FROM user_likes WHERE user_id = ? AND article_id = ?";
        String queryInsert = "INSERT INTO user_likes (user_id, article_id, user_preference) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {

            // Check if a record already exists
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, articleId);

            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Record exists; check if preference is different
                String existingPreference = resultSet.getString("user_preference");
                if (!existingPreference.equals(preference)) {
                    // Remove the old preference
                    try (PreparedStatement deleteStatement = connection.prepareStatement(queryDelete)) {
                        deleteStatement.setInt(1, userId);
                        deleteStatement.setInt(2, articleId);
                        deleteStatement.executeUpdate();
                    }
                    // Insert the new preference
                    insertPreference(connection, queryInsert, preference);
                } else {
                    // Preference is the same; show an alert
                    showAlert("Info", "You have already marked this as " + preference + ".");
                }
            } else {
                // No record exists; insert the new preference
                insertPreference(connection, queryInsert, preference);
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred while saving your preference.");
            e.printStackTrace();
        }
    }

    private void insertPreference(Connection connection, String queryInsert, String preference) throws SQLException {
        try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
            insertStatement.setInt(1, userId);
            insertStatement.setInt(2, articleId);
            insertStatement.setString(3, preference);
            insertStatement.executeUpdate();
            showAlert("Success", "Your preference has been saved as " + preference + "!");
        }
    }

    // Show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleLikeButtonClick() {
        if (userId == 0) {
            showAlert("Error", "User ID is missing. Please log in again.");
        } else if (articleId == 0) {
            showAlert("Error", "Article ID is missing. Please select an article.");
        } else {
            insertOrUpdateUserPreference("like");
        }
    }

    @FXML
    public void handleDislikeButtonClick() {
        if (userId == 0) {
            showAlert("Error", "User ID is missing. Please log in again.");
        } else if (articleId == 0) {
            showAlert("Error", "Article ID is missing. Please select an article.");
        } else {
            insertOrUpdateUserPreference("dislike");
        }
    }

    @FXML
    public void handleArticleClick() {
        TechnologyController controller = new TechnologyController();
        controller.showArticlePopup(titleLabel.getText(), fullContent);
    }

    @FXML
    public void showFullContent() {
        if (fullContent != null && !fullContent.isEmpty()) {
            System.out.println("Full Content: " + fullContent);
        } else {
            System.out.println("No content available.");
        }
    }
}
