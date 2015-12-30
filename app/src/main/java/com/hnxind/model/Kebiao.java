package com.hnxind.model;

/**
 * Created by mangocandy on 15-12-30.
 */
public class Kebiao {
    public static String KTMC="课室名称";
    public static String KCMC="课程名称";
    public static String JC="节次";
    public static String ZS="周数";
    public static String XQ="星期";
    public static String SJD="上课时间段";
    public static String TEACHER="任课老师";

    private String ktmc;
    private String sjd;
    private String zs;
    private String xq;
    private String teacher;
    private String kcmc;

    public String getKcmc() {
        return kcmc;
    }

    public void setKcmc(String kcmc) {
        this.kcmc = kcmc;
    }

    public String getKtmc() {
        return ktmc;
    }

    public void setKtmc(String ktmc) {
        this.ktmc = ktmc;
    }

    public String getSjd() {
        return sjd;
    }

    public void setSjd(String sjd) {
        this.sjd = sjd;
    }

    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
