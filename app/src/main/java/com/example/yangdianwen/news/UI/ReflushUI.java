package com.example.yangdianwen.news.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yangdianwen.news.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangdianwen on 2016/6/5.
 */
public class ReflushUI extends ListView implements AbsListView.OnScrollListener {
    private static final String TAG = "ReflushUI";
    View header;//顶部布局
    int HeaderHeight;//顶部布局的高度
    int firstVisibleItem;//当前可见页面的第一个item位置
    boolean isRemark;//标记当前是在最顶端按下的
    int startY;//按下时的高度y值
    int scrollState;//listview当前滚动状态
    int state;//当前状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉刷新状态
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//正在刷新状态

    public ReflushUI(Context context) {
        super(context);
        initView(context);
    }

    public ReflushUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ReflushUI(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /*
    *初始化界面，添加布局文件到listview
     */
    private void initView(Context context) {
        //布局填充器
        LayoutInflater inflater = LayoutInflater.from(context);
        //加载顶部布局
        header = inflater.inflate(R.layout.header, null);
        measureVeiw(header);
        //获取顶部布局的高度
        HeaderHeight = header.getMeasuredHeight();
        Log.d(TAG, "initView: 。。。。。。。高度。。。。。。。" + HeaderHeight);
        //使用高度的负值来隐藏顶部布局
        topPadding(-HeaderHeight);
        //把布局文件添加到listview中
        this.addHeaderView(header);
        //listview 的滑动监听
        this.setOnScrollListener(this);
    }

    /*
    /通知父布局，子布局占用的空间，宽，高
     */
    private void measureVeiw(View view) {
        //获取子布局
        ViewGroup.LayoutParams parent = view.getLayoutParams();
        if (parent == null) {
            parent = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            //getChildMeasureSpec三个参数分别表示 左右边距，内边距，子布局宽度
            int width = ViewGroup.getChildMeasureSpec(0, 0, parent.width);
            int height;
            int tempHeight = parent.height; //子布局的高度
            //高度不为空则填充布局，否则不填充
            if (tempHeight > 0) {
                height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
            } else {
                height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            }
            view.measure(width, height);
        }
    }

    /**
     * 设置布局上边距
     */
    public void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //向下拉状态
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
                break;
            //移动状态
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            //松开状态
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    //加载最新数据
                    reFlashViewByState();
                    reflashComplet();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reFlashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //判断移动过程操作
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        //获取当前移动到
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - HeaderHeight;
        switch (state) {
            case NONE:
                if (space > 0) {
                    state = PULL;
                    reFlashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                //当高度大于一定高度，状态改变
                if (space > HeaderHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reFlashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < HeaderHeight + 30) {
                    state = PULL;
                    reFlashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reFlashViewByState();
                }
                break;
        }
    }

    //通过监听状态刷新UI
    private void reFlashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
        //给箭头图片设置动画
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.roate);
//        RotateAnimation anim = new RotateAnimation(0, 180,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        anim.setDuration(500);
        anim.setFillAfter(true);
        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.rorate1);
//        RotateAnimation anim1 = new RotateAnimation(180, 0,
//                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state) {
            case NONE:
                topPadding(-HeaderHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新!");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新!");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新........");
                break;
        }
    }
    public void reflashComplet() {
        state = NONE;
        isRemark = false;
        reFlashViewByState();
        TextView last_update_time = (TextView) findViewById(R.id.last_update_time);
        //获取时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = sdf.format(date);
        last_update_time.setText(time);
    }
}
