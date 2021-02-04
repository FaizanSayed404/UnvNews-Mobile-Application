package com.unvnews.unvnews;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.unvnews.unvnews.databinding.ActivitySearchBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    MyAdapter adapter;
    List<Articles> articles = new ArrayList<>();
    Retrofit retrofit;
    String query;
    Models models = new Models();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbarSearch.setNavigationOnClickListener(v -> finish());
        binding.searchButton.setOnClickListener(v -> {
            query = binding.searchEditText.getText().toString();
            if (query.equals("")) {
                Snackbar snackbar = Snackbar.make(binding.searchRecyclerView, "Text field cannot be empty",
                        BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            } else {
                binding.searchProgressBar.setVisibility(View.VISIBLE);
                LoadSearchedNews();
            }
        });
    }

    void LoadSearchedNews() {
        retrofit = new Retrofit.Builder()
                .baseUrl(models.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<News> call = apiInterface.getArticlesByQuery(query, models.getAPI_KEY());
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.body() != null) {
                    articles = response.body().getArticles();
                    adapter = new MyAdapter(SearchActivity.this, articles);
                    binding.searchRecyclerView.setAdapter(adapter);
                    binding.searchProgressBar.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {

            }
        });
    }
}