package com.hnxind.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hnxind.zscj.R;

public class Act_Setting extends AppCompatActivity {
    Context context=getApplicationContext();
    public static int getColor(){
        Theme.MainColor=Color.parseColor("3");
        int color= Color.parseColor("#800080");
        return color;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
