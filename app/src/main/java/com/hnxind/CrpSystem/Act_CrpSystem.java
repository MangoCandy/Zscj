package com.hnxind.CrpSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hnxind.CrpSystem.Act_Information.Act_Information;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.zscj.R;

public class Act_CrpSystem extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crp_system);
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

    public void openInformation(View view) {
        Intent intent=new Intent(this, Act_Information.class);
        startActivity(intent);
    }
}
