package com.hnxind.model;

/**
 * Created by Administrator on 2015/12/22.
 */
public class UserInfo {//用户信息
    public static String SpUser="zscjUser";//sp表名

    public static String IDCARD="card_id";
    public static String ROLE="role";
    public static String NAME="name";
    public static String STUDENT_NUM="studentsNumber";

    private String idCard;
    private String role;
    private String name;
    private String studentnum;

    public String getStudentnum() {
        return studentnum;
    }

    public void setStudentnum(String studentnum) {
        this.studentnum = studentnum;
    }

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
