package com.comp9323.RestAPI.Beans;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodPlace implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("price_level")
    @Expose
    private String priceLevel;
    @SerializedName("google_rating")
    @Expose
    private String googleRating;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    private final static long serialVersionUID = -8996665835930522429L;

    /**
     * No args constructor for use in serialization
     *
     */
    public FoodPlace() {
    }

    /**
     *
     * @param id
     * @param priceLevel
     * @param location
     * @param name
     * @param googleRating
     * @param longitude
     * @param latitude
     */
    public FoodPlace(Integer id, String name, String location, String priceLevel, String googleRating, String latitude, String longitude) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.googleRating = googleRating;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public FoodPlace(String name, String location, String priceLevel, String googleRating, String latitude, String longitude) {
        super();
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.googleRating = googleRating;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FoodPlace withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodPlace withName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public FoodPlace withLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
    }

    public FoodPlace withPriceLevel(String priceLevel) {
        this.priceLevel = priceLevel;
        return this;
    }

    public String getGoogleRating() {
        return googleRating;
    }

    public void setGoogleRating(String googleRating) {
        this.googleRating = googleRating;
    }

    public FoodPlace withGoogleRating(String googleRating) {
        this.googleRating = googleRating;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public FoodPlace withLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public FoodPlace withLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

}