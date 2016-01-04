package com.hnxind.zscj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hnxind.setting.Theme;

public class Act_Wellcome extends Activity {
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        initTheme();//初始化主题

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(context,Act_Login.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    public void initTheme(){
        SharedPreferences sharedPreferences=getSharedPreferences(Theme.THEME_SP,MODE_PRIVATE);
        int maincolor=sharedPreferences.getInt(Theme.MAIN_COLOR,0);
        if(maincolor!=0){
            Theme.MainColor=maincolor;
            Theme.TitleColor=sharedPreferences.getInt(Theme.TITLE_COLOR,Color.parseColor("#48809d"));
        }
    }
}
