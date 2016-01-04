package com.hnxind.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hnxind.zscj.R;

import java.util.HashMap;
import java.util.Map;

public class Act_Setting extends AppCompatActivity {
    Context context=this;
    ListView colorView;
    TextView colorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initToolbar();
        initView();
    }

    String[] colorNames=new String[]{"默认","建城蓝","罗兰紫","少女粉","优雅绿","沁心绿","活力橙","时尚灰"};
    String[] colorCodes=new String[]{"#93d9fd"/*默认*/,"#0268b1"/*建城蓝*/,"#c2185b"/*罗兰紫*/,"#FFF26E8F"/*少女粉*/
        ,"#FF2CB38D"/*优雅绿*/,"#009688"/*沁心绿*/,"#e7ef8636"/*活力橙*/,"#607d8b"/*时尚灰*/
    };

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

        toolbar.setTitleTextColor(Theme.TitleColor);
        toolbar.setBackgroundColor(Theme.MainColor);
    }

    public void initView(){
        SharedPreferences sharedPreferences=getSharedPreferences(Theme.THEME_SP,MODE_PRIVATE);
        colorName=(TextView)findViewById(R.id.colorname);
        colorName.setText(sharedPreferences.getString(Theme.COLOR_NAME,"默认"));
    }
    AlertDialog alertDialog=null;
    public void changeTheme(View view) {//修改主题
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);

        colorView=new ListView(this);
        colorView.setAdapter(new ArrayAdapter<>(this,R.layout.single_simple_list,colorNames));
        colorView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theme.MainColor=Color.parseColor(colorCodes[position]);
                if(position==0){
                    Theme.TitleColor=Color.parseColor("#48809d");
                }else{
                    Theme.TitleColor=Color.WHITE;
                }
                alertDialog.dismiss();
                initToolbar();
                colorName.setText(colorNames[position]);

                Intent intent=new Intent();
                intent.setAction(Theme.CHANGE_THEME);
                sendBroadcast(intent);

                SharedPreferences sharedPreferences=getSharedPreferences(Theme.THEME_SP,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt(Theme.MAIN_COLOR,Theme.MainColor);
                editor.putInt(Theme.TITLE_COLOR,Theme.TitleColor);
                editor.putString(Theme.COLOR_NAME,colorNames[position]);
                editor.commit();
            }
        });
        builder.setView(colorView);
        alertDialog=builder.show();

    }
}
