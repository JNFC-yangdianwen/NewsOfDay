package com.example.yangdianwen.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ArrayList<View> mList = new ArrayList<>();
    private ViewPager mVp;
    int pic[] = {R.drawable.welcome, R.drawable.wy, R.drawable.bd, R.drawable.small};
    ImageView[] points = new ImageView[4];
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn_jump);
        //判断是否为第一次打开应用，使用 SharedPreferences存储状态
        SharedPreferences preferences = getSharedPreferences("runconfig", MODE_PRIVATE);
        //定义标记，为true
        boolean isFistRun = preferences.getBoolean("isFistRun", true);
        //如果不是第一次运行，则调到主界面
        if (!isFistRun) {
            //创建跳转意图,并开启
            Intent intent = new Intent(MainActivity.this, Second.class);
            startActivity(intent);
            finish();
            return;
        }
        //判断ViewPager显示的是第几张图片，根据id来设置小图标的颜色，
        points[0] = (ImageView) findViewById(R.id.iv0);
        points[1] = (ImageView) findViewById(R.id.iv1);
        points[2] = (ImageView) findViewById(R.id.iv2);
        points[3] = (ImageView) findViewById(R.id.iv3);
        setPoint(0);


        //遍历图片资源，并把数据添加到list集合中
        mVp = (ViewPager) findViewById(R.id.vp);
        for (int i = 0; i < pic.length; i++) {
            //创建ImageView的对象，调用setImageResource方法设置图片
            ImageView iv = new ImageView(this);
            iv.setImageResource(pic[i]);
            mList.add(iv);
        }
        //给viewpager设置数据setAdapter，把图片数据添加进来
        mVp.setAdapter(new MyAdapter(mList));
        //viewpager绑定监听
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //viewPager的滑动监听
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }
            //当页面被选中时，来改变底部图标的状态
            @Override
            public void onPageSelected(int position) {
                setPoint(position);
                if (position==3){
                mButton.setVisibility(View.VISIBLE);}else
                {
                    mButton.setVisibility(View.INVISIBLE);
                }
                //如果poistion大于3，则说明引导页面已到底部，判断是否第一次运行的标记置为false
                if (position > 3) {
                    //创建跳转意图,并开启
                    Intent intent = new Intent(MainActivity.this, Second.class);
                    startActivity(intent);
                    finish();
                    //使用 SharedPreferences保存标记，并修改
                    SharedPreferences preferences = getSharedPreferences("runconfig", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();
                }
            }
            //当页面的滑动状态改变时的方法
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //设置底部小图标
    public void setPoint(int index) {
        for (int i = 0; i < points.length; i++) {
            if (i == index) {
                points[i].setAlpha(255);
            } else {
                points[i].setAlpha(100);
            }
        }
    }

    public void next(View view) {
        Intent intent = new Intent(MainActivity.this, Second.class);
        startActivity(intent);
        finish();
    }

    //自定义ViewPager适配器继承 PagerAdapter，重写getCount，instantiateItem，destroyItem，isViewFromObject，几个方法
    private class MyAdapter extends PagerAdapter {
        private ArrayList<View> mlist;

        public MyAdapter(ArrayList<View> mlist) {
            this.mlist = mlist;
        }

        //获取数据的长度
        @Override
        public int getCount() {
            if (mlist != null) {
                return mlist.size();
            }
            return 0;
        }

        //把数据加到视图container容器中
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mlist.get(position), null);
            return mlist.get(position);
        }

        //销毁Item
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mlist.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
