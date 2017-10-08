package com.comp9323.data.beans;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserError implements Serializable {
    private final static long serialVersionUID = 2204497690867606834L;

    @SerializedName("detail")
    @Expose
    private String detail;

    public UserError() {
    }

    public UserError(String detail) {
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