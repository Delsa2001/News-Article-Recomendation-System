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

    public void setCategory(String category) {
        this.category.setText(category); // Set the category label
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


    /**
     * Insert or update the user preference (like or dislike) in the database.
     */
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
                String existingPreference = resultSet.getString("user_preference");
                if (!existingPreference.equals(preference)) {
                    // Remove the old preference
                    try (PreparedStatement deleteStatement = connection.prepareStatement(queryDelete)) {
                        deleteStatement.setInt(1, userId);
                        deleteStatement.setInt(2, articleId);
                        deleteStatement.executeUpdate();
                    }
                    // Insert the new preference and update the score
                    insertPreference(connection, queryInsert, preference);
                    updateCategoryScore(preference);
                } else {
                    showAlert("Info", "You have already marked this as " + preference + ".");
                }
            } else {
                // No record exists; insert the new preference and update the score
                insertPreference(connection, queryInsert, preference);
                updateCategoryScore(preference);
            }
        } catch (SQLException e) {
            showAlert("Error", "An error occurred while saving your preference.");
            e.printStackTrace();
        }
    }

    /**
     * Insert a new preference into the user_likes table.
     */
    private void insertPreference(Connection connection, String queryInsert, String preference) throws SQLException {
        try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
            insertStatement.setInt(1, userId);
            insertStatement.setInt(2, articleId);
            insertStatement.setString(3, preference);
            insertStatement.executeUpdate();
            showAlert("Success", "Your preference has been saved as " + preference + "!");
        }
    }

    /**
     * Update the category score for the user.
     */
    private void updateCategoryScore(String preference) {
        String categoryType = category.getText().toLowerCase() + "_score"; // Map category to column name
        int increment = preference.equals("like") ? 1 : -1; // Increment for like, 0 for dislike

        System.out.println("Debug: Starting updateCategoryScore");
        System.out.println("Debug: Category: " + category.getText());
        System.out.println("Debug: Category Type (Column): " + categoryType);
        System.out.println("Debug: Increment Value: " + increment);
        System.out.println("Debug: User ID: " + userId);

        // Define the queries
        String queryInsertOrIgnore = "INSERT INTO category_score (user_id) VALUES (?) ON DUPLICATE KEY UPDATE user_id = user_id";
        String queryUpdateScore = "UPDATE category_score SET " + categoryType + " = " + categoryType + " + ? WHERE user_id = ?";

        System.out.println("Debug: Insert/Ignore Query: " + queryInsertOrIgnore);
        System.out.println("Debug: Update Score Query: " + queryUpdateScore);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement insertOrIgnoreStatement = connection.prepareStatement(queryInsertOrIgnore);
             PreparedStatement updateScoreStatement = connection.prepareStatement(queryUpdateScore)) {

            // Step 1: Ensure a row exists for the user in category_score table
            insertOrIgnoreStatement.setInt(1, userId);
            System.out.println("Debug: Executing Insert/Ignore for user_id = " + userId);
            int insertedRows = insertOrIgnoreStatement.executeUpdate();
            System.out.println("Debug: Rows affected by Insert/Ignore: " + insertedRows);

            // Step 2: Update the score if the row exists
            updateScoreStatement.setInt(1, increment);
            updateScoreStatement.setInt(2, userId);
            System.out.println("Debug: Executing Update Score for user_id = " + userId + " with increment = " + increment);

            int rowsAffected = updateScoreStatement.executeUpdate();
            System.out.println("Debug: Rows affected by Update Score: " + rowsAffected);

            // Check if update was successful
            if (rowsAffected > 0) {
                System.out.println("Debug: Score updated successfully for user_id: " + userId);
                showAlert("Success", "Your score has been updated for " + categoryType + "!");
            } else {
                System.out.println("Debug: No rows affected. This should not happen after insertion.");
                showAlert("Error", "Failed to update your score. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Debug: SQL Exception occurred: " + e.getMessage());
            showAlert("Error", "An error occurred while updating your score.");
            e.printStackTrace();
        }

        System.out.println("Debug: Exiting updateCategoryScore");
    }




    /**
     * Show an alert dialog.
     */
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

        // Save the article read history to the database
        saveArticleReadHistory(userId, articleId);
    }

    /**
     * Save the article read history for the user in the database.
     */
    private void saveArticleReadHistory(int userId, int articleId) {
        String query = "INSERT INTO user_history (user_id, article_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, articleId);
            statement.executeUpdate();

            System.out.println("Article read history saved successfully for user " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while saving the article history.");
        }
    }



}
