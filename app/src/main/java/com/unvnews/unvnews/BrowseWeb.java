package com.unvnews.unvnews;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import org.adblockplus.libadblockplus.android.webview.AdblockWebView;

public class BrowseWeb extends AppCompatActivity {
    AdblockWebView adblockWebView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        Bundle extra = getIntent().getExtras();
        assert extra != null;
        url = extra.getString("URL");
        adblockWebView = findViewById(R.id.web_view);
        adblockWebView.setWebViewClient(new WebViewClient());
        adblockWebView.loadUrl(url);
        WebSettings webSettings = adblockWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (adblockWebView.canGoBack())
        {
            adblockWebView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

}
