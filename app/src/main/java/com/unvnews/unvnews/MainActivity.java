package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.Objects;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.pager);
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            switch (item.getItemId())
            {
                case R.id.toolbarSettings:
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
        adapter.addFragment(new SportsFragment(),"Sports");
        adapter.addFragment(new EntertainmentFragment(),"Entertainment");
        adapter.addFragment(new TechnologyFragment(),"Technology");
        adapter.addFragment(new HealthFragment(),"Health");
        adapter.addFragment(new BusinessFragment(),"Business");
        adapter.addFragment(new ScienceFragment(),"Science");
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

    }