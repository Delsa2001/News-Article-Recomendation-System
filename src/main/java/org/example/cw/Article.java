package org.example.cw;

public class Article {
    private int article_id;
    private String title;
    private String article_content;
    private String keywords;
    private String category_type;

    public Article(int id, String title, String content, String keywords, String category) {
        this.article_id = id;
        this.title = title;
        this.article_content = content;
        this.keywords = keywords;
        this.category_type = category;
    }

    public int getArticleId() {
        return article_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return article_content;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getCategory() {
        return category_type;
    }
}
