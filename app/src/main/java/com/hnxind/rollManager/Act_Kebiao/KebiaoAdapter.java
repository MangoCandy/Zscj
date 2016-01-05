package com.hnxind.rollManager.Act_Kebiao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hnxind.model.Kebiao;
import com.hnxind.zscj.R;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mangocandy on 15-12-30.
 */
public class KebiaoAdapter extends BaseExpandableListAdapter {
    Map<String,List<Kebiao>> kebiaoMap;
    String[] p;
    public KebiaoAdapter(Map<String,List<Kebiao>> kebiaoMap,String[] p){
        this.kebiaoMap=kebiaoMap;
        this.p=p;
    }
    @Override
    public int getGroupCount() {
        return kebiaoMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return kebiaoMap.get(p[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return kebiaoMap.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return kebiaoMap.get(p[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Set<String> t=kebiaoMap.keySet();
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ex_parent,null);
        TextView parenttext=(TextView)convertView.findViewById(R.id.parent);
        parenttext.setText(p[groupPosition]);

        TextView beizhu=(TextView)convertView.findViewById(R.id.parent_beizhu);
        beizhu.setText(kebiaoMap.get(p[groupPosition]).size()+"节");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_kebiao,null);
        Kebiao kebiao=kebiaoMap.get(p[groupPosition]).get(childPosition);
        TextView ktmc=(TextView)convertView.findViewById(R.id.ktmc);
        TextView kcmc=(TextView)convertView.findViewById(R.id.kcmc);
        TextView teacher=(TextView)convertView.findViewById(R.id.teacher);
        TextView sjd=(TextView)convertView.findViewById(R.id.sjd);

        ktmc.setText(kebiao.getKtmc());
        kcmc.setText(kebiao.getKcmc());
        teacher.setText(kebiao.getTeacher());
        sjd.setText(kebiao.getSjd());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
