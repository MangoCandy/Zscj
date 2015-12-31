package com.hnxind.zscj;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.Grid;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.utils.Utils_user;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Login extends AppCompatActivity {
    EditText username;//登录用户名
    EditText password;//登陆密码
    String usernameText;//用户名
    String passwordText;//密码
    Utils_user utils_user=new Utils_user(this);//用户工具类
    List<Grid> grids= new ArrayList<Grid>();

    CheckBox loginAuto;//自动登录
    CheckBox remember;//记住密码

    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getCheck();//获取选中状态
        if(remember.isChecked()){
            getLoginInfo();
            if(loginAuto.isChecked()){
                login(null);
            }
        }

    }

    public void initView(){
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        loginAuto=(CheckBox)findViewById(R.id.loginAuto);
        loginAuto.setOnClickListener(onClickListener);
        remember=(CheckBox)findViewById(R.id.remember);
        remember.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.loginAuto:
                    if(loginAuto.isChecked()&&!remember.isChecked()){//选中自动登陆后 连带选中记住密码
                        remember.setChecked(true);
                    }
                    break;
                case R.id.remember:
                    if(!remember.isChecked()){
                        loginAuto.setChecked(false);
                    }
                    break;
            }
        }
    };
    //Volley请求登陆
    public void login(View view){
        initProgress();//加载圈

        usernameText=username.getText().toString();
        passwordText=password.getText().toString();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.loginUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    String code=jsonObject.getString(mUrl.retCode);
                    if(code.equals("000")){
                        jsonObject=jsonObject.getJSONObject(mUrl.retData);
                        UserInfo userInfo=new UserInfo();
                        userInfo.setRole(jsonObject.getString(UserInfo.ROLE));
                        userInfo.setName(jsonObject.getString(UserInfo.NAME));
                        userInfo.setIdCard(jsonObject.getString(UserInfo.IDCARD));
                        userInfo.setStudentnum(usernameText);
                        utils_user.saveUserInfo(userInfo);//保存用户信息
                        saveCheck();//保存checkbox状态
                        if(remember.isChecked()){
                            saveLoginInfo(usernameText,passwordText);
                        }
                        gotoMain(jsonObject.getJSONArray("grid"));
                        finish();
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
                progressDialog.dismiss();
                Toast.makeText(context,"请检查网络连接或重新请求登陆...",Toast.LENGTH_SHORT).show();
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
    private void gotoMain(JSONArray jsonArray){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("grid",jsonArray.toString());
        startActivity(intent);
    }

    public void saveCheck(){
        SharedPreferences sharedPreferences=getSharedPreferences("zscj",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("loginAuto",loginAuto.isChecked());
        editor.putBoolean("remember",remember.isChecked());
        editor.commit();
    }
    public void getCheck(){//check状态获取
        SharedPreferences sharedPreferences=getSharedPreferences("zscj",MODE_PRIVATE);
        loginAuto.setChecked(sharedPreferences.getBoolean("loginAuto",false));
        remember.setChecked(sharedPreferences.getBoolean("remember",false));
    }

    private void saveLoginInfo(String username,String password){//保存密码等
        SharedPreferences sharedPreferences=getSharedPreferences("zscj",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }
    public void getLoginInfo(){//check状态获取
        SharedPreferences sharedPreferences=getSharedPreferences("zscj",MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username",""));
        password.setText(sharedPreferences.getString("password",""));
    }
    ProgressDialog progressDialog;
    public void initProgress(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("正在请求登陆...");
        }
        progressDialog.show();
    }
}
