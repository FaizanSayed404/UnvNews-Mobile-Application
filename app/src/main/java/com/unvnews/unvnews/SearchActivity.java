package com.unvnews.unvnews;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

public class SearchActivity extends AppCompatActivity implements OnReadLaterClickedListener {
    ActivitySearchBinding binding;
    MyAdapter adapter;
    List<Articles> articles = new ArrayList<>();
    Retrofit retrofit;
    String query;
    ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ArticleViewModel.class);
        adapter = new MyAdapter();
        adapter.setActivityName("SearchActivity");
        adapter.setOnReadLaterClickedListener(this);
        binding.toolbarSearch.setNavigationOnClickListener(v -> finish());
        binding.searchButton.setOnClickListener(v -> {
            query = binding.searchEditText.getText().toString();
            searchNews();
        });
        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query = binding.searchEditText.getText().toString();
                searchNews();
            }
            return true;
        });
    }

    private void searchNews() {
        if (query.isEmpty()) {
            Snackbar snackbar = Snackbar.make(binding.searchRecyclerView, "Text field cannot be empty",
                    BaseTransientBottomBar.LENGTH_SHORT);
            snackbar.show();
        } else {
            binding.searchProgressBar.setVisibility(View.VISIBLE);
            LoadSearchedNews(query);
        }
    }

    void LoadSearchedNews(String query) {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<News> call = apiInterface.getArticlesByQuery(query, Constants.API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.body() != null) {
                    articles = response.body().getArticles();
                    adapter.setList(articles);
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

    @Override
    public void onReadLaterClicked(int pos) {
        Articles clickedArticle = articles.get(pos);
        viewModel.insertArticle(clickedArticle);
        Toast.makeText(SearchActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
    }
}