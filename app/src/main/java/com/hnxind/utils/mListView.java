package com.hnxind.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hnxind.CrpSystem.Act_Information.CrpInformationAdapter;
import com.hnxind.zscj.R;

/**
 * Created by Administrator on 2015/12/24.
 */
public class mListView extends ListView implements View.OnTouchListener{
    public mListView(Context context) {
        super(context);
    }
    float y=0;
    float my=0;
    View footview;
    boolean isLoading;
    OnLoadMoreListener onLoadMoreListener;
    public mListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setOnLoadMoreListener(final OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener=onLoadMoreListener;
        footview= LayoutInflater.from(getContext()).inflate(R.layout.foot_layout,null);
        setOnTouchListener(this);
    }
    public mListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public boolean isBottom(){
        return getLastVisiblePosition()==getAdapter().getCount();
    }
    public void setLoadMore(boolean loadMore){
        isLoading=loadMore;
        if(loadMore){
            addFooterView(footview);
        }else{
            removeFooterView(footview);
        }
    }
    public boolean checkCanLoad(){
        if(isBottom()&&my-y>80&&!isLoading){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("asd",event.getRawY()+"");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                y=event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                my=event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if(checkCanLoad()){
                    setLoadMore(true);
                    onLoadMoreListener.loadmore();
                }
                break;
        }
        return false;
    }

    public interface OnLoadMoreListener{
        public void loadmore();
    }
}
