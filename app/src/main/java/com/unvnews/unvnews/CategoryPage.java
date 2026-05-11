package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.unvnews.unvnews.databinding.ActivityCategoryPageBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class CategoryPage extends AppCompatActivity implements OnReadLaterClickedListener {
    ActivityCategoryPageBinding binding;
    Retrofit retrofit;
    MyAdapter adapter;
    List<Articles> articles;
    ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extra = getIntent().getExtras();
        String title = extra.getString("TITLE");
        articles = new ArrayList<>();
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ArticleViewModel.class);
        adapter = new MyAdapter();
        adapter.setActivityName("CategoryPage");
        adapter.setOnReadLaterClickedListener(this);
        binding.categoryProgressBar.setVisibility(View.VISIBLE);
        binding.materialToolbarCategory.setTitle(title);
        binding.materialToolbarCategory.setNavigationOnClickListener(v -> finish());
        binding.materialToolbarCategory.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            if (item.getItemId() == R.id.toolbarAbout) {
                intent.setClass(CategoryPage.this, AboutActivity.class);
            } else {
                intent.setClass(CategoryPage.this, SearchActivity.class);
            }
            startActivity(intent);
            return true;
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<News> call;

        switch (title) {
            case "Top Headlines":
                call = apiInterface.getArticle(Constants.COUNTRY, Constants.API_KEY);
                break;

            case "Sports":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "sports", Constants.API_KEY);
                break;

            case "Entertainment":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "entertainment", Constants.API_KEY);
                break;

            case "Technology":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "technology", Constants.API_KEY);
                break;

            case "Health":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "health", Constants.API_KEY);
                break;

            case "Business":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "business", Constants.API_KEY);
                break;

            case "Science":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "science", Constants.API_KEY);
                break;

            case "Read Later":
                Timber.i("onCreate: ");

            default:
                throw new IllegalStateException("Unexpected value: " + title);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                binding.categoryProgressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Articles> remoteArticles = response.body().getArticles();

                    if (remoteArticles != null && !remoteArticles.isEmpty()) {
                        articles = remoteArticles; // Update the list
                        adapter.setList(articles);
                        binding.categoryRecView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CategoryPage.this, "No news found in this category.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // This will tell you if it's still a 403 Forbidden
                    Log.e("UNV_DEBUG", "Category Error: " + response.code());
                    Toast.makeText(CategoryPage.this, "Server Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
                Toast.makeText(CategoryPage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onReadLaterClicked(int pos) {
        Articles clickedArticle = articles.get(pos);
        viewModel.insertArticle(clickedArticle);
        Toast.makeText(CategoryPage.this, "Added Successfully", Toast.LENGTH_SHORT).show();
    }
}