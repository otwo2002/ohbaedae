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

public class EventFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewGroup rootView ;
    WebView webView ;
    FragmentManager manager;

    public EventFragment() {

    }

    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
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

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_event, container, false);
        webView = rootView.findViewById(R.id.webView);

        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDisplayZoomControls(true);
        settings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient()); //이걸안해주면 새창이 뜸
        webView.setWebChromeClient(new WebChromeClient());
        final Button mallButton = rootView.findViewById(R.id.btnMalltail);
        final Button nygirlzButton = rootView.findViewById(R.id.btnNygirlz);
        final Button iporterButton = rootView.findViewById(R.id.btnIporter);
        final Button yogirlooButton = rootView.findViewById(R.id.btnYogirloo);
        //최초 색상지정
        mallButton.setBackgroundColor(Color.DKGRAY);
        nygirlzButton.setBackgroundColor(Color.GRAY);
        iporterButton.setBackgroundColor(Color.GRAY);
        yogirlooButton.setBackgroundColor(Color.GRAY);
        webView.loadUrl("http://post.malltail.com/eventservices/open_events");
        mallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mallButton.setTextColor();

                webView.loadUrl("http://post.malltail.com/eventservices/open_events");
                mallButton.setBackgroundColor(Color.DKGRAY);
                nygirlzButton.setBackgroundColor(Color.GRAY);
                iporterButton.setBackgroundColor(Color.GRAY);
                yogirlooButton.setBackgroundColor(Color.GRAY);
            }
        });
        nygirlzButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.loadUrl("http://www.nygirlz.co.kr/mobile/event/dailyattend1803.php");
                mallButton.setBackgroundColor(Color.GRAY);
                nygirlzButton.setBackgroundColor(Color.DKGRAY);
                iporterButton.setBackgroundColor(Color.GRAY);
                yogirlooButton.setBackgroundColor(Color.GRAY);
            }
        });
        iporterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.loadUrl("https://m.iporter.com/ko/event");
                mallButton.setBackgroundColor(Color.GRAY);
                nygirlzButton.setBackgroundColor(Color.GRAY);
                iporterButton.setBackgroundColor(Color.DKGRAY);
                yogirlooButton.setBackgroundColor(Color.GRAY);

            }
        });
        yogirlooButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                webView.loadUrl("http://www.yogirloo.com/m/board/event/list.asp");
                mallButton.setBackgroundColor(Color.GRAY);
                nygirlzButton.setBackgroundColor(Color.GRAY);
                iporterButton.setBackgroundColor(Color.GRAY);
                yogirlooButton.setBackgroundColor(Color.DKGRAY);
            }
        });
        return rootView;
    }
}