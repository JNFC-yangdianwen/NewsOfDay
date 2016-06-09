package com.example.yangdianwen.news.UI;

import android.util.Log;

/**
 * Created by yangdianwen on 2016/6/7.
 */
public class MatchAccont {
    private static final String TAG = "MatchAccont";
    public static final String REG = "[1-9][0-9]{4,10}@[\\w]{2,7}.com";
    public static void matchAccount(String user,String pwd){
        if (user==null){
            Log.d(TAG, "matchAccount:。。。。。。 用户名不能为空");
        }
         if (pwd==null){
             Log.d(TAG, "matchAccount: ...............密码不能为空");
         }
    }
}
