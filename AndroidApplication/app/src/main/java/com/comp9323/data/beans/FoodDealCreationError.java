package com.comp9323.data.beans;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDealCreationError implements Serializable {
    private final static long serialVersionUID = 6262283035546068605L;

    @SerializedName("updated_time")
    @Expose
    private List<String> updatedTime = null;

    @SerializedName("updated_date")
    @Expose
    private List<String> updatedDate = null;

    public FoodDealCreationError() {
    }

    public FoodDealCreationError(List<String> updatedTime, List<String> updatedDate) {
        this.updatedTime = updatedTime;
        this.updatedDate = updatedDate;
    }

    public List<String> getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(List<String> updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FoodDealCreationError withUpdatedTime(List<String> updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public List<String> getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(List<String> updatedDate) {
        this.updatedDate = updatedDate;
    }

    public FoodDealCreationError withUpdatedDate(List<String> updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

}