package com.hnxind.model;

/**
 * Created by Administrator on 2015/12/22.
 */
public class UserInfo {
    public static String SpUser="zscjUser";//sp表名

    public static String IDCARD="card_id";
    public static String ROLE="role";
    public static String NAME="name";

    private String idCard;
    private String role;
    private String name;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
