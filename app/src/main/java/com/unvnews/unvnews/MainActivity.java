package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.unvnews.unvnews.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MyAdapter adapter;
    List<Articles> articles;
    Retrofit retrofit;
    List<Constants> constantsList;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constantsList = new ArrayList<>();
        binding.homeProgressBar.bringToFront();
        binding.homeProgressBar.setVisibility(View.VISIBLE);
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
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.body() != null) {
                    articles = response.body().getArticles();
                    adapter = new MyAdapter(MainActivity.this, articles);
                    binding.homeRecyclerView.setAdapter(adapter);
                    binding.homeProgressBar.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {

            }
        });
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        NavigationView navigationView = findViewById(R.id.home_navView);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == item.getItemId() && !item.getTitle().equals("About") && !item.getTitle().equals("Exit")) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CategoryPage.class);
                intent.putExtra("TITLE", item.getTitle());
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == item.getItemId() && !item.getTitle().equals("Exit")) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                showDialog();
            }
            return true;
        });

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.toolbarSearch:
                    intent.setClass(MainActivity.this, SearchActivity.class);
                    break;
                case R.id.toolbarAbout:
                    intent.setClass(MainActivity.this, AboutActivity.class);
                    break;
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
}