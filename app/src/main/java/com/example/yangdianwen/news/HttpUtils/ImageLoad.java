package com.example.yangdianwen.news.HttpUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 异步加载网络图片
 * Created by yangdianwen on 2016/6/4.
 */
public class ImageLoad {
    private ImageView mImageView;
    private String mUrl;
    private LruCache<String, Bitmap> mCache;
    public ImageLoad() {
        //获取运行时最大内存
        int maxMemroy = (int) Runtime.getRuntime().maxMemory();
        //缓存大小
        int cacheSize = maxMemroy / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    //添加图片到缓存区
    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }
    //获取缓存区中的数据
    public Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //防止图片的显示混乱，添加标记判断，getTag是由adpter传来的标记
            if (mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };
    //使用线程与handler实现信息传递，显示图片
    public void showImageByThread(ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;
        //线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapUrl(url);
                //创建消息对象，使用obtain提高message使用效率
                Message message = Message.obtain();
                message.obj = bitmap;
                //给handler发消息，把bitmap传进来
                mHandler.sendMessage(message);
            }
        }.start();
    }

    //使用httpurlconnection获取图片的网络路径，
    public Bitmap getBitmapUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            //获取俩链接
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            //使用get请求
            httpUrlConnection.setRequestMethod("GET");
            //获取网络流
            is = new BufferedInputStream(httpUrlConnection.getInputStream());
            //获取bitmap
            bitmap = BitmapFactory.decodeStream(is);
            //关闭网路服务
            httpUrlConnection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //使用AsyncTask实现异步加载
    public void showImageByAsynckTask(ImageView imageView, String url) {
        //从缓存区中获取图片
         Bitmap bitmap = getBitmapFromCache(url);
        //如果缓存中没有图片，去网络下载
        if (bitmap == null) {
            new mAsyncTask(imageView, url).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }
    }
    private class mAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView mImageView;
        private String mUrl;

        public mAsyncTask(ImageView imageView, String url) {
            mImageView = imageView;
            mUrl = url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url=params[0];
            //从网络获取图片
            Bitmap bitmap= getBitmapUrl(url);
            if (bitmap != null) {
                //将图片加入缓存
             addBitmapToCache(url,bitmap);
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //为防止图片加载混乱
            if (mImageView.getTag().equals(mUrl)) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
