package com.hnxind.rollManager.Act_Contacts;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Contacts extends AppCompatActivity {
    RecyclerView contactsView;//联系人列表视图
    ContactsAdapter contactsAdapter;
    Context context=this;
    List<Contact> contacts=new ArrayList<Contact>();
    TextView className;//班级名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getContacts();
        initView();
        initToolbar();

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
        contactsView=(RecyclerView)findViewById(R.id.contacts_recycleview);
        contactsView.setLayoutManager(new LinearLayoutManager(this));
        contactsAdapter=new ContactsAdapter(contacts);
        contactsView.setAdapter(contactsAdapter);

        className=(TextView)findViewById(R.id.classname);
    }
    //获取联系人
    public void getContacts(){
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
                            JSONObject conJson=jsonArray.getJSONObject(i);
                            Contact contact=new Contact();
                            contact.setName(conJson.getString("name"));
                            contact.setNum(conJson.getString("telephone"));
                            contact.setMclass(conJson.getString("class"));
                            contacts.add(contact);
                        }
                        contactsAdapter.notifyDataSetChanged();
                        className.setText(contacts.get(0).getMclass());
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
                Toast.makeText(context,"请检测网络连接",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                UserInfo userInfo= (new Utils_user(context)).getUserInfo();
                Map<String,String> params=new HashMap<>();
                params.put("infoId","28");
                params.put("role","0");
                params.put("students_number",userInfo.getStudentnum());
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    //排序
    public void paixu(){
        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

    }
}
