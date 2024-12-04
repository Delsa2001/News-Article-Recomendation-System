package org.example.cw;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BoxController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label contentPreview;

    private String fullContent;

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setContentPreview(String content) {
        this.fullContent = content;
        contentPreview.setText(content.length() > 50 ? content.substring(0, 50) + "..." : content);
    }

    @FXML
    public void handleArticleClick() {
        // Get the full content (or a placeholder for now) to show in the popup
        String fullContent = contentPreview.getText(); // This could be expanded to get actual content

        // Access the TechnologyController and call showArticlePopup
        TechnologyController controller = new TechnologyController();
        controller.showArticlePopup(titleLabel.getText(), fullContent);
    }
}
