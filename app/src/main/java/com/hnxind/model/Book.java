package com.hnxind.model;

/**
 * Created by Administrator on 2015/12/28.
 */
public class Book {
    public static String TITLE="title";
    public static String AUTHOR="author";
    public static String PUBILSHER="publisher";

    private String title;
    private String author;
    private String publisher;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
