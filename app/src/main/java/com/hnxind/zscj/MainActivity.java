package com.hnxind.zscj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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
        toolbar.setNavigationIcon(R.mipmap.logo);
        setSupportActionBar(toolbar);
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
}
