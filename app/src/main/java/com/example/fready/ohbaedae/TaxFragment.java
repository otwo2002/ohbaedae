package com.example.fready.ohbaedae;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Created by fready on 2018-03-16.
 */

public class TaxFragment  extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewGroup rootView ;
    WebView webView ;
    FragmentManager manager;

    public TaxFragment() {

    }

    public static TaxFragment newInstance(String param1, String param2) {
        TaxFragment fragment = new TaxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tax, container, false);
        webView = rootView.findViewById(R.id.webView);

        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(true);
        settings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient()); //이걸안해주면 새창이 뜸
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl("https://search.naver.com/search.naver?sm=tab_sug.top&where=nexearch&query=%EA%B4%80%EB%B6%80%EA%B0%80%EC%84%B8%EA%B3%84%EC%82%B0%EA%B8%B0&oquery=%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C+%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4+json%EC%B6%94%EA%B0%80&tqi=TqqRZdpVuFdsst3UzdossssstWK-451046&acq=%EA%B4%80%EB%B6%80&acr=1&qdt=0");

        return rootView;
    }
}
