package com.comp9323.RestAPI.APIImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;

import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.APIInterface.UserInterface;
import com.comp9323.RestAPI.Beans.User;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImpl {
    public static final String USR_PERF = "APP_USR_INFO" ;
    private static final UserInterface apiInterface = RestClient.getClient().create(UserInterface.class);

    public static void CreateUser(String username, final Context context){
        CreateUser( new User(username, createUUID()), context );
    }

    /**
     * Buggy, it need to wait until all activity action finish to execute  onResponse
     * @param user
     * @param context
     */
    private static void CreateUser(User user, final Context context){
        final SingletonDataHolder DH = SingletonDataHolder.getInstance();
         Log.v("TAG", "start calling REST");
        Call<User> call = apiInterface.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                Log.d("MIANEVENT",response.code()+"");
                User user = response.body();
                //temp store user detail into data holder
                DH.setUserSelf(user);
                //saving user detail into shared_preference
                SharedPreferences.Editor SFE = context.getSharedPreferences(USR_PERF, Context.MODE_PRIVATE).edit();
                SFE.putInt("id", user.getId());
                SFE.putString("username", user.getUsername());
                SFE.putString("uuid", user.getDeviceId());
                SFE.commit();
                Log.v("INFO", DH.getUserSelf().getDeviceId());
                return;
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.v("TAG", "FAILEED");
                call.cancel();
            }
        });
        Log.v("TAG", "Exit create user");
    }

    public static boolean isFirstLogin(Context context){

        SharedPreferences SF = context.getSharedPreferences(USR_PERF, Context.MODE_PRIVATE);
        if (! SF.contains("uuid") ){
            //first install
            Log.i("INFO", "NO usr uuid");
            return true;
        }else{
            Log.i("INFO", "found usr uuid");
            //load up user detail from shared_preference to dataholder
            SingletonDataHolder DH = SingletonDataHolder.getInstance();
            DH.setUserSelf(new User(SF.getInt("id", -1), SF.getString("username",null), SF.getString("uuid",null)));
            return false;
        }

    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
