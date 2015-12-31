package com.hnxind.library.jieyueHistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.hnxind.model.JieyueLS;
import com.hnxind.zscj.R;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/12/31.
 */
public class JieyueAdapter extends BaseExpandableListAdapter {
    Map<String,JieyueLS> jieyueLSMap;
    List<String> books;
    public JieyueAdapter(Map<String,JieyueLS> jieyueLSMap,List<String> books){
        this.jieyueLSMap=jieyueLSMap;
        this.books=books;
    }
    @Override
    public int getGroupCount() {
        return books.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return jieyueLSMap.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return books.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return jieyueLSMap.get(books.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String s=books.get(groupPosition);
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ex_parent,null);
        TextView parenttext=(TextView)convertView.findViewById(R.id.parent);
        parenttext.setText(s);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_jieyue,null);
        JieyueLS jieyueLS=jieyueLSMap.get(books.get(groupPosition));

        TextView datecheckout=(TextView)convertView.findViewById(R.id.datecheckout);
        TextView datedue=(TextView)convertView.findViewById(R.id.datedue);
        TextView tiaoma=(TextView)convertView.findViewById(R.id.tiaoma);

        datecheckout.setText("借书日期："+jieyueLS.getDateCheckout());
        datedue.setText("到期时间："+jieyueLS.getDateDue());
        tiaoma.setText("图书条码："+jieyueLS.getBookBarcode());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
