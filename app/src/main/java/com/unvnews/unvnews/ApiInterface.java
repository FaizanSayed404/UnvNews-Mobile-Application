package com.unvnews.unvnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("User-Agent: UnvNewsApp")
    @GET("top-headlines")
    Call<News> getArticle(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @Headers("User-Agent: UnvNewsApp")
    @GET("top-headlines")
    Call<News> getArticleByCategory(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    @Headers("User-Agent: UnvNewsApp")
    @GET("everything")
    Call<News> getArticlesByQuery(
            @Query("q") String query,
            @Query("apiKey") String apiKey

    );
}
