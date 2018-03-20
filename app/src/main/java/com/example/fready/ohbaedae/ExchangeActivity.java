package com.example.fready.ohbaedae;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ExchangeActivity extends AppCompatActivity {

    WebView webView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#267CB5")));
            getSupportActionBar().setTitle("환율조회");
        }

        webView = findViewById(R.id.webView);

        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(true);
        settings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient()); //이걸안해주면 새창이 뜸
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://m.stock.naver.com/marketindex/index.nhn?menu=exchange");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
