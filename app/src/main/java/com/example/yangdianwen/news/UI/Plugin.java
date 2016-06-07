package com.example.yangdianwen.news.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yangdianwen.news.R;

/**
 * Created by yangdianwen on 2016/6/7.
 */
public class Plugin extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn_zhuce;
    private Button mBtn_froget;
    private Button mBtn_plugin;
    private EditText mEt_username;
    private EditText mEt_psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin);
        mBtn_zhuce = (Button) findViewById(R.id.btn_zhuce);
        mBtn_froget = (Button) findViewById(R.id.btn_forget);
        mBtn_plugin = (Button) findViewById(R.id.btn_plugin);
        mEt_username = (EditText) findViewById(R.id.et_ueser);
        mEt_psw = (EditText) findViewById(R.id.et_psw);
        mBtn_zhuce.setOnClickListener(this);
        mBtn_froget.setOnClickListener(this);
        mBtn_plugin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id. btn_zhuce:
                Intent intent =new Intent(this,Regist.class);
                startActivity(intent);
                break;
            case R.id. btn_forget:
                Toast.makeText(Plugin.this, "忘记密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id. btn_plugin:
                Toast.makeText(Plugin.this, "登陆", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}