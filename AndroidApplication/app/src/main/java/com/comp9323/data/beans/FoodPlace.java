package com.comp9323.data.beans;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodPlace implements Serializable {
    private final static long serialVersionUID = -8731275924963347679L;

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

    @SerializedName("photo_link")
    @Expose
    private String photoLink;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("created_by")
    @Expose
    private String createdBy;

    public FoodPlace() {
    }

    public FoodPlace(Integer id, String name, String location, String priceLevel, String googleRating, String latitude, String longitude, String photoLink, String rating, String createdBy) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.googleRating = googleRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoLink = photoLink;
        this.rating = rating;
        this.createdBy = createdBy;
    }

    public FoodPlace(String name, String location, String priceLevel, String googleRating, String latitude, String longitude, String photoLink, String rating, String createdBy) {
        this.name = name;
        this.location = location;
        this.priceLevel = priceLevel;
        this.googleRating = googleRating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoLink = photoLink;
        this.rating = rating;
        this.createdBy = createdBy;
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

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public FoodPlace withPhotoLink(String photoLink) {
        this.photoLink = photoLink;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public FoodPlace withRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public FoodPlace withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

}