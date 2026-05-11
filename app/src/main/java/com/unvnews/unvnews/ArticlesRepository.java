package com.unvnews.unvnews;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticlesRepository {
    ArticlesDao articlesDao;
    List<Articles> favArticles;
    LiveData<List<Articles>> allArticles;

    public LiveData<List<Articles>> getAllArticles() {
        return allArticles;
    }

    public ArticlesRepository(Application application) {
        ArticlesDatabase articlesDatabase = ArticlesDatabase.getInstance(application);
        articlesDao = articlesDatabase.articlesDao();
        favArticles = articlesDao.getFavouriteArticles();
        allArticles = articlesDao.getAllFavouriteArticles();
    }

    public void insertArticle(Articles articles) {
        articlesDao.insertArticle(articles);
    }

    public List<Articles> getFavouriteArticles() {
        return favArticles;
    }

    public void deleteFavourites() {
        articlesDao.deleteFavourites();
    }

    public void deleteArticleByTitle(String title) {
        articlesDao.deleteArticleByTitle(title);
    }

}
