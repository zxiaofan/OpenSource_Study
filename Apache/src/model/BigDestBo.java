/*
 * 文件名：modelBigSrc.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： modelBigSrc.java
 * 修改人：yunhai
 * 修改时间：2015年12月4日
 * 修改内容：新增
 */
package model;

import java.util.Date;

/**
 * 
 * @author yunhai
 */
public class BigDestBo {
    private Date birth_date;

    private String doc_id;

    private int doc_type;

    private String passenger_name;

    private Integer passenger_type;

    private String ticket_no;

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public int getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(int doc_type) {
        this.doc_type = doc_type;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public Integer getPassenger_type() {
        return passenger_type;
    }

    public void setPassenger_type(Integer passenger_type) {
        this.passenger_type = passenger_type;
    }

    public String getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

}
