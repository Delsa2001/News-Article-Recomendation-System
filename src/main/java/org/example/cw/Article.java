package org.example.cw;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Article {

    private final IntegerProperty article_id;
    private final StringProperty title;
    private final StringProperty article_content;
    private final StringProperty keywords;
    private final StringProperty category_type;

    // Constructor
    public Article(int id, String title, String content, String keywords, String category) {
        this.article_id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.article_content = new SimpleStringProperty(content);
        this.keywords = new SimpleStringProperty(keywords);
        this.category_type = new SimpleStringProperty(category);
    }

    // Getters
    public int getArticleId() {
        return article_id.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getContent() {
        return article_content.get();
    }

    public String getKeywords() {
        return keywords.get();
    }

    public String getCategory() {
        return category_type.get();
    }

    // Setters
    public void setArticleId(int id) {
        this.article_id.set(id);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setContent(String content) {
        this.article_content.set(content);
    }

    public void setKeywords(String keywords) {
        this.keywords.set(keywords);
    }

    public void setCategory(String category) {
        this.category_type.set(category);
    }

    // Property methods (for data binding)
    public IntegerProperty article_idProperty() {
        return article_id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty article_contentProperty() {
        return article_content;
    }

    public StringProperty keywordsProperty() {
        return keywords;
    }

    public StringProperty category_typeProperty() {
        return category_type;
    }
}
