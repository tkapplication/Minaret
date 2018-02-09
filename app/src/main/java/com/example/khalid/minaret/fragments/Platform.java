package com.example.khalid.minaret.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.khalid.minaret.R;

import static com.example.khalid.minaret.activities.MainActivity.title;

/**
 * Created by khalid on 1/15/2018.
 */

public class Platform extends Fragment {

    public static Platform newInstance() {
        Platform news = new Platform();
        return news;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.platform, container, false);
        title.setText("المنصة");

        WebView webView = view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://minaretproject.com");
        webView.setHorizontalScrollBarEnabled(false);
        return view;
    }
}
