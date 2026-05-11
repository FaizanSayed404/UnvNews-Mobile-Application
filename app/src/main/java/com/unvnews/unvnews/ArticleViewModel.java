package com.unvnews.unvnews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {
    ArticlesRepository articlesRepository;
    List<Articles> favArticles;
    LiveData<List<Articles>> allArticles;

    public ArticleViewModel(@NonNull @NotNull Application application) {
        super(application);
        articlesRepository = new ArticlesRepository(application);
        favArticles = articlesRepository.getFavouriteArticles();
        allArticles = articlesRepository.getAllArticles();
    }
    public void insertArticle(Articles articles) {
        articlesRepository.insertArticle(articles);
    }

    public List<Articles> getFavouriteArticles() {
        return favArticles;
    }

    public void deleteFavourites() {
        articlesRepository.deleteFavourites();
    }

    public void deleteArticleByTitle(String title) {
        articlesRepository.deleteArticleByTitle(title);
    }
    public LiveData<List<Articles>> getAllArticles() {
        return allArticles;
    }
}
