package com.hnxind.fankui;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.model.mUrl;
import com.hnxind.zscj.R;

public class Act_Fankui extends AppCompatActivity {
    EditText editText;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fankui);
        initToolbar();
        initView();
    }
    public void initView(){
        editText=(EditText)findViewById(R.id.edittext);
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
                    progressDialog.dismiss();
                    editText.setText("");
                    Toast.makeText(context,"提交成功，谢谢你的反馈",Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(context,"请检测网络连接或尝试刷新",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
            requestQueue.start();
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
