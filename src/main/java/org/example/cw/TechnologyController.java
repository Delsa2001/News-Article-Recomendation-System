package org.example.cw;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
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

public class TechnologyController {

    @FXML
    private TilePane articleTilePane;

    @FXML
    public void initialize() {
        // Initialization logic if required
    }

    public void loadArticles(String category) {
        articleTilePane.getChildren().clear();

        String query = "SELECT * FROM articles WHERE category_type = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Load individual article box for each article
                VBox articleBox = createArticleBox(
                        resultSet.getString("title"),
                        resultSet.getString("article_content")
                );
                articleTilePane.getChildren().add(articleBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createArticleBox(String title, String content) {
        try {
            // Load the Box.fxml to create an article box
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Box.fxml"));
            VBox articleBox = loader.load();

            // Set the title and content for this box
            BoxController boxController = loader.getController();
            boxController.setTitle(title);
            boxController.setContentPreview(content);

            return articleBox;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Show the article popup
    public void showArticlePopup(String title, String content) {
        try {
            // Load Popup.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
            AnchorPane popupBox = loader.load(); // Match the root type in Popup.fxml

            // Set the article details
            PopupController popupController = loader.getController();
            popupController.setArticle(title, content);

            // Create and show the popup window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            Scene popupScene = new Scene(popupBox); // Use the appropriate root here
            popupStage.setScene(popupScene);
            popupStage.setTitle("Article Details");
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
