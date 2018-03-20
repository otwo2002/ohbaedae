package com.example.fready.ohbaedae;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class KeyActivity extends AppCompatActivity {
    // TODO: Rename and change types of parameters

    WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#267CB5")));
            getSupportActionBar().setTitle("관세청 개인통관고유부호");
        }

        webView = findViewById(R.id.webView);

        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(true);
        settings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient()); //이걸안해주면 새창이 뜸
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://m.customs.go.kr/pms/html/mos/extr/MOS0101053S.do");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
