package com.hnxind.rollManager.Act_Score;

import android.content.Context;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.Contact;
import com.hnxind.model.Score;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_score extends AppCompatActivity {
    Context context=this;
    UserInfo userInfo;

    int startYear;
    int endYear;
    String[] dates;
    String[] ds;
    String date;
    Spinner spinner;

    List<Score> scores=new ArrayList<>();
    ScoreAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        userInfo= (new Utils_user(context)).getUserInfo();
        initToolbar();
        getData();
        initView();
    }
    public void initView(){
        spinner=(Spinner)findViewById(R.id.spiner);
        spinner.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,dates));
        spinner.setSelection(dates.length-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=dates.length-1){
                    date=ds[position];
                    getScore(date);
                }else {
                    Toast.makeText(context,"请选择日期",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context,"请选择日期",Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(date!=null){
                    getScore(date);
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(context,"请选择日期",Toast.LENGTH_SHORT).show();}
            }
        });
        listView=(ListView)findViewById(R.id.listview);
        adapter=new ScoreAdapter(scores);
        listView.setAdapter(adapter);
    }
    public void getData(){
        startYear=Integer.parseInt(userInfo.getStudentnum().substring(0,4));
        int year=startYear;
        endYear= Calendar.getInstance().get(Calendar.YEAR);
        dates=new String[(endYear-startYear+1)*2];
        ds=new String[(endYear-startYear+1)*2-1];
        for(int i=1;i<=ds.length;i++){
            if(i%2==0){
                year++;
                dates[i-1]=year+"年上半年";
                ds[i-1]=year+"01";
            }else{
                dates[i-1]=year+"年下半年";
                ds[i-1]=year+"02";
            }

        }
        dates[dates.length-1]="请选择日期";
        for(int i=0;i<dates.length;i++){
            Log.i("asd",dates[i]);
        }

    }
    //初始化toolbar
    public void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setBackgroundColor(Theme.MainColor);
    }
    public void getScore(final String date){
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        scores.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray(mUrl.retData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject conJson=jsonArray.getJSONObject(i);
                            Score score=new Score();
                            score.setProject(conJson.getString(Score.PROJECT));
                            score.setGrades(conJson.getString(Score.GRADES));
                            score.setDate(conJson.getString(Score.YEAR));
                            scores.add(score);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(context,jsonObject.getString(mUrl.retMessage),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context,"请检测网络连接或尝试刷新",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("infoId","24");
                params.put("role",userInfo.getRole());
                params.put("semester",date);
                params.put("students_number",userInfo.getStudentnum());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }
}
