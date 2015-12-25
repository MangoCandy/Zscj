package com.hnxind.schoolCard.Act_CostList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnxind.model.Cost;
import com.hnxind.model.CrpInformation;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
public class CostAdapter extends BaseAdapter{
    List<Cost> costList=null;
    Context context;
    public CostAdapter(List<Cost> costList, Context context){
        this.costList=costList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return costList.size();
    }

    @Override
    public Object getItem(int position) {
        return costList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView location;
        TextView cost;
        TextView yue;
        TextView time;
        TextView decription;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        Cost cost=costList.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.single_cost,null);
            viewHolder=new ViewHolder();
            viewHolder.cost= (TextView) convertView.findViewById(R.id.cost);
            viewHolder.time= (TextView) convertView.findViewById(R.id.time);
            viewHolder.location= (TextView) convertView.findViewById(R.id.location);
            viewHolder.yue= (TextView) convertView.findViewById(R.id.yue);
            viewHolder.decription= (TextView) convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.cost.setText("支出："+cost.getCost());
        viewHolder.decription.setText("描述："+cost.getDescription());
        viewHolder.time.setText("时间："+cost.getTime());
        viewHolder.location.setText("地点："+cost.getLocation());
        viewHolder.yue.setText("余额："+cost.getYue());
        return convertView;
    }
}
