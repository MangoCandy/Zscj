package com.hnxind.model;

/**
 * Created by Administrator on 2015/12/31.
 */
public class JieyueLS {
    public static String TITLE="title";
    public static String BOOK_BARCODE="book_barcode";//书籍条码
    public static String DATE_CHECKOUT="date_checkout";//借书日期
    public static String DATE_DUE="date_due";//到期日期
    public static String CIRCUL_STATUS="circul_status";//图书状态

    private String title;
    private String bookBarcode;
    private String dateCheckout;
    private String dateDue;
    private String circulStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookBarcode() {
        return bookBarcode;
    }

    public void setBookBarcode(String bookBarcode) {
        this.bookBarcode = bookBarcode;
    }

    public String getDateCheckout() {
        return dateCheckout;
    }

    public void setDateCheckout(String dateCheckout) {
        this.dateCheckout = dateCheckout;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getCirculStatus() {
        return circulStatus;
    }

    public void setCirculStatus(String circulStatus) {
        this.circulStatus = circulStatus;
    }
}
