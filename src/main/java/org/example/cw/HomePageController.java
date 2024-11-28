package org.example.cw;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController {

    @FXML
    private Label welcomeLabel;

    public void setWelcomeMessage(String username) {
        welcomeLabel.setText("Welcome " + username);
    }
}
