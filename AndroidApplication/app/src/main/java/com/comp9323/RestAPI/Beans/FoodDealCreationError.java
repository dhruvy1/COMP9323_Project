package com.comp9323.RestAPI.Beans;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDealCreationError implements Serializable
{

    @SerializedName("updated_time")
    @Expose
    private List<String> updatedTime = null;
    @SerializedName("updated_date")
    @Expose
    private List<String> updatedDate = null;
    private final static long serialVersionUID = 6262283035546068605L;

    /**
     * No args constructor for use in serialization
     *
     */
    public FoodDealCreationError() {
    }

    /**
     *
     * @param updatedDate
     * @param updatedTime
     */
    public FoodDealCreationError(List<String> updatedTime, List<String> updatedDate) {
        super();
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