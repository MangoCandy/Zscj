package com.hnxind.model;

/**
 * Created by mangocandy on 15-12-30.
 */
public class Score {
    public static String PROJECT="project";
    public static String GRADES="grades";
    public static String YEAR="year";

    private String project;
    private String grades;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
