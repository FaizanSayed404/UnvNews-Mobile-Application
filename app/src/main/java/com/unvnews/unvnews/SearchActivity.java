package com.unvnews.unvnews;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    List<Articles> articles = new ArrayList<>();
    Retrofit retrofit;
    EditText searchEditText;
    Button searchButton;
    String query;
    ProgressBar searchProgressBar;
    News news = new News();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.searchRecyclerView);
        toolbar = findViewById(R.id.toolbarSearch);
        searchProgressBar = findViewById(R.id.searchProgressBar);
        toolbar.setNavigationOnClickListener(v -> finish());
        searchButton.setOnClickListener(v -> {
                searchProgressBar.setVisibility(View.VISIBLE);
                LoadSearchedNews();
        });
    }

    void LoadSearchedNews(){
        query = searchEditText.getText().toString();
        retrofit = new Retrofit.Builder()
                .baseUrl(news.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<News> call = apiInterface.getArticlesByQuery(query, news.API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                searchProgressBar.setVisibility(View.INVISIBLE);
                if(response.body() != null)
                {
                    searchProgressBar.setVisibility(View.GONE);
                    articles = response.body().getArticles();
                    searchAdapter = new SearchAdapter(SearchActivity.this,articles);
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
}