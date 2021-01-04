package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.pager);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setupViewPager(viewPager);
        NavigationView navigationView = findViewById(R.id.Category_NavView);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        navigationView.setNavigationItemSelectedListener(item -> {

           if (item.getItemId() == item.getItemId() && !item.getTitle().equals("About"))
           {
               Intent intent = new Intent(MainActivity.this,CategoryPage.class);
               intent.putExtra("TITLE",item.getTitle());
               startActivity(intent);
               drawerLayout.closeDrawer(GravityCompat.START);
           }
           else
           {
               Intent intent = new Intent(MainActivity.this,AboutActivity.class);
               startActivity(intent);
               drawerLayout.closeDrawer(GravityCompat.START);
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
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShowNews(),"Top Headlines");
//        adapter.addFragment(new SportsFragment(),"This Week");
        viewPager.setAdapter(adapter);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure You Want to Exit")
                .setTitle("Exit")
                .setCancelable(true)
                .setPositiveButton("Yes",(dialog, which) -> {
                    this.finish();
                }).setNegativeButton("No",(dialog, which) -> {
                    dialog.cancel();
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}