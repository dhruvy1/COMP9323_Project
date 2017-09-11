package com.comp9323.RestAPI.Beans;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserList implements Serializable
{

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    private final static long serialVersionUID = 8588749089454689720L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserList() {
    }

    /**
     *
     * @param username
     * @param deviceId
     */
    public UserList(String username, String deviceId) {
        super();
        this.username = username;
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserList withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public UserList withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

}