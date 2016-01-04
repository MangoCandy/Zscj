package com.hnxind.rollManager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hnxind.rollManager.Act_Contacts.Act_Contacts;
import com.hnxind.rollManager.Act_Kebiao.Act_Kebiao;
import com.hnxind.rollManager.Act_PayForStudy.Act_PayForStudy;
import com.hnxind.rollManager.Act_Score.Act_score;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.zscj.R;

public class Act_rollManager extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_manager);
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

    //跳转联系人
    public void openContacts(View view){
        Intent intent=new Intent(this, Act_Contacts.class);
        startActivity(intent);
    }

    public void openScore(View view) {
        Intent intent=new Intent(this, Act_score.class);
        startActivity(intent);
    }

    public void openPay(View view) {//学费查询
        Intent intent=new Intent(this, Act_PayForStudy.class);
        startActivity(intent);
    }

    public void openKebiao(View view) {//课表查询
        Intent intent=new Intent(this, Act_Kebiao.class);
        startActivity(intent);
    }
}
