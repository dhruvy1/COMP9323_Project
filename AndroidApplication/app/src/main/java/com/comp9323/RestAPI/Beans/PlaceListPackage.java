package com.comp9323.RestAPI.Beans;

import java.io.Serializable;
import java.util.Vector;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceListPackage implements Serializable
{

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
    private Vector<FoodPlace> results = null;
    private final static long serialVersionUID = -3411571719605032865L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PlaceListPackage() {
    }

    /**
     *
     * @param nextUrl
     * @param results
     * @param previous
     * @param count
     */
    public PlaceListPackage(Integer count, String nextUrl, String previous, Vector<FoodPlace> results) {
        super();
        this.count = count;
        this.nextUrl = nextUrl;
        this.previousUrl = previous;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public PlaceListPackage withCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public PlaceListPackage withNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
        return this;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public PlaceListPackage withPrevious(String previous) {
        this.previousUrl = previous;
        return this;
    }

    public Vector<FoodPlace> getResults() {
        return results;
    }

    public void setResults(Vector<FoodPlace> results) {
        this.results = results;
    }

    public PlaceListPackage withResults(Vector<FoodPlace> results) {
        this.results = results;
        return this;
    }

}