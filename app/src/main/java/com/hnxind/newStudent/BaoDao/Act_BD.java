package com.hnxind.newStudent.BaoDao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hnxind.setting.Theme;
import com.hnxind.zscj.R;

public class Act_BD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bd);
        initToolbar();
    }

    public void initToolbar(){
        Theme theme=new Theme(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setTitleTextColor(theme.getTitleColor());
        toolbar.setBackgroundColor(theme.getMainColor());
    }

    public void openliucheng(View view) {//流程
        Intent intent=new Intent(this,Act_BaoDaoLC.class);
        startActivity(intent);
    }

    public void opensushe(View view) {//宿舍查询
        Intent intent=new Intent(this,Act_Sushe.class);
        startActivity(intent);
    }
}
