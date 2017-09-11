package com.comp9323.RestAPI.APIImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;

import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.APIInterface.UserInterface;
import com.comp9323.RestAPI.Beans.User;
import com.comp9323.myapplication.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImpl {
    public static final String USR_PERF = "APP_USR_INFO" ;
    private static final UserInterface apiInterface = RestClient.getClient().create(UserInterface.class);

    public static void CreateUser(String username){CreateUser( new User(username, createUUID()));}
    public static void  GetUsers(){}


    /**
     *
     * @param user
     */
    private static void CreateUser(User user){
        //final SingletonDataHolder DH = SingletonDataHolder.getInstance();
         Log.v("TAG", "start calling REST");
        Call<User> call = apiInterface.createUser(user);
        call.enqueue(new Callback<User>() {
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
                return;
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("REST CALL", "FAILED");
                call.cancel();
            }
        });
    }
    



    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
