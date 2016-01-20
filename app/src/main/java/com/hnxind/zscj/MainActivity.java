package com.hnxind.zscj;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hnxind.model.Grid;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.personInfo.Act_PersonInfo;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Grid> grids=new ArrayList<>();
    Context context=this;

    RecyclerView gridView;
    GridAdapter gridAdapter;

    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGrids();
        initView();
        initToolBar();
        initBroad();

        userInfo=(new Utils_user(context)).getUserInfo();
//        if(userInfo.getStudentnum().equals("")){//手机号登陆 没有学籍号 先接收学籍号
//
//        }
    }

    public void initBroad(){//接受广播 修改主题
        MyBroad myBroad=new MyBroad();
        IntentFilter filter=new IntentFilter();
        filter.addAction(Theme.CHANGE_THEME);
        registerReceiver(myBroad,filter);
    }
    DrawerLayout drawer;
    NavigationView navigationView;
    public void initToolBar(){
        Theme theme=new Theme(this);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(theme.MainColor);
        toolbar.getBackground().setAlpha(0);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.action_settings, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView=(NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView=(NavigationView)findViewById(R.id.navigationView);
        navigationView.getHeaderView(0).setBackgroundColor(theme.MainColor);
    }
    public void initView(){
        gridAdapter=new GridAdapter(grids);
        gridView=(RecyclerView) findViewById(R.id.gridView);
        gridView.setLayoutManager(new GridLayoutManager(this,3));
        gridView.setAdapter(gridAdapter);
    }

    //jsonArray转grids（模块信息）
    private void getGrids(){
        try {//从登陆界面获取模块信息
            JSONArray jsonArray= new JSONArray(getIntent().getExtras().getString("grid"));
            for(int i=0;i<jsonArray.length();i++){
                Grid grid=new Grid();
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                grid.setGridId(jsonObject.getString(Grid.GRID_ID));
                grid.setGridName(jsonObject.getString(Grid.GRID_NAME));
                grid.setGridPic(jsonObject.getString(Grid.GRID_PIC));
                grids.add(grid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawers();
        final int id=item.getItemId();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (id){
                    case R.id.info:
                        Intent intent=new Intent(context, Act_PersonInfo.class);
                        startActivity(intent);
                        break;
                    case R.id.loginout:
                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setMessage("确认退出登录");
//                        builder.setCustomTitle(LayoutInflater.from(context).inflate(R.layout.nav_header,null));
                        builder.setTitle("温馨提示");
                        builder.setNegativeButton("取消",null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils_user utils_user=new Utils_user(context);
                                utils_user.clearUserInfo();
                                Intent intent=new Intent(context,Act_Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                        break;
                    case R.id.setting://设置
                        Intent intent1=new Intent(context, Act_Setting.class);
                        startActivity(intent1);
                        break;
                }
            }
        },300);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(navigationView)){
            drawer.closeDrawers();
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        View view=LayoutInflater.from(this).inflate(R.layout.nav_header,null);
//        builder.setCustomTitle(view);
            builder.setTitle("温馨提示");
            builder.setMessage("确认退出掌上城建");
            builder.setNegativeButton("取消",null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }
    }

    public class MyBroad extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            initToolBar();
        }
    }
}
