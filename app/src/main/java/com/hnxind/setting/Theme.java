package com.hnxind.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;

/**
 * Created by mangocandy on 16-1-3.
 */
public class Theme {
    public static final String THEME_SP="theme";
    public static final String MAIN_COLOR="maincolor";
    public static final String TITLE_COLOR="titlecolor";
    public static final String CHANGE_THEME="com.hnxind.changetheme";
    public static final String COLOR_NAME="colorname";

    public static final String DEFAULT_TITLE_COLOR="#48809d";
    Context context;
    public Theme(Context context){
        this.context=context;
        initTheme();
    }

    public void initTheme(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(Theme.THEME_SP,context.MODE_PRIVATE);
        int maincolor=sharedPreferences.getInt(Theme.MAIN_COLOR,0);
        if(maincolor!=0){
            setMainColor(maincolor);
            setTitleColor(sharedPreferences.getInt(Theme.TITLE_COLOR, Color.parseColor("#48809d")));
        }
    }
    public int MainColor= Color.parseColor("#00bcd4");
    public int TitleColor= Color.parseColor("#48809d");

    public int getMainColor() {
        return MainColor;
    }

    public void setMainColor(int mainColor) {
        MainColor = mainColor;
    }

    public int getTitleColor() {
        return TitleColor;
    }

    public void setTitleColor(int titleColor) {
        TitleColor = titleColor;
    }

    public ColorStateList getColorStateList(){
        ColorStateList colorStateList=null;
        int[][] states=new int[2][];
        states[0]=new int[]{android.R.attr.state_pressed};
        states[1]=new int[]{};
        int[] colors=new int[]{MainColor+20,MainColor};
        colorStateList=new ColorStateList(states,colors);
        return colorStateList;
    }
}
