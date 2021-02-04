package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.unvnews.unvnews.databinding.WebViewBinding;

public class BrowseWeb extends AppCompatActivity {
    WebViewBinding webViewBinding;
    String url;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewBinding = WebViewBinding.inflate(getLayoutInflater());
        setContentView(webViewBinding.getRoot());
        url = getIntent().getExtras().getString("URL");
        webViewBinding.webView.setWebViewClient(new WebViewClient());
        webViewBinding.webView.loadUrl(url);
        WebSettings webSettings = webViewBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webViewBinding.webView.canGoBack())
        {
            webViewBinding.webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

}
