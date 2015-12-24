package com.hnxind.CrpSystem.Act_Information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnxind.model.CrpInformation;
import com.hnxind.model.Tuition;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
public class CrpInformationAdapter extends BaseAdapter{
    List<CrpInformation> crpInformations=null;
    Context context;
    public CrpInformationAdapter(List<CrpInformation> crpInformations, Context context){
        this.crpInformations=crpInformations;
        this.context=context;
    }
    @Override
    public int getCount() {
        return crpInformations.size();
    }

    @Override
    public Object getItem(int position) {
        return crpInformations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView title;
        TextView send_name;
        TextView create_time;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        CrpInformation crpInformation=crpInformations.get(position);
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.single_crpinformation,null);
            viewHolder=new ViewHolder();
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.send_name=(TextView)convertView.findViewById(R.id.send_name);
            viewHolder.create_time=(TextView)convertView.findViewById(R.id.create_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(crpInformation.getTitle());
        viewHolder.send_name.setText("作者:"+crpInformation.getSend_name());
        viewHolder.create_time.setText("发布时间:"+crpInformation.getCreate_date());
        return convertView;
    }
}
