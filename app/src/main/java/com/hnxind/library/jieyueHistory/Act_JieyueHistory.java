package com.hnxind.library.jieyueHistory;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.JieyueLS;
import com.hnxind.model.Qianfei;
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

public class Act_JieyueHistory extends AppCompatActivity {
    Context context=this;
    SwipeRefreshLayout swipeRefreshLayout;
    ExpandableListView listView;
    Map<String,JieyueLS> jieyueLSMap=new HashMap<>();
    List<String> books=new ArrayList<>();
    JieyueAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jieyue_history);
        initView();
        initToolbar();
        getHistory();
    }

    public void initView(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHistory();
            }
        });

        listView=(ExpandableListView)findViewById(R.id.jieyueView);
        adapter=new JieyueAdapter(jieyueLSMap,books);
        listView.setAdapter(adapter);
    }
    public void initToolbar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getHistory(){
        swipeRefreshLayout.setRefreshing(true);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jieyueLSMap.clear();
                    books.clear();
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        JSONArray jsonArray=jsonObject.getJSONArray(mUrl.retData);
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject=jsonArray.getJSONObject(i);
                            JieyueLS jieyueLS=new JieyueLS();
                            jieyueLS.setBookBarcode(jsonObject.getString(JieyueLS.BOOK_BARCODE));
                            jieyueLS.setCirculStatus(jsonObject.getString(JieyueLS.CIRCUL_STATUS));
                            jieyueLS.setDateCheckout(jsonObject.getString(JieyueLS.DATE_CHECKOUT));
                            jieyueLS.setDateDue(jsonObject.getString(JieyueLS.DATE_DUE));
                            jieyueLS.setTitle(jsonObject.getString(JieyueLS.TITLE));
                            books.add("《"+jieyueLS.getTitle()+"》");
                            jieyueLSMap.put(books.get(i),jieyueLS);
                        }
                    }else{
                        Toast.makeText(context,jsonObject.getString(mUrl.retMessage),Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
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
                UserInfo userInfo= (new Utils_user(context)).getUserInfo();
                Map<String,String> params=new HashMap<>();
                params.put("infoId","25");
                params.put("role",userInfo.getRole());
                params.put("students_number",userInfo.getStudentnum());
                params.put("card_id",userInfo.getIdCard());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }
}
