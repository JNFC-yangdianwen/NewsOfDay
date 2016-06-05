package com.example.yangdianwen.news.UI;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yangdianwen.news.Adapter.DataAdapter;
import com.example.yangdianwen.news.Bean.GsonBean;
import com.example.yangdianwen.news.R;
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
public class Home extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Home";
    private ArrayList<GsonBean.Data> mArrayList;
    private ReflushUI mList_item;
    private TextView mTvShehui;
    private TextView mTv_junshi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
    }

    //初始化主页面
    //在主页面中使用AsyncTask获取网络数据
    private void initView() {
        mList_item = (ReflushUI) findViewById(R.id.lv_list);
        //主页Actionbar中的控件
        ImageView iv_title_main = (ImageView) findViewById(R.id.iv_title_main);
        ImageView iv_share = (ImageView) findViewById(R.id.iv_title_share);
        mTvShehui = (TextView)findViewById(R.id.tv_shehui);
        mTv_junshi = (TextView)findViewById(R.id.tv_junshi);
        //侧拉菜单初始化
        SlidingMenu slidingMenu = new SlidingMenu(this);
        //侧拉菜单的触摸响应范围
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置侧拉菜单的偏移量
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_width);
        //布置侧拉菜单界面
        slidingMenu.setMenu(R.layout.sliding_menu_type);
        slidingMenu.setSecondaryMenu(R.layout.second_slidingmenu);
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

        //控件的监听
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
        //执行异步任务，获取网络数据
        new MyAsynctask().execute();
    }

    //一些控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_main:
                Toast.makeText(Home.this, "点击了主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_shehui:
                Toast.makeText(Home.this, "点击了社会", Toast.LENGTH_SHORT).show();
                mTvShehui.setTextColor(Color.RED);
                mTv_junshi.setTextColor(Color.BLACK);
                break;
            case R.id.tv_junshi:
                Toast.makeText(Home.this, "点击了军事", Toast.LENGTH_SHORT).show();
                mTvShehui.setTextColor(Color.BLACK);
                mTv_junshi.setTextColor(Color.RED);
                break;
            case R.id.iv_title_share:
                Toast.makeText(Home.this, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_0:
                Intent intent = new Intent(this, WebView1.class);
                startActivity(intent);
                Toast.makeText(this, "点击了NEWS", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_1:
                Toast.makeText(Home.this, "点击了FAVORITE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_2:
                Toast.makeText(Home.this, "点击了LOCAL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_3:
                Toast.makeText(Home.this, "点击了FLLOWME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_4:
                Toast.makeText(Home.this, "点击了PHOTO", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_plugin:
                Toast.makeText(this, "点击了登陆1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_plugin:
                Toast.makeText(Home.this, "点击了登陆2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_weixin:
                Toast.makeText(Home.this, "点击了登陆3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_qq:
                Intent intent_qq = new Intent(this, WebQQ.class);
                startActivity(intent_qq);
                Toast.makeText(Home.this, "点击了登陆qq", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_friend:
                Toast.makeText(Home.this, "点击了登陆5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_weibo:
                Toast.makeText(this, "点击了登陆6", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_update:
                Toast.makeText(Home.this, "当前版本已是最新", Toast.LENGTH_SHORT).show();
                break;
        }

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
            ArrayList<GsonBean.Data> datas = Parsegson(json);
            //创建适配器对象myAdapter
            DataAdapter myAdapter = new DataAdapter(Home.this,datas,mList_item);
            //调用添加数据方法把由doInbackground传来的数据添加到适配器中
            myAdapter.addData(datas);
            //给listview setAdapter
            mList_item.setAdapter(myAdapter);
            mList_item.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
            //ListView中的item的点击事件
            mList_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < position; i++) {
                        if (position == i) {
                            Intent intent = new Intent(getApplicationContext(), WebView1.class);
                            startActivity(intent);
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
            int cnt=20;
            GetDate date=new GetDate();
            String date_value = date.getdate();
            Uri uri = Uri.parse(PATH).buildUpon()
                    .appendQueryParameter(VER, Integer.toString(version))
                    .appendQueryParameter(SUBID, Integer.toString(subid))
                    .appendQueryParameter(DIR, Integer.toString(dir))
                    .appendQueryParameter(NID, Integer.toString(nid))
                    .appendQueryParameter(DATE,date_value)
                    .appendQueryParameter(NUM,Integer.toString(cnt))
                    .build();
            URL url=null;
            try {
                 url= new URL(uri.toString());
                Log.d(TAG, "doInBackground: 网络地址。。。。。。。。。。。。。"+url);
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
