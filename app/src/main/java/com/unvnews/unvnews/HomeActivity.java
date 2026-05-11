package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.annotations.NotNull;
import com.unvnews.unvnews.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements ArticleInsertListener, OnReadLaterClickedListener {
    ActivityHomeBinding binding;
    MyAdapter adapter;
    Retrofit retrofit;
    List<Constants> constantsList;
    ArticleViewModel viewModel;
    ArticleInsertListener articleInsertListener;
    List<Articles> articles;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        articleInsertListener = this;
        adapter = new MyAdapter();
        adapter.setActivityName("HomeActivity");
        adapter.setOnReadLaterClickedListener(this);
        constantsList = new ArrayList<>();
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.homeProgressBar.bringToFront();
        binding.homeProgressBar.setVisibility(View.VISIBLE);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ArticleViewModel.class);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        binding.homeRecyclerView.addItemDecoration(decoration);
        articles = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<News> call = apiInterface.getArticle(Constants.COUNTRY, Constants.API_KEY);
        call.enqueue(new Callback<News>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                binding.homeProgressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    // Get the list directly from the body
                    List<Articles> remoteArticles = response.body().getArticles();

                    if (remoteArticles != null && !remoteArticles.isEmpty()) {
                        // Update your local list and the adapter
                        articles.clear();
                        articles.addAll(remoteArticles);

                        adapter.setList(articles);
                        binding.homeRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        Log.d("UNV_DEBUG", "Articles successfully parsed: " + articles.size());
                    } else {
                        Log.d("UNV_DEBUG", "JSON parsed but list is empty. Check API Key/Country.");
                        Toast.makeText(HomeActivity.this, "No news found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("UNV_DEBUG", "Server Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
                binding.homeProgressBar.setVisibility(View.INVISIBLE); // Add this line!
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        NavigationView navigationView = findViewById(R.id.home_navView);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == item.getItemId() && !item.getTitle().equals("About") && !item.getTitle().equals("Exit") && !item.getTitle().equals("Read Later")) {
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, CategoryPage.class);
                intent.putExtra("TITLE", item.getTitle());
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == item.getItemId() && !item.getTitle().equals("Exit") && !item.getTitle().equals("Exit") && !item.getTitle().equals("Read Later")) {
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, AboutActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.menuFav) {
                Intent intent = new Intent(this,FavouritesActivity.class);
                startActivity(intent);
            } else {
                showDialog();
            }
            return true;
        });

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            int id = item.getItemId();
            if (id == R.id.toolbarSearch) {
                intent.setClass(HomeActivity.this, SearchActivity.class);
            } else if (id == R.id.toolbarAbout) {
                intent.setClass(HomeActivity.this, AboutActivity.class);
            }
            startActivity(intent);
            return true;
        });
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setMessage("Are You Sure You Want to Exit")
                .setTitle("Exit")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, which) -> this.finish()).setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    @Override
    public void onArticleInserted(List<Articles> articles) {
        this.articles = articles;
    }

    @Override
    public void onReadLaterClicked(int pos) {
        Articles clickedArticle = articles.get(pos);
        viewModel.insertArticle(clickedArticle);
        Toast.makeText(HomeActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
    }
}