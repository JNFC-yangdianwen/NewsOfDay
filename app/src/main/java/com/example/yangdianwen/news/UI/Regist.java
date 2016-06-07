package com.example.yangdianwen.news.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yangdianwen.news.R;

/**
 * Created by yangdianwen on 2016/6/7.
 */
public class Regist extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Regist";
    public static final String PWD = "pwd";
    private Button mBtn_reg;
    private EditText mEdt_regist_mail;
    private EditText mEdt_regist_user;
    private EditText mEdt_regist_psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist);
        mEdt_regist_mail = (EditText) findViewById(R.id.et_reg_mail);
        mEdt_regist_user = (EditText) findViewById(R.id.et_reg_user);
        mEdt_regist_psw = (EditText) findViewById(R.id.et_reg_psw);
        mBtn_reg = (Button) findViewById(R.id.btn_reg);
        mBtn_reg.setOnClickListener(this);
    }
    /**
     * 1. 如果用户名为空，弹toast提示用户，否则进入密码判断
     * 2. 如果第一行密码为空则提示密码不能为空，
     *      或者密码长度不在6到16字符，提示用户长度不符合规定
     * 3.确认两次密码是否匹配
     */

    @Override
    public void onClick(View v) {
        String mail = mEdt_regist_mail.getText().toString().trim();
        String user_name=mEdt_regist_user.getText().toString().trim();
        String psw = mEdt_regist_psw.getText().toString().trim();
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(Regist.this, "请输入邮箱名", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "onClick:。。。。。。。 开始匹配");
        String reg="[1-9][0-9]{4,10}@[\\w]{2,7}.com";
        boolean matches = mail.matches(reg);
        if (matches){
            Toast.makeText(Regist.this, "邮箱正确", Toast.LENGTH_SHORT).show();
            return;
        }if (!matches){
            Toast.makeText(Regist.this, "邮箱错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user_name)) {
            Toast.makeText(Regist.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(psw)&&(psw.length()<6||psw.length()>16)){
            Toast.makeText(Regist.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        }
    }

