package com.unvnews.unvnews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unvnews.unvnews.databinding.SplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    SplashScreenBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().getDecorView().setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
        binding.splashAnimation.playAnimation();
        int SPLASH_SCREEN_TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            Intent i=new Intent(SplashScreen.this,
                    MainActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
