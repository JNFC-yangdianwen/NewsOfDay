package com.example.yangdianwen.news.UI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yangdianwen.news.Adapter.DataAdapter;
import com.example.yangdianwen.news.Bean.GsonBean;
import com.example.yangdianwen.news.R;
import com.example.yangdianwen.news.WebUI.Base_WebView;
import com.example.yangdianwen.news.WebUI.WebQQ;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener,ReFlashListView.IReflashListener{
    private static final String TAG = "Home";
    private ArrayList<GsonBean.Data> mArrayList;
    private ReFlashListView mList_item;
    private TextView mTvShehui;
    private TextView mTv_junshi;
    private ArrayList<GsonBean.Data> mDatas;
    public static String mLink;//网络连接地址
    private SlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
//        initEvents();
    }


    //初始化主页面
    //在主页面中使用AsyncTask获取网络数据
    private void initView() {
        mList_item = (ReFlashListView) findViewById(R.id.lv_list);
        //主页Actionbar中的控件
        ImageView iv_title_main = (ImageView) findViewById(R.id.iv_title_main);
        ImageView iv_share = (ImageView) findViewById(R.id.iv_title_share);
        mTvShehui = (TextView) findViewById(R.id.tv_shehui);
        mTv_junshi = (TextView) findViewById(R.id.tv_junshi);
        //侧拉菜单初始化
       mSlidingMenu=new SlidingMenu(this);
        //侧拉菜单的触摸响应范围
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置侧拉菜单的偏移量
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_width);
        //布置侧拉菜单界面
        mSlidingMenu.setMenu(R.layout.sliding_menu_type);
        mSlidingMenu.setSecondaryMenu(R.layout.second_slidingmenu);
        //左菜单的控件id
        LinearLayout linearLayout_0 = (LinearLayout) findViewById(R.id.linear_0);
        LinearLayout linearLayout_1 = (LinearLayout) findViewById(R.id.linear_1);
        LinearLayout linearLayout_2 = (LinearLayout) findViewById(R.id.linear_2);
        LinearLayout linearLayout_3 = (LinearLayout) findViewById(R.id.linear_3);
        LinearLayout linearLayout_4 = (LinearLayout) findViewById(R.id.linear_4);
        //右菜单的控件id
        //图片登陆
        ImageView iv_plugin = (ImageView) findViewById(R.id.iv_plugin);
        //点击TextView登陆
        TextView tv_plugin = (TextView) findViewById(R.id.tv_plugin);
        //第三方登陆
        //微信
        ImageView iv_weixin = (ImageView) findViewById(R.id.iv_weixin);
        //qq
        ImageView iv_qq = (ImageView) findViewById(R.id.iv_qq);
        //friend
        ImageView iv_friend = (ImageView) findViewById(R.id.iv_friend);
        //微博
        ImageView iv_weibo = (ImageView) findViewById(R.id.iv_weibo);
        //更新
        TextView tv_update = (TextView) findViewById(R.id.tv_update);


        iv_title_main.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        //给左菜单控件绑定监听，实现点击效果
        linearLayout_0.setOnClickListener(this);
        linearLayout_1.setOnClickListener(this);
        linearLayout_2.setOnClickListener(this);
        linearLayout_3.setOnClickListener(this);
        linearLayout_4.setOnClickListener(this);
        //右菜单绑定监听
        iv_plugin.setOnClickListener(this);
        tv_plugin.setOnClickListener(this);
        iv_weixin.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_friend.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        tv_update.setOnClickListener(this);
        mTvShehui.setOnClickListener(this);
        mTv_junshi.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        mList_item.setInterface(this);
        //执行异步任务，获取网络数据
        new MyAsynctask().execute();
    }

    //一些控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_main:
                //左侧sliding menu的拉出和隐藏，调用toggle方法
                mSlidingMenu.toggle();
                break;
            //设置分类的点击事件
            case R.id.tv_shehui:
                mTvShehui.setTextColor(Color.RED);
                mTv_junshi.setTextColor(Color.BLACK);
                break;
            case R.id.tv_junshi:
                mTvShehui.setTextColor(Color.BLACK);
                mTv_junshi.setTextColor(Color.RED);
                break;
            case R.id.iv_title_share:
                //显示右侧的sliding menu
                mSlidingMenu.toggle();
                mSlidingMenu.showSecondaryMenu();
                break;
            case R.id.linear_0:
                mSlidingMenu.toggle();
                break;
            case R.id.linear_1:

                break;
            case R.id.linear_2:
                break;
            case R.id.linear_3:
                break;
            case R.id.linear_4:
                break;
            case R.id.iv_plugin:
                Intent intent = new Intent(this, Plugin.class);
                startActivity(intent);
                mSlidingMenu.toggle();
                break;
            case R.id.tv_plugin:
                Intent intent1 = new Intent(this, Plugin.class);
                startActivity(intent1);
                mSlidingMenu.toggle();
                break;
            case R.id.iv_weixin:
                break;
            case R.id.iv_qq:
                Intent intent_qq = new Intent(this, WebQQ.class);
                startActivity(intent_qq);
                break;
            case R.id.iv_friend:
                break;
            case R.id.iv_weibo:
                break;
            case R.id.tv_update:
                break;
        }

    }

    public void onReflash() {
        // TODO Auto-generated method stub\
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取最新数据
                //通知界面显示
                //通知listview 刷新数据完毕；
                mList_item.reflashComplete();
            }
        }, 2000);
    }
