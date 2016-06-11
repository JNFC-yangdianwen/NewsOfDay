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
    public static final String USER = "user";
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
        SharedPreferences sp=getSharedPreferences(USER,MODE_PRIVATE);
        mUser_name=sp.getString(Regist.USERNAME,mUser_name);

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
                //回到主页
                Intent intent_toHome = new Intent(this,Home.class);
                startActivity(intent_toHome);
                finish();
                break;
            case R.id.btn_regist:
                //进入注册界面
                Intent intent = new Intent(this, Regist.class);
                startActivity(intent);
                break;
            case R.id.btn_forgetPassword:
                //进入找回密码的界面
               Intent intent_findpsw=new Intent(this,ForgetAccount.class);
                startActivity(intent_findpsw);
                break;
            case R.id.btn_plugin:
                //1 当点击登陆按键时则去用户信息数据库中寻找对应的账户名和对应的密码，
                // 2 如果用户名不存在则提示该用户账号不存在，如果密码错误则提示用户密码错误
                // 3 如果匹配成功则进入用户登录状态
                SharedPreferences sharedPreferences = getSharedPreferences(USER, MODE_PRIVATE);
                String use_name = sharedPreferences.getString(Regist.USERNAME, mUser_name);
                String psw = sharedPreferences.getString(Regist.PWD, mPsw);
                if (TextUtils.isEmpty(mUser_name)) {
                    Toast.makeText(Plugin.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mUser_name.equals(use_name)){
                    Toast.makeText(Plugin.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mUser_name.equals(use_name) && mPsw.equals(psw)) {
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