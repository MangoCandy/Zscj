package com.hnxind.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hnxind.library.jieyueHistory.Act_JieyueHistory;
import com.hnxind.library.qianfei.Act_Qianfei;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.zscj.R;

public class Act_Library extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        initToolbar();
    }

    //初始化toolbar
    public void initToolbar(){
        Theme theme=new Theme(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);

        toolbar.setTitleTextColor(theme.getTitleColor());
        toolbar.setBackgroundColor(theme.getMainColor());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void openhistory(View view) {//借阅历史
        Intent intent=new Intent(this, Act_JieyueHistory.class);
        startActivity(intent);
    }

    public void openqianfei(View view) {//欠费查询
        Intent intent=new Intent(this, Act_Qianfei.class);
        startActivity(intent);
    }

    public void openbook(View view) {//书籍信息
        Intent intent=new Intent(this, Act_Book.class);
        startActivity(intent);
    }
}
