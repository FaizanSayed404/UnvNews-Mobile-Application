package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Articles> articles;
    Retrofit retrofit;
    Models models = new Models();
    ProgressBar progressBar;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.home_progressBar);
        recyclerView = findViewById(R.id.homeRecyclerView);
        progressBar.setVisibility(View.VISIBLE);
        articles = new ArrayList<>();
        retrofit = new Retrofit.Builder()
                .baseUrl(models.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<News> call = apiInterface.getArticle(models.getCOUNTRY(),models.getAPI_KEY());

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.body() != null) {
                    articles = response.body().getArticles();
                    adapter = new MyAdapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });


        builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setMessage("Are You Sure You Want to Exit")
                .setTitle("Exit")
                .setCancelable(true)
                .setPositiveButton("Yes",(dialog, which) -> {
                    this.finish();
                }).setNegativeButton("No",(dialog, which) -> {
            dialog.cancel();
        });
        alertDialog = builder.create();
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        NavigationView navigationView = findViewById(R.id.Category_NavView);
        navigationView.setNavigationItemSelectedListener(item -> {

           if (item.getItemId() == item.getItemId() && !item.getTitle().equals("About") && !item.getTitle().equals("Exit"))
           {
               Intent intent = new Intent(MainActivity.this,CategoryPage.class);
               intent.putExtra("TITLE",item.getTitle());
               startActivity(intent);
               drawerLayout.closeDrawer(GravityCompat.START);
           }
           else if (item.getItemId()==item.getItemId() && !item.getTitle().equals("Exit"))
           {
               Intent intent = new Intent(MainActivity.this,AboutActivity.class);
               startActivity(intent);
               drawerLayout.closeDrawer(GravityCompat.START);
           }
           else{
               alertDialog.show();
           }
            return true;
        });

        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            switch (item.getItemId())
            {
                case R.id.toolbarSearch:
                    intent.setClass(MainActivity.this,SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.toolbarAbout:
                    intent.setClass(MainActivity.this,AboutActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearApplicationData();
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(Objects.requireNonNull(cache.getParent()));
        if (appDir.exists()) {
            String[] children = appDir.list();
            assert children != null;
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Timber.i("**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        assert dir != null;
        return dir.delete();
    }

    @Override
    public void onBackPressed() {
        alertDialog.show();
    }
}