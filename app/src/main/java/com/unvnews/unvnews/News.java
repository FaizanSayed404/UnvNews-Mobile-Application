package com.unvnews.unvnews;

import java.util.ArrayList;
import java.util.List;

public class News {

    public String BASE_URL = "https://newsapi.org/v2/";

    public String API_KEY = "87ceb7c136aa462eaf4a1b206ff162e6";

    public String COUNTRY = "in";

    private final List<Articles> articles = new ArrayList<>();

    public List<Articles> getArticles() {
        return articles;
    }
}
