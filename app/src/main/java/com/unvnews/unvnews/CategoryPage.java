package com.unvnews.unvnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryPage extends AppCompatActivity {
    Retrofit retrofit;
    RecyclerView recyclerView;
    MyAdapter adapter;
    public String BASE_URL = "https://newsapi.org/v2/";
    public String API_KEY = "87ceb7c136aa462eaf4a1b206ff162e6";
    public String COUNTRY = "in";
    TextView textViewTitle;
    MaterialToolbar materialToolbar;
    List<Articles> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
//        textViewTitle = findViewById(R.id.catHeaderTextView);
        Bundle extra = getIntent().getExtras();
        String title = extra.getString("TITLE");
        articles = new ArrayList<>();
        materialToolbar = findViewById(R.id.materialToolbarCategory);
//        textViewTitle.setText(title);
        recyclerView = findViewById(R.id.category_recView);
        materialToolbar.setTitle(title);
        materialToolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
               if (item.getItemId() == R.id.toolbarAbout)
               {
                   intent = new Intent(CategoryPage.this,AboutActivity.class);
               }
               else
               {
                   intent = new Intent(CategoryPage.this,SearchActivity.class);
               }
                startActivity(intent);
                return true;
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<News> call ;

        switch (title)
        {
            case "Top Headlines" : call = apiInterface.getArticle(COUNTRY,API_KEY);
            break;

            case "Sports" : call = apiInterface.getArticleByCategory(COUNTRY,"sports",API_KEY);
            break;

            case "Entertainment" : call = apiInterface.getArticleByCategory(COUNTRY,"entertainment",API_KEY);
            break;

            case "Technology" : call = apiInterface.getArticleByCategory(COUNTRY,"technology",API_KEY);
            break;

            case "Health" : call = apiInterface.getArticleByCategory(COUNTRY,"health",API_KEY);
            break;

            case "Business" : call = apiInterface.getArticleByCategory(COUNTRY,"business",API_KEY);
            break;

            case "Science" : call = apiInterface.getArticleByCategory(COUNTRY,"science",API_KEY);
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + title);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.body() != null) {
                    articles = response.body().getArticles();
                }
                adapter = new MyAdapter(CategoryPage.this,articles);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
            }
        });


    }
}