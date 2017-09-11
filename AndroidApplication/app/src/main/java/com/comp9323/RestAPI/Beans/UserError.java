package com.comp9323.RestAPI.Beans;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserError implements Serializable
{

    @SerializedName("detail")
    @Expose
    private String detail;
    private final static long serialVersionUID = 2204497690867606834L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserError() {
    }

    /**
     *
     * @param detail
     */
    public UserError(String detail) {
        super();
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public UserError withDetail(String detail) {
        this.detail = detail;
        return this;
    }

}