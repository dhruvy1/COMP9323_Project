package com.comp9323.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.User;
import com.comp9323.restclient.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.login_sign_up_btn);
        button.setOnClickListener(this);

//        deleteSharedPreferences(); // DEBUG USE

        if (isFirstLogin()) {
            // first login
            findViewById(R.id.login_sign_up_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.login_username_et).setVisibility(View.VISIBLE);
        } else {
            // user already exists, load the user
            SharedPreferences sp = getSharedPreferences(getString(R.string.user_pref), Context.MODE_PRIVATE);
            User user = new User(sp.getInt("id", -1), sp.getString("username", null), sp.getString("uuid", null));

            loginUser(user);
        }
    }

    private boolean isFirstLogin() {
        SharedPreferences sp = getSharedPreferences(getString(R.string.user_pref), Context.MODE_PRIVATE);
        return (!sp.contains("uuid"));
    }

    private void deleteSharedPreferences() {
        SharedPreferences sp = getSharedPreferences(getString(R.string.user_pref), Context.MODE_PRIVATE);
        if (sp.contains("uuid")) {
            sp.edit().remove("uuid").remove("username").remove("id").apply();
        }
    }

    private void saveUser(User user) {
        SharedPreferences sp = getSharedPreferences(getString(R.string.user_pref), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("uuid", user.getDeviceId());

        editor.apply();
    }

    private void loginUser(User user) {
        DataHolder.getInstance().setUser(user);

        String welcome = "Welcome " + user.getUsername() + "!";
        Toast.makeText(getBaseContext(), welcome, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_sign_up_btn:
                EditText username = findViewById(R.id.login_username_et);
                User user = new User();
                user.setUsername(username.getText().toString());
                UserService.postUser(user, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            saveUser(response.body());
                            loginUser(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
                break;
        }
    }
}
