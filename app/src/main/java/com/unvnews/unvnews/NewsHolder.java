package com.unvnews.unvnews;

public class NewsHolder {
    private String title;
    private String description;
    private String ImageUrl;
    private String NewsUrl;

    public NewsHolder(String title, String description, String imageUrl,String NewsUrl) {
        this.title = title;
        this.description = description;
        ImageUrl = imageUrl;
        this.NewsUrl = NewsUrl;
    }
    public NewsHolder() {
    }

    public String getNewsUrl() {
        return NewsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        NewsUrl = newsUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
