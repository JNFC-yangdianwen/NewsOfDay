package com.example.yangdianwen.news.WebUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.yangdianwen.news.R;

public class WebQQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_qq);
        //QQ登陆界面
        WebView webView=new WebView(this);
        //
        webView.getSettings().getJavaScriptEnabled();
        webView.loadUrl("http://connect.qq.com");
        setContentView(webView);
    }
}
