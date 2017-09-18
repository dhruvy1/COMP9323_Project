package com.comp9323.RestAPI.APIImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;

import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.APIInterface.UserInterface;
import com.comp9323.RestAPI.Beans.User;
import com.comp9323.myapplication.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserImpl {
    public static final String USR_PERF = "APP_USR_INFO";
    private static final UserInterface apiInterface = RestClient.getClient().create(UserInterface.class);

    public static void CreateUser(String username) {
        CreateUser(new User(username, createUUID()));
    }

    public static boolean deleteSelf() {
        return deleteUser(MainActivity.DH.getUserSelf().getId());
    }

    public static boolean editUserWithFields(HashMap<String, String> couples) {
        //create new user template
        int Id = MainActivity.DH.getUserSelf().getId();
        User templateUser = new User();
        for (String item : couples.keySet()) {
            String value = couples.get(item);
            if (item.equals("username")) {
                templateUser.setUsername(value);
            } else if (item.equals("deviceid")) {
                templateUser.setDeviceId(value);
            }
        }
        //call Rest
        return editUserWithFields(Id, templateUser);
    }

    public static boolean editWholeUser(HashMap<String, String> couples) {
        //load user
        User userSelf = MainActivity.DH.getUserSelf();
        //set fields
        for (String item : couples.keySet()) {
            String value = couples.get(item);
            if (item.equals("username") ){
                userSelf.setUsername(value);
            } else if (item.equals( "deviceid") ){
                userSelf.setDeviceId(value);
            }
        }
        //call Rest
        return editWholeUser(userSelf.getId(), userSelf);

    }

    public static boolean getUsers() {
        Log.v("Rest Call", "Start Load All User");
        final boolean[] ifSuccess = {false};
        apiInterface.getUsers().enqueue(new Callback<Vector<User>>() {
            @Override
            public void onResponse(Call<Vector<User>> call, Response<Vector<User>> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                Vector<User> users = response.body();
                if (users != null) {
                    MainActivity.DH.addUsers(users);
                    ifSuccess[0] = true;
                    for (User u : users) {
                        Log.d("Rest Debug Print", u.toString());
                    }
                }
                Log.v("Rest Call", "End get User list");
            }

            @Override
            public void onFailure(Call<Vector<User>> call, Throwable t) {
                Log.d("Rest Call", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    public static boolean getUser(int id) {
        Log.v("Rest Call", "Start Get User");
        final boolean[] ifSuccess = {false};
        apiInterface.getUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                User user = response.body();
                Log.d("Rest Debug print", user.toString());
                MainActivity.DH.addUser(user);//TODO Should i store it?
                ifSuccess[0] = true;
                Log.v("Rest Call", "End Get User");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static void CreateUser(User user) {
        //final SingletonDataHolder DH = SingletonDataHolder.getInstance();
        Log.v("Rest Call", "Start Create User");
        apiInterface.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                User user = response.body();
                //temp store user detail into data holder
                MainActivity.DH.setUserSelf(user);
                //saving user detail into shared_preference
                SharedPreferences.Editor SFE = MainActivity.DH.getContext().getSharedPreferences(USR_PERF, Context.MODE_PRIVATE).edit();
                SFE.putInt("id", user.getId());
                SFE.putString("username", user.getUsername());
                SFE.putString("uuid", user.getDeviceId());
                SFE.commit();
                Log.v("Rest Call", "End Create User");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
    }

    private static boolean deleteUser(int id) {
        Log.v("Rest Call", "Start Delete User (self)");
        final boolean[] ifSuccess = {false};
        apiInterface.deleteUser(id).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Delete User(self)");
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static boolean editUserWithFields(int id, User user) {
        Log.v("Rest Call", "start Edit Fields");
        final boolean[] ifSuccess = {false};
        apiInterface.editUserWithFields(id, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Edit Fields");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static boolean editWholeUser(int id, User user) {
        Log.v("Rest Call", "start Edit User");
        final boolean[] ifSuccess = {false};
        apiInterface.editWholeUser(id, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Edit User");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
