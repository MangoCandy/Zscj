package com.hnxind.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnxind.model.Tuition;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class LuxianAdapter extends BaseAdapter {
    List<String> luxians=null;
    Context context;
    public LuxianAdapter(List<String> luxians,Context context){
        this.luxians=luxians;
        this.context=context;
    }
    @Override
    public int getCount() {
        return luxians.size();
    }

    @Override
    public Object getItem(int position) {
        return luxians.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView luxian;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        String luxiantext=luxians.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.single_payforstudy,null);
            viewHolder=new ViewHolder();
            viewHolder.luxian= (TextView) convertView.findViewById(R.id.payname);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.luxian.setText(luxiantext);
        return convertView;
    }
}
