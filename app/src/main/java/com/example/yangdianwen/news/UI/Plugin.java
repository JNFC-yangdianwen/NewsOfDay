package com.example.yangdianwen.news.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yangdianwen.news.R;

/**
 * Created by yangdianwen on 2016/6/7.
 */
public class Plugin extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Plugin";
    private Button mBtn_regist;
    private Button mBtn_froget;
    private Button mBtn_plugin;
    private EditText mEt_username;
    private EditText mEt_psw;
    private String mUser_name;
    private String mPsw;
    private ImageView mIv_home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin);
        initView();
        initEvents();

    }



    //初始化视图
    private void initView() {
        mIv_home = (ImageView) findViewById(R.id.iv_home);
        mBtn_regist = (Button) findViewById(R.id.btn_regist);
        mBtn_froget = (Button) findViewById(R.id.btn_forgetPassword);
        mBtn_plugin = (Button) findViewById(R.id.btn_plugin);
        mEt_username = (EditText) findViewById(R.id.et_username);
        mEt_psw = (EditText) findViewById(R.id.et_password);

    }
    //初始化事件
    private void initEvents() {
        mBtn_regist.setOnClickListener(this);
        mBtn_froget.setOnClickListener(this);
        mBtn_plugin.setOnClickListener(this);
        mIv_home.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        mUser_name = mEt_username.getText().toString().trim();
        mPsw = mEt_psw.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_home:
                Intent intent_toHome = new Intent(this,Home.class);
                startActivity(intent_toHome);
                finish();
                break;
            case R.id.btn_regist:
                Intent intent = new Intent(this, Regist.class);
                startActivity(intent);
                break;
            case R.id.btn_forgetPassword:
               Intent intent_findpsw=new Intent(this,ForgetAccount.class);
                startActivity(intent_findpsw);
                break;
            case R.id.btn_plugin:
                SharedPreferences sharedPreferences = getSharedPreferences("", MODE_PRIVATE);
                String use_name = sharedPreferences.getString(Regist.USERNAME, mUser_name);
                String psw = sharedPreferences.getString(Regist.PWD, mPsw);
                if (TextUtils.isEmpty(mUser_name)) {
                    Toast.makeText(Plugin.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(mUser_name) && mUser_name.equals(use_name) && mPsw.equals(psw)) {
                    Intent intent1 = new Intent(this, UserUI.class);
                    startActivity(intent1);
                    return;
                }
                if (!mPsw.equals(psw)) {
                    Toast.makeText(Plugin.this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;

        }
    }
}