package org.example.cw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryController {

    @FXML
    private Label welcomeLabel;

    private int userId;

    @FXML
    private TableView<Article> tableView;

    @FXML
    private TableColumn<Article, Integer> articleIdColumn;

    @FXML
    private TableColumn<Article, String> categoryColumn;

    @FXML
    private TableColumn<Article, String> articleColumn;

    private ObservableList<Article> userHistory = FXCollections.observableArrayList();




    public void setUserId(int userId) {
        this.userId = userId;
        loadUserHistory();
    }

    private void loadUserHistory() {
        System.out.println("Loading history for user ID: " + userId);
        userHistory.clear(); // Clear the list before adding new data.

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT a.article_id, a.title, a.category_type FROM articles a " +
                    "JOIN user_history urh ON a.article_id = urh.article_id " +
                    "WHERE urh.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int articleId = resultSet.getInt("article_id");
                String title = resultSet.getString("title");
                String category = resultSet.getString("category_type");

                userHistory.add(new Article(articleId, title, null, null, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(userHistory);
    }

    @FXML
    public void initialize() {
        // Set up the columns with the corresponding properties.
        articleIdColumn.setCellValueFactory(new PropertyValueFactory<>("article_id"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        articleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    }

    @FXML
    public void onBackToHomeClick() {
        System.out.println("Back to Home Page clicked. Navigating...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent homePage = loader.load();

            HomePageController homePageController = loader.getController();
            homePageController.setId(userId); // Pass the user ID back to HomePageController
            homePageController.setWelcomeMessage("Back!"); // Set a welcome message

            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(homePage);
            stage.setScene(scene);
            stage.setTitle("Home Page");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error navigating back to HomePage.fxml");
        }
    }


}
