package com.hnxind.newStudent.BaoDao;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Act_Sushe extends AppCompatActivity {
    Context context=this;
    UserInfo userInfo;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sushe);
        userInfo=(new Utils_user(this)).getUserInfo();
        initToolbar();
        initView();

        getSushe();
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

    public void initView(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSushe();
            }
        });
//        swipeRefreshLayout.setProgressViewOffset(false, 0, 60);
//        swipeRefreshLayout.setRefreshing(true);
    }
    public void getSushe(){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.i("asd",response+"1");
                try {
                    JSONObject js=new JSONObject(response);
                    if(js.getString(mUrl.retCode).equals("00")){
                        js=js.getJSONObject(mUrl.retData);
                        String house=js.getString("house_name");
                        String room=js.getString("room_no");
                        setView(house,room);
                    }else{
                        Toast.makeText(context,js.getString(mUrl.retMessage),Toast.LENGTH_SHORT).show();
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
                params.put("infoId","29");
                params.put("role",userInfo.getRole());
                params.put("card_id",userInfo.getIdCard());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setView(String house,String room){
        ((TextView)findViewById(R.id.house)).setText("宿舍楼栋："+house);
        ((TextView)findViewById(R.id.room)).setText("宿舍房间："+room);
    }
}
