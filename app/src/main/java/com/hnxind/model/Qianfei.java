package com.hnxind.model;

/**
 * Created by Administrator on 2015/12/28.
 */
public class Qianfei {
    public static String READER_BARCODE="reader_barcode";//读者编码
    public static String LIBRARY_ID="library_id";//所在图书馆
    public static String READER_NAME="reader_name";//读者姓名
    public static String IDENT_ID="ident_id";//学工号
    public static String GENDER="reader_gender";//性别
    public static String ECARD_CODE="ecard_code";//卡号1
    public static String CARDID="cardid";//卡号2
    public static String DEBT="debt";//欠款金额
    public static String CARD_START_DATE="card_start_date";//办卡日期
    public static String CARD_STaTUS="card_status";//卡状态
    public static String CARD_DUE_DATE="card_due_date";//失效日期
    public static String NATIONAL_ID="national_id";//身份证
    public static String ECARD_ACCOUNTID="ecard_accountid";//账号1
    public static String ECARD_ACCOUNTID2="ecard_accountid2";//账号2
    public static String WORKPLACE_DESC="workplace_desc";//工作单位
    public static String READERTYPE_DESC="readertype_desc";//读者身份
    public static String KJ="kj";//可借数量
    public static String YJ="yj";//已借

    private String reader_barcode;
    private String library_id;
    private String reader_name;
    private String ident_id;
    private String gender;
    private String ecard_code;
    private String ecardid;
    private String debt;
    private String card_start_date;
    private String cardstatus;
    private String card_due_date;
    private String national_id;
    private String ecard_accountid;
    private String ecard_accountid2;
    private String workplace;
    private String readertype;
    private String kj;
    private String yj;

    public String getReader_barcode() {
        return reader_barcode;
    }

    public void setReader_barcode(String reader_barcode) {
        this.reader_barcode = reader_barcode;
    }

    public String getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(String library_id) {
        this.library_id = library_id;
    }

    public String getReader_name() {
        return reader_name;
    }

    public void setReader_name(String reader_name) {
        this.reader_name = reader_name;
    }

    public String getIdent_id() {
        return ident_id;
    }

    public void setIdent_id(String ident_id) {
        this.ident_id = ident_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEcard_code() {
        return ecard_code;
    }

    public void setEcard_code(String ecard_code) {
        this.ecard_code = ecard_code;
    }

    public String getEcardid() {
        return ecardid;
    }

    public void setEcardid(String ecardid) {
        this.ecardid = ecardid;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public String getCard_start_date() {
        return card_start_date;
    }

    public void setCard_start_date(String card_start_date) {
        this.card_start_date = card_start_date;
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus;
    }

    public String getCard_due_date() {
        return card_due_date;
    }

    public void setCard_due_date(String card_due_date) {
        this.card_due_date = card_due_date;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getEcard_accountid() {
        return ecard_accountid;
    }

    public void setEcard_accountid(String ecard_accountid) {
        this.ecard_accountid = ecard_accountid;
    }

    public String getEcard_accountid2() {
        return ecard_accountid2;
    }

    public void setEcard_accountid2(String ecard_accountid2) {
        this.ecard_accountid2 = ecard_accountid2;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getReadertype() {
        return readertype;
    }

    public void setReadertype(String readertype) {
        this.readertype = readertype;
    }

    public String getKj() {
        return kj;
    }

    public void setKj(String kj) {
        this.kj = kj;
    }

    public String getYj() {
        return yj;
    }

    public void setYj(String yj) {
        this.yj = yj;
    }
}
