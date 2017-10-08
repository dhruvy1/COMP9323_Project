package com.comp9323.data.beans;

import java.io.Serializable;
import java.util.Vector;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DealListPackage implements Serializable {
    private final static long serialVersionUID = 8664749458471685386L;

    @SerializedName("count")
    @Expose
    private Integer count;

    @SerializedName("next")
    @Expose
    private String nextUrl;

    @SerializedName("previous")
    @Expose
    private String previousUrl;

    @SerializedName("results")
    @Expose
    private Vector<FoodDeal> results = null;

    public DealListPackage() {
    }

    public DealListPackage(Integer count, String nextUrl, String previousUrl, Vector<FoodDeal> results) {
        this.count = count;
        this.nextUrl = nextUrl;
        this.previousUrl = previousUrl;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public DealListPackage withCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String next) {
        this.nextUrl = nextUrl;
    }

    public DealListPackage withNextUrl(String next) {
        this.nextUrl = nextUrl;
        return this;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previous) {
        this.previousUrl = previousUrl;
    }

    public DealListPackage withPreviousUrl(String previous) {
        this.previousUrl = previousUrl;
        return this;
    }

    public Vector<FoodDeal> getResults() {
        return results;
    }

    public void setResults(Vector<FoodDeal> results) {
        this.results = results;
    }

    public DealListPackage withResults(Vector<FoodDeal> results) {
        this.results = results;
        return this;
    }

}