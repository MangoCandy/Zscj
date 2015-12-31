package com.hnxind.rollManager.Act_Score;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnxind.model.Score;
import com.hnxind.zscj.R;

import java.util.List;

/**
 * Created by mangocandy on 15-12-30.
 */
public class ScoreAdapter extends BaseAdapter {
    List<Score> scores;
    public ScoreAdapter(List<Score> scores){
        this.scores=scores;
    }
    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Score score= (Score) getItem(position);
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_grades,null);
            viewHolder.project=(TextView)convertView.findViewById(R.id.project);
            viewHolder.grade=(TextView)convertView.findViewById(R.id.grade);
            viewHolder.date=(TextView)convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.date.setText(score.getDate());
        viewHolder.project.setText(score.getProject());
        viewHolder.grade.setText(score.getGrades());
        return convertView;
    }
    class ViewHolder{
        TextView project;
        TextView grade;
        TextView date;
    }
}
