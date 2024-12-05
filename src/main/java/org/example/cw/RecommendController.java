package org.example.cw;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecommendController {

    // FXML annotation to bind the TilePane from FXML
    @FXML
    private TilePane articleTilePane;  // This is where the articles will be displayed inside TilePane

    private int userId;

    // This method will be used to set the userId from the parent controller or when navigating to this page
    public void setUserId(int userId) {
        this.userId = userId;
        System.out.println("User ID set to: " + userId);  // Debugging statement
        loadRecommendedArticles(); // Call to load the articles after setting the userId
    }

    // This method is called when the page is loaded or user ID is set
    public void initialize() {
        System.out.println("Initializing RecommendController...");  // Debugging statement

        // Ensure that the articleTilePane is not null
        if (articleTilePane != null) {
            if (userId != 0) { // Ensure that userId is set
                System.out.println("User ID is valid, loading recommended articles...");  // Debugging statement
                loadRecommendedArticles();
            } else {
                System.out.println("Error: User ID is not set!");  // Debugging statement
            }
        } else {
            System.out.println("Error: articleTilePane is null!");  // Debugging statement
        }
    }

    // Load recommended articles based on user scores
    private void loadRecommendedArticles() {
        System.out.println("Loading recommended articles for user ID: " + userId);  // Debugging statement

        // Fetch recommended articles using the RecommendationModel
        List<Article> recommendedArticles = RecommendationModel.getRecommendedArticles(userId);
        System.out.println("Fetched " + recommendedArticles.size() + " recommended articles.");  // Debugging statement

        // Dynamically add articles to the TilePane
        for (Article article : recommendedArticles) {
            System.out.println("Adding article: " + article.getTitle() + " to TilePane");  // Debugging statement
            VBox articleBox = createArticleBox(article);
            if (articleBox != null) {
                articleTilePane.getChildren().add(articleBox);
            } else {
                System.out.println("Error: articleBox is null for article: " + article.getTitle());  // Debugging statement
            }
        }
    }

    // Create a box for each article to display on the page
    private VBox createArticleBox(Article article) {
        try {
            System.out.println("Creating article box for article ID: " + article.getArticleId());  // Debugging statement

            // Load the Box.fxml layout for the article box
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Box.fxml"));
            VBox articleBox = loader.load();

            // Get the controller for Box.fxml (BoxController)
            BoxController boxController = loader.getController();

            // Set article details using the BoxController methods
            boxController.setTitle(article.getTitle());
            boxController.setContentPreview(article.getContent());
            boxController.setArticleId(article.getArticleId()); // Assuming getId() is the method to get the article ID
            boxController.setCategory(article.getCategory()); // Optional - if you want to display categories

            // Return the article box (VBox)
            return articleBox;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading Box.fxml or setting article details for article ID: " + article.getArticleId());  // Debugging statement
        }
        return null;
    }

    @FXML
    public void onBackToHomeClick() {
        System.out.println("Back to Home Page clicked. Navigating...");  // Debugging statement

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            AnchorPane homePage = loader.load();

            HomePageController homePageController = loader.getController();
            homePageController.setId(userId); // Pass user ID back to HomePageController
            homePageController.setWelcomeMessage("Back!"); // Set a welcome message

            Stage stage = (Stage) articleTilePane.getScene().getWindow();
            Scene scene = new Scene(homePage);
            stage.setScene(scene);
            stage.setTitle("Home Page");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error navigating back to HomePage.fxml");  // Debugging statement
        }
    }
}
