package com.hnxind.schoolCard.Act_CostList;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.Cost;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

public class Act_CostList extends AppCompatActivity {
    Context context=this;
    UserInfo userInfo;
    ScrollView dateLayout;//选择时间容器
    LinearLayout listLayout;//数据展示容器
    DatePicker datePicker;//日期选择器
    ListView listView;//你懂的
    String date;
    List<Cost> costList=new ArrayList<>();
    CostAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_list);
        userInfo=(new Utils_user(context)).getUserInfo();
        initToolbar();
        initView();
    }

    public void initView(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCostList(date);
            }
        });

        dateLayout=(ScrollView)findViewById(R.id.datelayout);
        datePicker=(DatePicker)findViewById(R.id.datepicker);

        listLayout=(LinearLayout)findViewById(R.id.listLayout);
        listView=(ListView)findViewById(R.id.informationView);
        adapter=new CostAdapter(costList,this);
        listView.setAdapter(adapter);

    }
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
    }

    public void getCostList(final String date){
        listView.removeHeaderView(LayoutInflater.from(context).inflate(R.layout.foot_layout,null));
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
                        costList.clear();
                        JSONObject object=jsonObject.getJSONObject(mUrl.retData);
                        JSONArray jsonArray=object.getJSONArray("consume");
                        if(jsonArray.length()>1){
                            for(int i=0;i<jsonArray.length();i++){
                                object=jsonArray.getJSONObject(i);
                                Cost cost=new Cost();
                                cost.setCost(object.getString(Cost.COST));
                                cost.setDescription(object.getString(Cost.DESCRIPTION));
                                cost.setLocation(object.getString(Cost.LOCATION));
                                cost.setTime(object.getString(Cost.TIME));
                                cost.setYue(object.getString(Cost.ODDFARE));
                                costList.add(cost);
                            }
                        }else{
                            listView.addHeaderView(LayoutInflater.from(context).inflate(R.layout.foot_layout,null));
                            Toast.makeText(context,"当天无消费记录",Toast.LENGTH_SHORT).show();
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
                costList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(context,"请检测网络连接",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                UserInfo userInfo= (new Utils_user(context)).getUserInfo();
                Map<String,String> params=new HashMap<>();
                params.put("infoId","18");
                params.put("role",userInfo.getRole());
                params.put("card_id",userInfo.getIdCard());
                params.put("name",userInfo.getName());
                params.put("date",date);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public void confirm(View view) {//确认时间
        dateLayout.setVisibility(View.GONE);
        listLayout.setVisibility(View.VISIBLE);
        date=datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+(datePicker.getDayOfMonth());
        Log.i("asds",date);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d=formatter.parse(date);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Log.i("asd",d+"");
            d=calendar.getTime();
            date=formatter.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("asdq",date);
        getCostList(date);


    }

    public void choiceDate(View view) {//选择日期
        dateLayout.setVisibility(View.VISIBLE);
        listLayout.setVisibility(View.GONE);
    }
}
