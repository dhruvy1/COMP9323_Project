package com.comp9323.restclient.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.User;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;

import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserApiImpl {
    private static final String TAG = "UserApiImpl";

    private static final UserApi api = RestClient.getClient().create(UserApi.class);

    public static boolean postUser(String username) {
        return postUser(new User(username, createUUID()));
    }

    public static boolean deleteUser() {
        return deleteUser(DataHolder.getInstance().getUser().getId());
    }

    public static boolean putUser(HashMap<String, String> couples) {
        //load user
        User userSelf = DataHolder.getInstance().getUser();
        //set fields
        for (String item : couples.keySet()) {
            String value = couples.get(item);
            if (item.equals("username")) {
                userSelf.setUsername(value);
            } else if (item.equals("deviceid")) {
                userSelf.setDeviceId(value);
            }
        }

        return putUser(userSelf.getId(), userSelf);
    }

    public static boolean patchUser(HashMap<String, String> couples) {
        //create new user template
        int Id = DataHolder.getInstance().getUser().getId();
        User templateUser = new User();
        for (String item : couples.keySet()) {
            String value = couples.get(item);
            if (item.equals("username")) {
                templateUser.setUsername(value);
            } else if (item.equals("deviceid")) {
                templateUser.setDeviceId(value);
            }
        }

        return patchUser(Id, templateUser);
    }

//    public static boolean getUser(int id) {
//        Log.v(TAG, "Start Get User");
//        final boolean[] ifSuccess = {false};
//        apiInterface.getUser(id).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
//                User user = response.body();
//                Log.d("Rest Debug print", user.toString());
//                SingletonDataHolder.getInstance().addUser(user); // TODO need to store it!
//                ifSuccess[0] = true;
//                Log.v(TAG, "End Get User");
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.d(TAG, "~~FAILED~~");
//                call.cancel();
//            }
//        });
//        return ifSuccess[0];
//    }

//    public static boolean getUsers() {
//        Log.v(TAG, "Start Load All User");
//        final boolean[] ifSuccess = {false};
//        apiInterface.getUsers().enqueue(new Callback<Vector<User>>() {
//            @Override
//            public void onResponse(Call<Vector<User>> call, Response<Vector<User>> response) {
//                Log.d(TAG, "Is response success? " + response.isSuccessful());
//                Vector<User> users = response.body();
//                if (users != null) {
//                    SingletonDataHolder.getInstance().addUsers(users);
//                    ifSuccess[0] = true;
//                    for (User u : users) {
//                        Log.d("Rest Debug Print", u.toString());
//                    }
//                }
//                Log.v(TAG, "End get User list");
//            }
//
//            @Override
//            public void onFailure(Call<Vector<User>> call, Throwable t) {
//                Log.d(TAG, "~~FAILED~~");
//                call.cancel();
//            }
//        });
//        return ifSuccess[0];
//    }

    private static boolean postUser(User user) {
        //final DataHolder DH = DataHolder.getInstance();
        Log.v(TAG, "Start Create User");
        final boolean[] ifSuccess = {false};
        final User[] users = new User[1];
        api.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                User user = response.body();
                //temp store user detail into data holder
                DataHolder.getInstance().setUser(user);
                //saving user detail into shared_preference
                Context context = DataHolder.getInstance().getContext();
                SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.user_pref), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putInt("id", user.getId());
                editor.putString("username", user.getUsername());
                editor.putString("uuid", user.getDeviceId());
                ifSuccess[0] = editor.commit();

                users[0] = user;
                Log.v(TAG, "End Create User, commiting:" + ifSuccess[0]);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                users[0] = new User();
                call.cancel();
            }
        });
        while (users[0] == null) {
            Log.i("Waiting", "REST resposne is not run yet");
        }
        Log.v("Rest Call Debug", "End Create User, committing:" + ifSuccess[0]);
        Log.v("Rest Call Debug", "username:" + DataHolder.getInstance().getUser().getUsername());

        return ifSuccess[0];
    }

    private static boolean deleteUser(int id) {
        Log.v(TAG, "Start Delete User (self)");
        final boolean[] ifSuccess = {false};
        api.deleteUser(id).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v(TAG, "End Delete User(self)");
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    private static boolean putUser(int id, User user) {
        Log.v(TAG, "start Edit User");
        final boolean[] ifSuccess = {false};
        api.putUser(id, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v(TAG, "End Edit User");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    private static boolean patchUser(int id, User user) {
        Log.v(TAG, "start Edit Fields");
        final boolean[] ifSuccess = {false};
        api.patchUser(id, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v(TAG, "End Edit Fields");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    private static String createUUID() {
        return UUID.randomUUID().toString();
    }
}
