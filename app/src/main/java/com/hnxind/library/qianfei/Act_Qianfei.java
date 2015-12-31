package com.hnxind.library.qianfei;

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
import com.hnxind.model.Contact;
import com.hnxind.model.Qianfei;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Act_Qianfei extends AppCompatActivity {
    Context context=this;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qianfei);
        initView();
        initToolbar();
        getqianfei();
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
    }
    public void initView(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getqianfei();
            }
        });
    }
    public void getqianfei(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        jsonObject=jsonObject.getJSONObject(mUrl.retData);
                        Qianfei qianfei=new Qianfei();
                        qianfei.setReader_barcode(jsonObject.getString(Qianfei.READER_BARCODE));
                        qianfei.setLibrary_id(jsonObject.getString(Qianfei.LIBRARY_ID));
                        qianfei.setReader_name(jsonObject.getString(Qianfei.READER_NAME));
                        qianfei.setGender(jsonObject.getString(Qianfei.GENDER));
                        qianfei.setIdent_id(jsonObject.getString(Qianfei.IDENT_ID));
                        qianfei.setEcard_code(jsonObject.getString(Qianfei.ECARD_CODE));
                        qianfei.setEcardid(jsonObject.getString(Qianfei.CARDID));
                        qianfei.setDebt(jsonObject.getString(Qianfei.DEBT));
                        qianfei.setCard_start_date(jsonObject.getString(Qianfei.CARD_START_DATE));
                        qianfei.setCardstatus(jsonObject.getString(Qianfei.CARD_STaTUS));
                        qianfei.setCard_due_date(jsonObject.getString(Qianfei.CARD_DUE_DATE));
                        qianfei.setNational_id(jsonObject.getString(Qianfei.NATIONAL_ID));
                        qianfei.setEcard_accountid(jsonObject.getString(Qianfei.ECARD_ACCOUNTID));
                        qianfei.setEcard_accountid2(jsonObject.getString(Qianfei.ECARD_ACCOUNTID2));
                        qianfei.setWorkplace(jsonObject.getString(Qianfei.WORKPLACE_DESC));
                        qianfei.setReadertype(jsonObject.getString(Qianfei.READERTYPE_DESC));
                        qianfei.setKj(jsonObject.getString(Qianfei.KJ));
                        qianfei.setYj(jsonObject.getString(Qianfei.YJ));

                        setView(qianfei);
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
                UserInfo userInfo= (new Utils_user(context)).getUserInfo();
                Map<String,String> params=new HashMap<>();
                params.put("infoId","26");
                params.put("role",userInfo.getRole());
                params.put("students_number",userInfo.getStudentnum());
                params.put("card_id",userInfo.getIdCard());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public void setView(Qianfei qianfei){
        ((TextView)findViewById(R.id.card_start)).setText(qianfei.getCard_start_date());
        ((TextView)findViewById(R.id.card_due)).setText(qianfei.getCard_due_date());
        ((TextView)findViewById(R.id.qianfei_text)).setText(qianfei.getDebt()+"元");
        ((TextView)findViewById(R.id.kj_text)).setText(qianfei.getKj()+"本");
        ((TextView)findViewById(R.id.yj_text)).setText((qianfei.getYj().replace("null","0"))+"本");

    }
}
