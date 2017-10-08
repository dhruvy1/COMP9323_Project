package com.comp9323.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp9323.asynctask.UserAsyncTask;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DataHolder.getInstance().setContext(getApplicationContext());

        Button button = findViewById(R.id.login_sign_up_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.login_sign_up_btn) {
                    EditText text = findViewById(R.id.login_username_et);
                    String username = text.getText().toString();
                    new UserAsyncTask().execute(username);
                }
            }
        });

        // deleteSharedPreferences(); // DEBUG USE

        if (isFirstLogin()) {
            findViewById(R.id.login_sign_up_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.login_username_et).setVisibility(View.VISIBLE);
        } else {
            SharedPreferences sp = getSharedPreferences(getString(R.string.user_pref), Context.MODE_PRIVATE);
            DataHolder.getInstance().setUser(new User(sp.getInt("id", -1), sp.getString("username", null), sp.getString("uuid", null)));

            String welcome = "Welcome " + DataHolder.getInstance().getUserSelf().getUsername() + "!";
            Toast.makeText(DataHolder.getInstance().getContext(), welcome, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
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
}
