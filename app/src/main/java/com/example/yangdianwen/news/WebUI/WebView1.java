package com.example.yangdianwen.news.WebUI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.yangdianwen.news.R;
import com.example.yangdianwen.news.UI.Home;
import com.example.yangdianwen.news.UI.MyWebviewClient;

public class WebView1 extends AppCompatActivity {
    private static final String TAG = "WebView1";
    public String url = Home.mLink;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //实例化一个webview
        openWebView();
    }

    private void openWebView() {
        mWebView = new WebView(this);
        //获取webview的JavaScript的脚本
        mWebView.getSettings().getJavaScriptEnabled();
        //加载一个网页，传入一个url路径
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new MyWebviewClient());
        setContentView(mWebView);
    }

    //点击后退webview页面回退到上级页面
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
