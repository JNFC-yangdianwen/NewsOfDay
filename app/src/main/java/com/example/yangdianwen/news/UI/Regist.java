package com.example.yangdianwen.news.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yangdianwen.news.R;

/**
 * Created by yangdianwen on 2016/6/7.
 */
public class Regist extends AppCompatActivity implements View.OnClickListener {
    private String USER = "user";
    private static final String TAG = "Regist";
    public static final String PWD = "pwd";
    public static final String MAIL = "mail";
    public static final String USERNAME = "username";
    private Button mBtn_reg;
    private EditText mEdt_regist_mail;
    private EditText mEdt_regist_user;
    private EditText mEdt_regist_psw;
    public static final String REG = "[A-Za-z1-9][A-Za-z0-9]{4,10}@[a-z]{2,7}.com";


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
     * 1. 如果邮箱名为空，弹toast提示用户，否则进入邮箱匹配阶段，使用正则匹配
     * 邮箱匹配成功则进入下一步
     * 2. 如果用户名为空，则提示用户不能为空，当用户名输入完之后则进入用户名的匹配中，
     * 去数据库中查询用户名是否存在，也就是遍历数据库中所有的用户名数据，如果用户名和数据库中的有相同的则提示用户，
     * 该用户名已注册
     * 3.输入密码的校验，判断密码长度是否符合规定
     * 4.如果前三项验证通过则进行用户信息的保存，使用 SharedPreferences
     */
    @Override
    public void onClick(View v) {
        String mail = mEdt_regist_mail.getText().toString().trim();
        String user_name = mEdt_regist_user.getText().toString().trim();
        String psw = mEdt_regist_psw.getText().toString().trim();
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(Regist.this, "请输入邮箱名", Toast.LENGTH_SHORT).show();
            return;
        }
        //匹配邮箱
        boolean matches = mail.matches(REG);
        if (!matches) {
            Toast.makeText(Regist.this, "邮箱错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(user_name)) {
            Toast.makeText(Regist.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (psw.length() < 6 || psw.length() > 16) {
            Toast.makeText(Regist.this, "密码不符合规定", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
