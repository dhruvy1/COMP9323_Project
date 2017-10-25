package com.comp9323.restclient.service;

import com.comp9323.data.beans.User;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.UserApi;

import java.util.List;
import java.util.UUID;

import retrofit2.Callback;


public class UserService {
    private static final String TAG = "UserService";

    private static final UserApi api = RestClient.getClient().create(UserApi.class);

    /**
     * Get a user from the server
     *
     * @param id
     * @param callback the callback where the response of the server will be returned
     */
    public static void getUser(int id, Callback<User> callback) {
        api.getUser(id).enqueue(callback);
    }

    /**
     * Get all users from the server
     *
     * @param callback the callback where the response of the server will be returned
     */
    public static void getUsers(Callback<List<User>> callback) {
        api.getUsers().enqueue(callback);
    }

    /**
     * Create a new user into the server
     *
     * @param user     the new user
     * @param callback the callback where the response of the server will be returned
     */
    public static void postUser(User user, Callback<User> callback) {
        user.setDeviceId(getUUID());
        user.setKarmaPoint("0");
        api.postUser(user).enqueue(callback);
    }

    /**
     * Delete an existing user from the server
     *
     * @param id       the id of the user
     * @param callback the callback where the response of the server will be returned
     */
    public static void deleteUser(int id, Callback<Void> callback) {
        api.deleteUser(id).enqueue(callback);
    }

    /**
     * Put a new user into the server
     *
     * @param id       the id of the user
     * @param user     the user to be put
     * @param callback the callback where the response of the server will be returned
     */
    public static void putUser(int id, User user, Callback<User> callback) {
        api.putUser(id, user).enqueue(callback);
    }

    /**
     * Patch a user into the server
     *
     * @param id       the id of the user
     * @param user     the fields of the user to be patched
     * @param callback the callback where the response of the server will be returned
     */
    public static void patchUser(int id, User user, Callback<User> callback) {
        api.patchUser(id, user).enqueue(callback);
    }

    /**
     * Gets the UUID of the device
     *
     * @return the UUID of the device
     */
    private static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
