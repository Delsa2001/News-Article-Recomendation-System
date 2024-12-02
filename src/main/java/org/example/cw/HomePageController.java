package org.example.cw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Label welcomeLabel;

    public void setWelcomeMessage(String username) {
        welcomeLabel.setText("Welcome " + username);
    }

    public void onTechnologyHyperlinkClick(ActionEvent event) {
        try {
            // Load the Technology.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Technology.fxml"));
            Parent root = loader.load();

            // Access the TechnologyController and call loadArticles for "Technology" category
            TechnologyController controller = loader.getController();
            controller.loadArticles("Technology");

            // Transition to Technology.fxml
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}