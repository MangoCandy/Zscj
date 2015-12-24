package com.hnxind.rollManager.Act_PayForStudy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.Contact;
import com.hnxind.model.Tuition;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_PayForStudy extends AppCompatActivity {
    UserInfo userInfo;
    Utils_user utils_user=new Utils_user(this);
    ListView payList;
    List<Tuition> tuitions=new ArrayList<>();
    TuitionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_for_study);
        userInfo=utils_user.getUserInfo();
        initToolbar();
        initView();
        getPaynum();
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
    public void initView(){
        payList=(ListView)findViewById(R.id.payList);
        adapter=new TuitionAdapter(tuitions,this);
        payList.setAdapter(adapter);
    }
    public void getPaynum(){//获取学费数据
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        JSONArray jsonArray=jsonObject.getJSONArray(mUrl.retData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject tuitionObject=jsonArray.getJSONObject(i);
                            Tuition tuition=new Tuition();
                            tuition.setTime(tuitionObject.getString("PERIOD_NAME"));
                            tuition.setName(tuitionObject.getString("ENTRY_NAME"));
                            tuition.setYingjiao(tuitionObject.getString("AMOUNT_YS"));
                            tuition.setShijiao(tuitionObject.getString("AMOUNT_SS"));
                            tuition.setQianfei(tuitionObject.getString("AMOUNT_QF"));
                            tuitions.add(tuition);
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
                Toast.makeText(context,"请检查网络设置",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("infoId","21");
                params.put("card_id",userInfo.getIdCard());
                params.put("role",userInfo.getRole());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }
    Context context=this;
}