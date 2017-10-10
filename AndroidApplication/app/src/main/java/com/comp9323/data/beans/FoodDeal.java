package com.comp9323.data.beans;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDeal implements Serializable {
    private final static long serialVersionUID = -8242576134588360110L;

    public static final String FACEBOOK = "Facebook";

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("post_id")
    @Expose
    private String postId;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("updated_time")
    @Expose
    private String updatedTime;

    @SerializedName("photo_link")
    @Expose
    private String photoLink;

    @SerializedName("event_link")
    @Expose
    private String eventLink;

    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("created_by")
    @Expose
    private String createdBy;

    public FoodDeal() {
    }

    public FoodDeal(Integer id, String postId, String message, String updatedTime, String photoLink, String eventLink, String updatedDate, String rating, String createdBy) {
        this.id = id;
        this.postId = postId;
        this.message = message;
        this.updatedTime = updatedTime;
        this.photoLink = photoLink;
        this.eventLink = eventLink;
        this.updatedDate = updatedDate;
        this.rating = rating;
        this.createdBy = createdBy;
    }

    public FoodDeal(String postId, String message, String updatedTime, String photoLink, String eventLink, String updatedDate, String rating, String createdBy) {
        this.postId = postId;
        this.message = message;
        this.updatedTime = updatedTime;
        this.photoLink = photoLink;
        this.eventLink = eventLink;
        this.updatedDate = updatedDate;
        this.rating = rating;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FoodDeal withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public FoodDeal withPostId(String postId) {
        this.postId = postId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FoodDeal withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FoodDeal withUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public FoodDeal withPhotoLink(String photoLink) {
        this.photoLink = photoLink;
        return this;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }

    public FoodDeal withEventLink(String eventLink) {
        this.eventLink = eventLink;
        return this;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public FoodDeal withUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public FoodDeal withRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public FoodDeal withCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String toString(){
        return new Gson().toJson(this);
    }

}