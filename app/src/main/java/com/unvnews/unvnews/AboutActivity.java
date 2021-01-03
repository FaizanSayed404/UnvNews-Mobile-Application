package com.unvnews.unvnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class AboutActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = findViewById(R.id.toolbarAbout);
        textView = findViewById(R.id.textViewEmailAddress);
        toolbar.setNavigationOnClickListener(v -> finish());
        textView.setOnClickListener(v -> {
            Intent intent = new Intent (Intent.ACTION_SENDTO , Uri.parse("mailto:" + textView.getText().toString()));
            startActivity(intent);
        });
    }
}