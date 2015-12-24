package com.hnxind.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hnxind.zscj.R;

public class Act_WebView extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initToolbar();
        initView();
        initDate();
    }

    private void initDate() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String content=bundle.getString("content");
        if(content!=null&&!content.equals("")){
            webView.loadDataWithBaseURL("",content,"text/html","UTF-8","");
        }
    }

    public void initView(){
        webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
    }
    public void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
