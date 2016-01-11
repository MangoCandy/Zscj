package com.hnxind.zscj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hnxind.CrpSystem.Act_CrpSystem;
import com.hnxind.fankui.Act_Fankui;
import com.hnxind.library.Act_Library;
import com.hnxind.map.Act_Map;
import com.hnxind.model.Grid;
import com.hnxind.model.mUrl;
import com.hnxind.newStudent.BaoDao.Act_BD;
import com.hnxind.newStudent.BaoDao.Act_BaoDaoLC;
import com.hnxind.rollManager.Act_rollManager;
import com.hnxind.schoolCard.Act_SchoolCard;
import com.hnxind.utils.Act_WebView;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.mViewHolder> {
    List<Grid> grids;
    Context context;
    public GridAdapter(List<Grid> grids){
        this.grids=grids;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.single_grid,null);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        final Grid grid=grids.get(position);
        holder.grid_name.setText(grid.getGridName());
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) holder.grid_pic.getLayoutParams();
        layoutParams.height=getMetrics().widthPixels/3;
        holder.grid_pic.setLayoutParams(layoutParams);
        Glide.with(context).load(mUrl.iconUrl+grid.getGridPic()).into(holder.grid_pic);
        holder.gridParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGrid(grid.getGridId());
            }
        });
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        TextView grid_name;
        ImageView grid_pic;
        LinearLayout gridParent;
        public mViewHolder(View itemView) {
            super(itemView);
            grid_name= (TextView) itemView.findViewById(R.id.grid_name);
            grid_pic= (ImageView) itemView.findViewById(R.id.grid_pic);
            gridParent=(LinearLayout) itemView.findViewById(R.id.gridParent);
        }
    }
    @Override
    public int getItemCount() {
        return grids.size();
    }
    //获取屏幕尺寸
    public DisplayMetrics getMetrics(){
        DisplayMetrics metrics=new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
    private void openGrid(String gridid){
        Intent intent=null;
        switch (gridid){
            case "14"://学籍管理
                intent=new Intent(context,Act_rollManager.class);
                break;
            case "19"://CRP系统
                intent=new Intent(context,Act_CrpSystem.class);
                break;
            case "18"://一卡通
                intent=new Intent(context, Act_SchoolCard.class);
                break;
            case "17"://图书
                intent=new Intent(context, Act_Library.class);
                break;
            case "20"://信息卡
                intent=new Intent(context, Act_WebView.class);
                intent.putExtra("url","http://wap.10010.com/t/home.htm");
                intent.putExtra("title","联通");
                break;
            case "22"://建议反馈
                intent=new Intent(context,Act_Fankui.class);
                break;
            case "15"://报道信息
                intent=new Intent(context, Act_BD.class);
                break;
            case "23"://实名
                intent=new Intent(context, Act_WebView.class);
                intent.putExtra("url",mUrl.shiming);
                intent.putExtra("title","联通实名认证");
                break;
            case "24"://百度地图
//                intent=new Intent(context,Act_Map.class);
                intent=new Intent(context,Act_WebView.class);
                intent.putExtra("url","http://map.baidu.com/mobile/webapp/index/index/foo=bar/vt=map");
                intent.putExtra("title","百度地图");
                break;
        }

        if(intent!=null){
            context.startActivity(intent);
        }else {
            Toast.makeText(context,"功能正在开发",Toast.LENGTH_SHORT).show();
        }
    }
}
