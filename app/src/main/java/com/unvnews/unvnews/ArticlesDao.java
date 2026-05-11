package com.unvnews.unvnews;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ArticlesDao {

    @Query("SELECT * FROM articles_table ORDER BY newsId DESC")
    List<Articles> getFavouriteArticles();

    @Query("SELECT * FROM articles_table ORDER BY newsId DESC")
    LiveData<List<Articles>> getAllFavouriteArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Articles articles);

    @Query("DELETE FROM articles_table")
    void deleteFavourites();

    @Query("DELETE FROM articles_table WHERE title LIKE :clicked_title")
    void deleteArticleByTitle(String clicked_title);
}
