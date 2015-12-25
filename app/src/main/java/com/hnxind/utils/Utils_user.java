package com.hnxind.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.hnxind.model.UserInfo;

/**
 * Created by Administrator on 2015/12/22.
 */
public class Utils_user {
    Context context;
    public Utils_user(Context context){
        this.context=context;
    }

    public void saveUserInfo(UserInfo userInfo){
        SharedPreferences sharedPreferences=context.getSharedPreferences(UserInfo.SpUser,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(UserInfo.IDCARD,userInfo.getIdCard());
        editor.putString(UserInfo.NAME,userInfo.getName());
        editor.putString(UserInfo.ROLE,userInfo.getRole());
        editor.putString(UserInfo.STUDENT_NUM,userInfo.getStudentnum());
        editor.commit();
    }
    public UserInfo getUserInfo(){//获取用户信息
        UserInfo userInfo=new UserInfo();
        SharedPreferences sharedPreferences=context.getSharedPreferences(UserInfo.SpUser,Context.MODE_PRIVATE);
        userInfo.setIdCard(sharedPreferences.getString(UserInfo.IDCARD,""));
        userInfo.setName(sharedPreferences.getString(UserInfo.NAME,""));
        userInfo.setRole(sharedPreferences.getString(UserInfo.ROLE,""));
        userInfo.setStudentnum(sharedPreferences.getString(UserInfo.STUDENT_NUM,""));
        return userInfo;
    }

    public void clearUserInfo(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(UserInfo.SpUser,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
