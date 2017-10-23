package com.comp9323.data.beans;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable {
    private final static long serialVersionUID = 3137907275006527167L;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("karma_points")
    @Expose
    private String karmarPoint;

    public User() {
    }

    public User(Integer id, String username, String deviceId, String karma) {
        this.id = id;
        this.username = username;
        this.deviceId = deviceId;
        this.karmarPoint = karma;
    }
    public User(Integer id, String username, String deviceId) {
        this.id = id;
        this.username = username;
        this.deviceId = deviceId;
    }

    public User(String username, String deviceId, String karma) {
        this.username = username;
        this.deviceId = deviceId;
        this.karmarPoint = karma;
    }

    public User(String username, String deviceId) {
        this.username = username;
        this.deviceId = deviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public User withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }
    public void setKarmarPoint(String karmarPoint){
        this.karmarPoint = karmarPoint;
    }

    public String getKarmarPoint() {
        return karmarPoint;
    }

    @Override
    public String toString() {
        String s = "User[ id: " + id + " , ";
        if (this.username != null)
            s += "user name: " + username;
        if (this.deviceId != null)
            s += " , deviceId: " + deviceId;
        if (this.karmarPoint != null)
            s += " , karmaPoint: " + karmarPoint;
        return s;
    }
}