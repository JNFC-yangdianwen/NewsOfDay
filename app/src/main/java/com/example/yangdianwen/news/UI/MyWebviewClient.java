package com.example.yangdianwen.news.UI;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yangdianwen on 2016/6/1.
 */
public class MyWebviewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return  true;
    }
}
