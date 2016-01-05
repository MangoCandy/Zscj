package com.hnxind.rollManager.Act_Kebiao;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.Kebiao;
import com.hnxind.model.Tuition;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Kebiao extends AppCompatActivity {
    Context context=this;
    UserInfo userInfo;
    String date;
    ExpandableListView expandableListView;
    Map<String,List<Kebiao>> kebiaoMap=new HashMap<String,List<Kebiao>>();
    String[] parent=new String[7];
    KebiaoAdapter adapter;
    Spinner spinner;
    String[] types=new String[]{"上周课表","本周课表","下周课表"};
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebiao);
        userInfo=(new Utils_user(context)).getUserInfo();
        initToolbar();
        initView();
    }
    public void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getKebiao();
            }
        });
        swipeRefreshLayout.setProgressViewOffset(false, 0, 30);
        swipeRefreshLayout.setRefreshing(true);

        spinner=(Spinner)findViewById(R.id.spiner);
        spinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,types));
        spinner.setSelection(1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getDate(position);
                getKebiao();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        expandableListView=(ExpandableListView)findViewById(R.id.kebiaoView);
        adapter=new KebiaoAdapter(kebiaoMap,parent);
        expandableListView.setAdapter(adapter);
    }
    public void initToolbar(){
        Theme theme=new Theme(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setTitleTextColor(theme.getTitleColor());
        toolbar.setBackgroundColor(theme.getMainColor());
    }
    public void getDate(int type){//type?上本下周？
        int i=0;
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        i=type-1; //根据选择星期变更i
        calendar.add(Calendar.WEEK_OF_YEAR,i);
        date=format.format(calendar.getTime());
    }
    public void getKebiao(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.i("asd",response);
                    kebiaoMap.clear();
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        JSONObject jsonObject1=jsonObject.getJSONObject(mUrl.retData);
                        JSONArray jsonArray=jsonObject1.names();
                        for(int i=0;i<jsonArray.length();i++){
                            JSONArray jsonArray1=jsonObject1.getJSONArray(jsonArray.getString(i));
                            List<Kebiao> kebiaos=new ArrayList<>();
                            for(int s=0;s<jsonArray1.length();s++){
                                JSONObject object=jsonArray1.getJSONObject(s);
                                Kebiao kebiao=new Kebiao();
                                kebiao.setKtmc(object.optString(Kebiao.KTMC));
                                kebiao.setSjd(object.optString(Kebiao.SJD));
                                kebiao.setTeacher(object.optString(Kebiao.TEACHER));
                                kebiao.setXq(object.optString(Kebiao.XQ));
                                kebiao.setZs(object.optString(Kebiao.ZS));
                                kebiao.setKcmc(object.optString(kebiao.KCMC));
                                kebiaos.add(kebiao);
                            }
                            parent[i]=jsonArray.getString(i)+getXq(jsonArray.getString(i));
                            kebiaoMap.put(parent[i],kebiaos);
                        }
                        Arrays.sort(parent);
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
                kebiaoMap.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(context,"请检测网络连接或尝试刷新",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("infoId","30");
                params.put("role",userInfo.getRole());
                params.put("students_number",userInfo.getStudentnum());
                params.put("date_time",date);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public String getXq(String date){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String xq=calendar.get(Calendar.DAY_OF_WEEK)-1+"";
        xq=xq.replace("1","一")
                .replace("2","二")
                .replace("3","三")
                .replace("4","四")
                .replace("5","五")
                .replace("6","六")
                .replace("0","天");
        return "  星期："+xq;
    }
}
