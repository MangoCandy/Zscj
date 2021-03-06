package com.hnxind.fankui;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Act_Fankui extends AppCompatActivity {
    EditText editText;
    Context context=this;
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfo=(new Utils_user(this)).getUserInfo();
        setContentView(R.layout.activity_fankui);
        initToolbar();
        initView();
    }
    public void initView(){
        editText=(EditText)findViewById(R.id.edittext);
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

    public void post(View view) {
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
        }
        if(editText.getText().length()<5){
            Toast.makeText(context,"内容不能少于五个字",Toast.LENGTH_SHORT).show();
        }else{
            RequestQueue requestQueue= Volley.newRequestQueue(context);
            StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("asd",response);
                    progressDialog.dismiss();
                    try {
                        JSONObject object=new JSONObject(response);
                        if(object.getString(mUrl.retCode).equals("000")){
                            editText.setText("");
                        }
                        Toast.makeText(context,object.getString(mUrl.retMessage),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"请检测网络连接或重新提交",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<>();
                    params.put("infoId","22");
                    params.put("role",userInfo.getRole());
                    params.put("card_id",userInfo.getIdCard());
                    params.put("content",editText.getText().toString());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
            initProgress();
        }
    }
    ProgressDialog progressDialog;
    public void initProgress(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("正在提交...");
        }
        progressDialog.show();
    }
}
