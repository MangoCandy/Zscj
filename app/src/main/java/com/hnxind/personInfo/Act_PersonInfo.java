package com.hnxind.personInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import com.hnxind.model.CrpInformation;
import com.hnxind.model.StudentInfo;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Act_PersonInfo extends AppCompatActivity {
    Context context=this;
    UserInfo userInfo;
    StudentInfo studentInfo;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        userInfo=(new Utils_user(this)).getUserInfo();

        initToolbar();
        initView();
        getInfo();
    }

    CollapsingToolbarLayout toolbarLayout;
    public void initToolbar(){
        AppBarLayout appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        appBarLayout.setBackgroundColor(Theme.MainColor);

        toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        toolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
        toolbarLayout.setTitleEnabled(false);
        toolbarLayout.setContentScrimColor(Theme.MainColor);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        toolbar.setTitleTextColor(Theme.TitleColor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void initView(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo();
            }
        });
    }
    public void getInfo(){
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        final StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        jsonObject=jsonObject.getJSONObject(mUrl.retData);
                        studentInfo=new StudentInfo();
                        studentInfo.setIdCard(jsonObject.getString("card_id_no"));
                        studentInfo.setClasses(jsonObject.getString("class"));
                        studentInfo.setName(jsonObject.getString("name"));
                        studentInfo.setTel(jsonObject.getString("mobile"));
                        studentInfo.setStudent_no(jsonObject.getString("students_no"));
                        studentInfo.setStatus(jsonObject.getString("status"));
                        studentInfo.setSchool(jsonObject.getString("campus"));
                        studentInfo.setMajor(jsonObject.getString("major"));
                        studentInfo.setGrade(jsonObject.getString("grade"));
                        studentInfo.setType(jsonObject.getString("type"));
                        studentInfo.setEmail(jsonObject.getString("email").replace("null",""));
                        studentInfo.setDept(jsonObject.getString("dept"));
                        setView();
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
                Log.i("error",error+"");
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context,"请检查网络设置或尝试刷新",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("infoId","14");
                params.put("students_number",userInfo.getStudentnum());
                params.put("role",userInfo.getRole());
                params.put("card_id",userInfo.getIdCard());
                params.put("name",userInfo.getName());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void setView(){
        toolbarLayout.setTitle(studentInfo.getName());
        ((TextView)findViewById(R.id.name)).setText(studentInfo.getName());
        ((TextView)findViewById(R.id.idcard)).setText(studentInfo.getIdCard());
        ((TextView)findViewById(R.id.status)).setText(studentInfo.getStatus());
        ((TextView)findViewById(R.id.type)).setText(studentInfo.getType());
        ((TextView)findViewById(R.id.school)).setText(studentInfo.getSchool());
        ((TextView)findViewById(R.id.grade)).setText(studentInfo.getGrade());
        ((TextView)findViewById(R.id.major)).setText(studentInfo.getMajor());
        ((TextView)findViewById(R.id.dept)).setText(studentInfo.getDept());
        ((TextView)findViewById(R.id.email)).setText(studentInfo.getEmail());
        ((TextView)findViewById(R.id.tel)).setText(studentInfo.getTel());
        ((TextView)findViewById(R.id.studentNo)).setText(studentInfo.getStudent_no());
        ((TextView)findViewById(R.id.clas)).setText(studentInfo.getClasses());
    }

}
