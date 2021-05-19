package com.whpu.house.pojo;

import java.util.Date;

public class Certificate {

    Integer id;

    Integer user_id;

    Integer house_id;

    Date create_time;

    String url;

    String certificate_type;

    String author_role;

    Integer house_or_order_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getHouse_id() {
        return house_id;
    }

    public void setHouse_id(Integer house_id) {
        this.house_id = house_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getAuthor_role() {
        return author_role;
    }

    public void setAuthor_role(String author_role) {
        this.author_role = author_role;
    }

    public Integer getHouse_or_order_id() {
        return house_or_order_id;
    }

    public void setHouse_or_order_id(Integer house_or_order_id) {
        this.house_or_order_id = house_or_order_id;
    }
}
