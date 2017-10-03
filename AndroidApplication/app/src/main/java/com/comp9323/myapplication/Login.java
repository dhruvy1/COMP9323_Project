package com.comp9323.myapplication;

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

import com.comp9323.AsycnTask.UserRegistration;
import com.comp9323.RestAPI.APIImpl.UserImpl;
import com.comp9323.RestAPI.Beans.User;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;

public class Login extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SingletonDataHolder.getInstance().setContext(getBaseContext());
        final Button button= (Button) findViewById(R.id.SignUpButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", view.getId()+"");
                if (view == findViewById(R.id.SignUpButton)){
                    EditText text = (EditText) findViewById(R.id.UserNameInput);
                    String username = text.getText().toString();
                    new UserRegistration().execute(username);
                }
            }
        });
        //DEBUG use
        //DeleteSharePreference();

        //user get their profile detail
        if (isFirstLogin()) {
            findViewById(R.id.SignUpButton).setVisibility(View.VISIBLE);
            findViewById(R.id.UserNameInput).setVisibility(View.VISIBLE);
        }else{
            Log.d("Login", "user name:" +SingletonDataHolder.getInstance().getUserSelf().getUsername());
            CharSequence welcome = "Welcome " + SingletonDataHolder.getInstance().getUserSelf().getUsername() + "!";
            Toast toast = Toast.makeText(SingletonDataHolder.getInstance().getContext(),welcome, Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }

    private boolean isFirstLogin() {
        SharedPreferences SF = this.getSharedPreferences(MainActivity.USR_PERF, Context.MODE_PRIVATE);
        if (!SF.contains("uuid")) {
            return true;
        } else {
            //load up user detail from shared_preference to data holder
            SingletonDataHolder.getInstance().setUserSelf(new User(SF.getInt("id", -1), SF.getString("username", null), SF.getString("uuid", null)));
            return false;
        }
    }

    private void DeleteSharePreference() {
        SharedPreferences SF = getSharedPreferences(MainActivity.USR_PERF, 0);
        if (SF.contains("uuid")) {
            getSharedPreferences(MainActivity.USR_PERF, 0).edit().remove("uuid").remove("username").remove("id").commit();
        }
    }

//    public void sendMessage(View view) {
//    }
}
