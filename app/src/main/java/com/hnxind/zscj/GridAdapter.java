package com.hnxind.zscj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
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
import com.hnxind.model.Grid;
import com.hnxind.model.mUrl;
import com.hnxind.rollManager.Act_rollManager;
import com.hnxind.schoolCard.Act_SchoolCard;

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

                break;
            case "20"://信息卡

                break;
            case "22"://建议反馈

                break;
        }

        context.startActivity(intent);
    }
}
