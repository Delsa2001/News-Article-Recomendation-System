package org.example.cw;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class PopupController {

    @FXML
    private Label titleLabel;

    @FXML
    private Text contentText;

    // Method to set the content of the popup
    public void setArticle(String title, String content) {
        titleLabel.setText(title);
        contentText.setText(content);
    }

    @FXML
    public void closePopup() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
