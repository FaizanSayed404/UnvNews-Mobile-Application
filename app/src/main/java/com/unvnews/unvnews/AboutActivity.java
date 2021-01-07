package com.unvnews.unvnews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.unvnews.unvnews.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {
    ActivityAboutBinding aboutBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(aboutBinding.getRoot());
        aboutBinding.toolbarAbout.setNavigationOnClickListener(v -> finish());
    }
}