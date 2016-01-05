package com.hnxind.library;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hnxind.library.book.BookAdapter;
import com.hnxind.model.Book;
import com.hnxind.model.UserInfo;
import com.hnxind.model.mUrl;
import com.hnxind.setting.Act_Setting;
import com.hnxind.setting.Theme;
import com.hnxind.utils.Utils_user;
import com.hnxind.zscj.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_Book extends AppCompatActivity {
    Context context=this;
    List<Book> books=new ArrayList<>();
    RecyclerView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    BookAdapter adapter;
    String type="01";//搜索依据
    EditText editText;
    Spinner spinner;
    String[]types=new String[]{"书名","作者"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initView();
        initToolbar();
    }

    public void initToolbar(){
        Theme theme=new Theme(this);

        AppBarLayout appBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        appBarLayout.setBackgroundColor(theme.MainColor);

        CollapsingToolbarLayout toolbarLayout;
        toolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        toolbarLayout.setCollapsedTitleTextColor(theme.TitleColor);
        toolbarLayout.setContentScrimColor(theme.MainColor);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(theme.TitleColor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void initView(){
        spinner=(Spinner)findViewById(R.id.spiner);
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.single_spiner_types,types));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type="0"+(position+1);
                Log.i("asd",type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText=(EditText)findViewById(R.id.edit_book);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    getDate(null);
                }
                return false;
            }
        });

        listView=(RecyclerView) findViewById(R.id.listview);
        adapter=new BookAdapter(books,context);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setStackFromEnd(false);
        listView.setLayoutManager(manager);
        listView.setAdapter(adapter);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDate(null);
            }
        });
    }
    public void getDate(View view){
        swipeRefreshLayout.setRefreshing(true);

        final String t=editText.getText().toString();
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if(imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
        }

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, mUrl.gridUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                books.clear();//首先清空资源
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.i("asd",response);
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString(mUrl.retCode).equals("00")){
                        JSONArray jsonArray=jsonObject.getJSONArray(mUrl.retData);
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject=jsonArray.getJSONObject(i);
                            Book book=new Book();
                            book.setTitle("书名：《"+jsonObject.getString(Book.TITLE)+"》");
                            book.setAuthor("作者："+jsonObject.getString(Book.AUTHOR));
                            book.setPublisher("出版商："+jsonObject.getString(Book.PUBILSHER).replace("null","不详"));
                            books.add(book);
                        }
                    }else{
                        Toast.makeText(context,jsonObject.getString(mUrl.retMessage),Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                    listView.scrollToPosition(0);
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
                params.put("infoId","27");
                params.put("role",userInfo.getRole());
                params.put("students_number",userInfo.getStudentnum());
                params.put("card_id",userInfo.getIdCard());
                params.put("keyword",t);
                params.put("seach_type",type);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
