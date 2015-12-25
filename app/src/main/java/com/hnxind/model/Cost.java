package com.hnxind.model;

/**
 * Created by Administrator on 2015/12/25.
 */
public class Cost {
    public static String LOCATION="termname";
    public static String DESCRIPTION="dscrp";
    public static String ODDFARE="oddfare";//余额
    public static String TIME="opdt";
    public static String COST="opfare";//支出

    public String location;//消费地点
    public String description;
    public String yue;
    public String time;
    public String cost;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYue() {
        return yue;
    }

    public void setYue(String yue) {
        this.yue = yue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
