package com.hnxind.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hnxind.CrpSystem.Act_Information.CrpInformationAdapter;
import com.hnxind.zscj.R;


/**
 * Created by Administrator on 2015/12/16.
 */
public class SwiperefreshWithLoadMore extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    Context context;
    View footview;
    OnLoadListener onLoadListener;
    OnLoadListener onLoadListener1;
    public SwiperefreshWithLoadMore(Context context) {
        super(context);
    }

    public SwiperefreshWithLoadMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        footview=LayoutInflater.from(context).inflate(R.layout.foot_layout,null);
        footview.setOnClickListener(null);
        mTouchSlop= 200;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        if(true){
            if(onLoadListener==null&&onLoadListener1!=null){
                ((TextView)footview.findViewById(R.id.text_more)).setText("正在加载...");
                onLoadListener=onLoadListener1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    ListView listView;
    View v;
    int my=0;
    int y=0;
    int mTouchSlop=0;
    public void getListview(){
        for(int i=0;i<this.getChildCount();i++){
            View view=getChildAt(i);
            v=view;
            if(view instanceof ListView){
                listView=(ListView) view;
                listView.addFooterView(footview);
                listView.setOnScrollListener(this);
                listView.setFooterDividersEnabled(false);
            }
        }
    }

    public void loaddate(){
        setLoadingMore(true);
        onLoadListener.load();
        listView.smoothScrollToPosition(listView.getLastVisiblePosition());
    }
    public boolean canLoad(){
        return isBottom()&&!isloading;
    }
    boolean isloading=false;
    public void setLoadingMore(boolean bool){
        isloading=bool;
        setRefreshing(bool);
        if(bool){
            footview.setVisibility(VISIBLE);
        }else {
            footview.setVisibility(GONE);
        }
    }

    public void setOnLoadListener(OnLoadListener onLoadListener){
        if(onLoadListener!=null){
            getListview();
            this.onLoadListener=onLoadListener;
            onLoadListener1=onLoadListener;
        }else{
            this.onLoadListener=null;
            if(footview.getVisibility()==GONE){
                footview.setVisibility(VISIBLE);
            }
            ((TextView)footview.findViewById(R.id.text_more)).setText("没有更多内容了哦");
        }
    }
    public boolean isBottom(){
        if(listView!=null){
            return listView.getLastVisiblePosition()>=listView.getAdapter().getCount()-1;
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(onLoadListener!=null){
            if(isBottom()&&scrollState==SCROLL_STATE_IDLE&&listView.getFooterViewsCount()<1){
                listView.addFooterView(footview);
            }
            if(listView.getFooterViewsCount()>0&&isBottom()){
                if(canLoad()){
                    loaddate();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public static interface OnLoadListener{
        public void load();
    }
}
