package com.example.yangdianwen.news.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.yangdianwen.news.R;

public class logoUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //设置动画效果
        ImageView lead_logo = (ImageView) findViewById(R.id.iv_logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        lead_logo.setAnimation(animation);
        //动画设置监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
        //动画结束时的逻辑操作
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent=new Intent(logoUI.this,Home.class);
                startActivity(intent);
//                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
