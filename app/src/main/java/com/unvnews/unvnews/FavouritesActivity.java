package com.unvnews.unvnews;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.unvnews.unvnews.databinding.ActivityFavouritesBinding;

import java.util.List;

public class FavouritesActivity extends AppCompatActivity implements OnRemoveButtonClickedListener {
    ActivityFavouritesBinding binding;
    MyAdapter adapter;
    ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ArticleViewModel.class);
        viewModel.getAllArticles().observe(this, articles -> {
            adapter = new MyAdapter();
            adapter.setOnRemoveButtonClickedListener(FavouritesActivity.this);
            adapter.setActivityName("FavouritesActivity");
            adapter.setList(articles);
            binding.recyclerViewfavourites.setAdapter(adapter);
        });
    }

    public void backButton(View view) {
        finish();
    }

    public void removeAllFavourites(View view) {
        List<Articles> allArticles = viewModel.getFavouriteArticles();
        viewModel.deleteFavourites();
        Snackbar.make(binding.recyclerViewfavourites, "All Articles Deleted", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("Undo", v -> {
                    for (int i = 0; i < allArticles.size() - 1; i++) {
                        viewModel.insertArticle(allArticles.get(i));
                    }
                }).show();
    }

    @Override
    public void onRemoveButtonClicked(String title, int position) {
        Articles clicked_article = viewModel.getFavouriteArticles().get(position);
        viewModel.deleteArticleByTitle(title);
        Snackbar.make(binding.recyclerViewfavourites, "Article Deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.insertArticle(clicked_article);
                    }
                }).show();
    }
}