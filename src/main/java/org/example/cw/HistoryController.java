package org.example.cw;

public class HistoryController {

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadUserHistory();
    }

    private void loadUserHistory() {
        // Use the userId to fetch and display the user's read history
        System.out.println("Loading history for user ID: " + userId);
        // Add database or logic to fetch user history here
    }


}
