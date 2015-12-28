package com.hnxind.CrpSystem.Act_Information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.CrpInformation;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.utils.Act_WebView;
import com.hnxind.utils.SwiperefreshWithLoadMore;
import com.hnxind.utils.Utils_user;
import com.hnxind.utils.mListView;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Information extends AppCompatActivity {
    Context context=this;
    UserInfo userInfo;
    int page=0;
    ListView listView;
    SwiperefreshWithLoadMore swiperefreshWithLoadMore;
    List<CrpInformation> crpInformations=new ArrayList<>();
    CrpInformationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        userInfo=(new Utils_user(context)).getUserInfo();
        initToolbar();
        initView();
        getinformation(false);
    }
    public void initView(){
        listView=(ListView) findViewById(R.id.informationView);
        adapter=new CrpInformationAdapter(crpInformations,context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRead(crpInformations.get(position).getContent(),crpInformations.get(position).getTitle());
            }
        });

        swiperefreshWithLoadMore=(SwiperefreshWithLoadMore)findViewById(R.id.refresh);
        swiperefreshWithLoadMore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getinformation(true);
            }
        });
        swiperefreshWithLoadMore.setOnLoadListener(new SwiperefreshWithLoadMore.OnLoadListener() {
            @Override
            public void load() {
                getinformation(false);
            }
        });

    }

    public void openRead(String content,String title){
        Intent intent=new Intent(context, Act_WebView.class);
        intent.putExtra("content",content);
        intent.putExtra("title",title);
        startActivity(intent);
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
    public void getinformation(final boolean isRefresh){//获取信息
        swiperefreshWithLoadMore.setRefreshing(true);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    swiperefreshWithLoadMore.setRefreshing(false);
                    swiperefreshWithLoadMore.setLoadingMore(false);
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        if(isRefresh){crpInformations.clear();}//刷新清空list
                        page=isRefresh?1:++page;//刷新页面回到1
                        JSONArray jsonArray=jsonObject.getJSONArray(mUrl.retData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject object=jsonArray.getJSONObject(i);
                            CrpInformation crpInformation=new CrpInformation();
                            crpInformation.setTitle(object.getString("title"));
                            crpInformation.setContent(object.getString("content"));
                            crpInformation.setSend_name(object.getString("sender_name"));
                            crpInformation.setCreate_date(object.getString("create_time"));
                            crpInformations.add(crpInformation);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        if(jsonObject.getString(mUrl.retMessage).equals("无内容")){
                            swiperefreshWithLoadMore.setOnLoadListener(null);
                        }
                        Toast.makeText(context,jsonObject.getString(mUrl.retMessage),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swiperefreshWithLoadMore.setRefreshing(false);
                swiperefreshWithLoadMore.setLoadingMore(false);
                Toast.makeText(context,"请检查网络设置",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("infoId","31");
                if(isRefresh){
                    params.put("count","0");
                }else{
                    params.put("count",page+"");
                }
                params.put("studNo",userInfo.getStudentnum());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

}
