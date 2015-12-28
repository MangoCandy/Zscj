package com.hnxind.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hnxind.zscj.R;

public class Act_WebView extends AppCompatActivity {
    WebView webView;
    String url;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initDate();
        initToolbar();
    }

    private void initDate() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String content=bundle.getString("content");
        title=bundle.getString("title");
        url=bundle.getString("url");
        if(content!=null&&!content.equals("")){
            webView.loadDataWithBaseURL("",content,"text/html","UTF-8","");
        }else
        if(url!=null&&!url.equals("")){
            webView.loadUrl(url);
        }
    }

    public void initView(){
        webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient(){
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    public void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(title);
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
