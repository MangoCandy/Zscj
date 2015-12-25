package com.hnxind.zscj;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.hnxind.model.mUrl;
import com.hnxind.personInfo.Act_PersonInfo;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getGrids();
        initView();
        initToolBar();
    }

    public void initToolBar(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.logo);
        setSupportActionBar(toolbar);
        toolbar.setMenu(null,null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.action_settings, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
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
        int id=item.getItemId();
        switch (id){
            case R.id.info:
                Intent intent=new Intent(context, Act_PersonInfo.class);
                startActivity(intent);
                break;
            case R.id.loginout:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("确认退出登录");
                builder.setCustomTitle(LayoutInflater.from(this).inflate(R.layout.nav_header,null));
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
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCustomTitle(LayoutInflater.from(this).inflate(R.layout.nav_header,null));
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
