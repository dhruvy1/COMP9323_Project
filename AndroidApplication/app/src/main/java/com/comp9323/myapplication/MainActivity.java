package com.comp9323.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.comp9323.RestAPI.APIImpl.UserImpl;
import com.comp9323.RestAPI.Beans.User;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;


public class MainActivity extends AppCompatActivity {
    public static final String USR_PERF = "APP_USR_INFO";
    public static  final SingletonDataHolder DH = SingletonDataHolder.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DH.setContext(this);
        DeleteSharePreference(); //DEBUG use

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //user get their profile detail
        if (isFirstLogin()) {
            ///TODO ASK user INPUT
            String username = "sample4";
            createNewUser(username);
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void DeleteSharePreference(){
        SharedPreferences SF = getSharedPreferences(USR_PERF, 0);
        if (SF.contains("uuid")) {
            getSharedPreferences(USR_PERF, 0).edit().remove("uuid").remove("username").remove("id").commit();
        }
    }

    private boolean isFirstLogin(){
        SharedPreferences SF = this.getSharedPreferences(USR_PERF, Context.MODE_PRIVATE);
        if (! SF.contains("uuid") ){
            return true;
        }else{
            //load up user detail from shared_preference to data holder
            DH.setUserSelf(new User(SF.getInt("id", -1), SF.getString("username",null), SF.getString("uuid",null)));
            return false;
        }
    }

    private void createNewUser(String username){
        UserImpl.CreateUser(username);
    }
}