/*
*
* 这是一个异步处理消息的内部类
* 实现了在UI线程中获取网络数据
*
*/

    private class MyAsynctask extends AsyncTask<String, Integer, String> {
        private StringBuffer mStringBuffer;

        //处理ondoInbackground的发来的数据
        @Override
        protected void onPostExecute(String json) {
            Log.d(TAG, "onPostExecute: 开始执行解析。。。。。。。。。。。。。。");
            super.onPostExecute(json);
            //调用Gson解析方法
            mDatas = Parsegson(json);
            //创建适配器对象myAdapter
            DataAdapter myAdapter = new DataAdapter(Home.this, mDatas, mList_item);
            //调用添加数据方法把由doInbackground传来的数据添加到适配器中
            myAdapter.addData(mDatas);
            //给listview setAdapter
            mList_item.setAdapter(myAdapter);
            //ListView中的item的点击事件
            mList_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < mDatas.size(); i++) {
                        //点击item获取适配器中的地址链接
                        if (position == i) {
                            //get方法中i要减1 是因为listview的item的position默认从1开始
                            mLink = mDatas.get(i - 1).getLink();
                            //创建每个item的点击跳转
                            Intent intent = new Intent(Home.this, Base_WebView.class);
                            //开启网页加载意图
                            startActivity(intent);
                            Log.d(TAG, "onItemClick: 点击了。。。。。。。。。。。。。。" + position);
                        }
                    }
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {
            //网络地址拼接
            String PATH = "http://118.244.212.82:9092/newsClient/news_list?";
            String VER = "ver";
            String SUBID = "subid";
            String DIR = "dir";
            String NID = "nid";
            String DATE = "stamp";
            String NUM = "cnt";
            int version = 1;
            int subid = 1;
            int dir = 1;
            int nid = 1;
            int cnt = 20;
            GetDate date = new GetDate();
            String date_value = date.getdate();
            Uri uri = Uri.parse(PATH).buildUpon()
                    .appendQueryParameter(VER, Integer.toString(version))
                    .appendQueryParameter(SUBID, Integer.toString(subid))
                    .appendQueryParameter(DIR, Integer.toString(dir))
                    .appendQueryParameter(NID, Integer.toString(nid))
                    .appendQueryParameter(DATE, date_value)
                    .appendQueryParameter(NUM, Integer.toString(cnt))
                    .build();
            URL url = null;
            try {
                url = new URL(uri.toString());
                Log.d(TAG, "doInBackground: 网络地址。。。。。。。。。。。。。" + url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //连接地址
//            String Url = "http://118.244.212.82:9092/newsClient/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20160601&cnt=20";
            //创建OkHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            //向网络发起请求
            Request request = new Request.Builder().url(url).build();
            InputStream mInputStream = null;
            BufferedReader br = null;
            try {
                //网络响应，并执行
                Response response = okHttpClient.newCall(request).execute();
                //获取网络流
                mInputStream = response.body().byteStream();
                //创建缓冲区
                mStringBuffer = new StringBuffer();
                //写入流
                br = new BufferedReader(new InputStreamReader(mInputStream));
                String s;
                while ((s = br.readLine()) != null) {
                    mStringBuffer.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    mInputStream.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "doInBackground: " + mStringBuffer.toString());
            return mStringBuffer.toString();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    //这是一个解析方法，
    private ArrayList<GsonBean.Data> Parsegson(String json) {
        mArrayList = new ArrayList<>();
        Gson gson = new Gson();
        //调用fromJson方法解析数据
        GsonBean bean = gson.fromJson(json, GsonBean.class);
//        //信息为200，说明获取数据成功
//        String message = bean.getMessage();
//        //状态码
//        int status = bean.getStatus();
        //获取data
        List<GsonBean.Data> data = bean.getData();
//        //数据集合中的具体信息
        for (int i = 0; i < data.size(); i++) {
            //图片，链接，nid，日期，内容，标题，类型
//            String icon = data.get(i).getIcon();
//            String link = data.get(i).getLink();
//            int nid = data.get(i).getNid();
//            String stamp = data.get(i).getStamp();
//            String summary = data.get(i).getSummary();
//            String title = data.get(i).getTitle();
//            int type1 = data.get(i).getType();
            //往集合中添加数据,注意集合泛型
            mArrayList.add(data.get(i));
        }
        return mArrayList;
    }
}