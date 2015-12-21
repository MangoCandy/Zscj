package com.hnxind.zscj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.mUrl;

import java.util.HashMap;
import java.util.Map;

public class Act_Login extends AppCompatActivity {
    EditText username;//登录用户名
    EditText password;//登陆密码
    String usernameText;//用户名
    String passwordText;//密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView(){
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
    }
    //Volley请求登陆
    public void login(View view){
        usernameText=username.getText().toString();
        passwordText=password.getText().toString();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.loginUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("asd",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("asd",error+"");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",usernameText);
                params.put("password",passwordText);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        requestQueue.start();
    }
}
