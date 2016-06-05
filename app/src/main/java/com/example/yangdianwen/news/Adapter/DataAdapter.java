package com.example.yangdianwen.news.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yangdianwen.news.Bean.GsonBean;
import com.example.yangdianwen.news.HttpUtils.ImageLoad;
import com.example.yangdianwen.news.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据适配器
 * Created by yangdianwen on 2016/6/3.
 */
public class DataAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private ImageLoad mImageLoad;
    List<GsonBean.Data> mList = new ArrayList<>();
    LayoutInflater mLayoutInflater;
    private int mStart;
    private int mEnd;
    public static String[] urls;
    private boolean isFirstRun;

    //自定义布局加载器
    public DataAdapter(Context context, List<GsonBean.Data> data, ListView listview) {
        mList = data;
        mLayoutInflater = LayoutInflater.from(context);
        mImageLoad = new ImageLoad(listview);
        urls = new String[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            urls[i] = mList.get(i).getIcon();
        }
        listview.setOnScrollListener(this);
        isFirstRun = true;
    }

    public void addSingle(GsonBean.Data data) {
        mList.add(data);
    }

    //添加数据方法
    public void addData(List<GsonBean.Data> list) {
        mList.addAll(list);
    }

    //清除数据方法
    public void clear() {
        mList.clear();
    }


    //返回数据长度
    @Override
    public int getCount() {
        return mList.size();
    }

    //获取Item
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
//使用viewholder优化
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //listview的控件初始化
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        GsonBean.Data gsonBean = mList.get(position);
        viewHolder.iv_icon.setImageResource(R.drawable.defaultpic);
        String iconUrl = mList.get(position).getIcon();
        viewHolder.iv_icon.setTag(iconUrl);
        //使用线程异步加载图片
//        new ImageLoad().showImageByThread(viewHolder.iv_icon,iconUrl);
        //使用AsyncTask加载
        mImageLoad.showImageByAsynckTask(viewHolder.iv_icon, iconUrl);
        viewHolder.tv_title.setText(gsonBean.getTitle().toString());
        viewHolder.tv_content.setText(gsonBean.getSummary().toString());
        viewHolder.tv_date.setText(gsonBean.getStamp().toString());
        return convertView;
    }

    //listView滑动状态改变时
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当前滚动停止
        if (scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            mImageLoad.loadImage(mStart, mEnd);
        } else {
            //停止任务
            mImageLoad.cancelAllTasks();


        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        //判断是否为第一次加载
        if (isFirstRun && visibleItemCount > 0) {
            mImageLoad.loadImage(mStart, mEnd);
            isFirstRun=false;
        }
    }

    class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_title;
        public TextView tv_content;
        public TextView tv_date;
    }
}
