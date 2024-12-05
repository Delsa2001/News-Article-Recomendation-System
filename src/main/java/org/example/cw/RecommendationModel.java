package org.example.cw;

import org.example.cw.Article;
import org.example.cw.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendationModel {

    private int userId;

    public static List<Article> getRecommendedArticles(int userId) {
        List<Article> recommendedArticles = new ArrayList<>();

        // Step 1: Get the scores for categories for the given user
        String getTopCategoriesQuery =
                "SELECT * FROM category_score WHERE user_id = ?";

        System.out.println("Fetching category scores for user_id: " + userId);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(getTopCategoriesQuery)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Category scores found for user_id: " + userId);

                // Step 2: Get the scores for each category
                int[] scores = new int[6];  // Technology, Health, AI, Agriculture, Sports, War scores
                scores[0] = rs.getInt("technology_score");
                scores[1] = rs.getInt("health_score");
                scores[2] = rs.getInt("ai_score");
                scores[3] = rs.getInt("agriculture_score");
                scores[4] = rs.getInt("sports_score");
                scores[5] = rs.getInt("war_score");

                System.out.println("Scores retrieved: " +
                        "Technology: " + scores[0] + ", " +
                        "Health: " + scores[1] + ", " +
                        "AI: " + scores[2] + ", " +
                        "Agriculture: " + scores[3] + ", " +
                        "Sports: " + scores[4] + ", " +
                        "War: " + scores[5]);

                // Find top 3 categories based on the highest scores
                String[] categories = {"technology", "health", "ai", "agriculture", "sports", "war"};
                List<String> topCategories = getTopCategories(categories, scores);

                System.out.println("Top 3 categories: " + topCategories);

                // Step 3: Get articles for the top 3 categories
                String getArticlesQuery =
                        "SELECT * FROM Articles WHERE category_type IN (?, ?, ?)";

                try (PreparedStatement psArticles = connection.prepareStatement(getArticlesQuery)) {
                    // Set the top 3 categories to the query
                    psArticles.setString(1, topCategories.get(0));
                    psArticles.setString(2, topCategories.get(1));
                    psArticles.setString(3, topCategories.get(2));

                    ResultSet articlesRS = psArticles.executeQuery();
                    int articleCount = 0;

                    while (articlesRS.next()) {
                        // Create Article objects from the result set and add to the list
                        Article article = new Article(
                                articlesRS.getInt("article_id"),
                                articlesRS.getString("title"),
                                articlesRS.getString("article_content"),
                                articlesRS.getString("keywords"),
                                articlesRS.getString("category_type")
                        );
                        recommendedArticles.add(article);
                        articleCount++;
                    }

                    System.out.println("Number of articles fetched: " + articleCount);
                }
            } else {
                System.out.println("No category scores found for user_id: " + userId);
            }
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        }

        return recommendedArticles;
    }

    // Helper method to get the top 3 categories based on scores
    private static List<String> getTopCategories(String[] categories, int[] scores) {
        // Pairing categories with their scores
        List<CategoryScorePair> scorePairs = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            scorePairs.add(new CategoryScorePair(categories[i], scores[i]));
        }

        // Sorting the pairs based on score in descending order
        scorePairs.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        // Extracting the top 3 categories (ensure there are at least 3 categories)
        List<String> topCategories = new ArrayList<>();
        for (int i = 0; i < Math.min(3, scorePairs.size()); i++) {
            topCategories.add(scorePairs.get(i).getCategory());
        }

        return topCategories;
    }

    // Helper class to hold category and score pairs
    private static class CategoryScorePair {
        private final String category;
        private final int score;

        public CategoryScorePair(String category, int score) {
            this.category = category;
            this.score = score;
        }

        public String getCategory() {
            return category;
        }

        public int getScore() {
            return score;
        }
    }
}
