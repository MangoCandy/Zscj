package com.hnxind.rollManager.Act_PayForStudy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnxind.model.Tuition;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
public class TuitionAdapter extends BaseAdapter{
    List<Tuition> tuitions=null;
    Context context;
    public TuitionAdapter(List<Tuition> tuitions,Context context){
        this.tuitions=tuitions;
        this.context=context;
    }
    @Override
    public int getCount() {
        return tuitions.size();
    }

    @Override
    public Object getItem(int position) {
        return tuitions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView time;
        TextView name;
        TextView yingjiao;
        TextView shijiao;
        TextView qianfei;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        Tuition tuition=tuitions.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.single_payforstudy,null);
            viewHolder=new ViewHolder();
            viewHolder.name= (TextView) convertView.findViewById(R.id.payname);
            viewHolder.yingjiao=(TextView)convertView.findViewById(R.id.yingjiao);
            viewHolder.shijiao=(TextView)convertView.findViewById(R.id.shijiao);
            viewHolder.qianfei=(TextView)convertView.findViewById(R.id.qianfei);
            viewHolder.time=(TextView)convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.time.setText(tuition.getTime()+"年");
        viewHolder.name.setText("缴费项目:"+tuition.getName());
        viewHolder.yingjiao.setText("应交:"+tuition.getYingjiao());
        viewHolder.qianfei.setText("欠费"+tuition.getQianfei());
        viewHolder.shijiao.setText("实交"+tuition.getShijiao());
        return convertView;
    }
}
