package com.comp9323.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.comp9323.RestAPI.APIImpl.UserImpl;
import com.comp9323.RestAPI.APIInterface.EventAPI;
import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.User;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String USR_PERF = "APP_USR_INFO";
    public static final SingletonDataHolder DH = SingletonDataHolder.getInstance();

    private EventAPI eventAPI;
    private ListView mList;
    //private RecyclerView mList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mList = (RecyclerView) findViewById(R.id.recycler_view);

                    mList = (ListView) findViewById(R.id.listview);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            EventBean event = (EventBean) adapterView.getItemAtPosition(i);

                            Toast clicked = Toast.makeText(getApplicationContext(), "Item selected " + event.getName(), Toast.LENGTH_LONG);
                            clicked.show();

                            EventItemFragment eventPage = new EventItemFragment();
                    }
                    });

                    eventAPI.getEvents().enqueue(eventsCallback);
                    return true;
                case R.id.navigation_dashboard:
                    Toast dash = Toast.makeText(getApplicationContext(), "Item selected navigation dashboard", Toast.LENGTH_SHORT);
                    dash.show();
                    return true;
                case R.id.navigation_notifications:
                    Toast nav = Toast.makeText(getApplicationContext(), "Item selected navigation notifications", Toast.LENGTH_SHORT);
                    nav.show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createEventAPI();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
/**
        DH.setContext(this);
        DeleteSharePreference(); //DEBUG use

        //user get their profile detail
        if (isFirstLogin()) {
            ///TODO ASK user INPUT
            String username = "sample4";
            createNewUser(username);
        }
 **/
    }

    private void DeleteSharePreference() {
        SharedPreferences SF = getSharedPreferences(USR_PERF, 0);
        if (SF.contains("uuid")) {
            getSharedPreferences(USR_PERF, 0).edit().remove("uuid").remove("username").remove("id").commit();
        }
    }

    private boolean isFirstLogin() {
        SharedPreferences SF = this.getSharedPreferences(USR_PERF, Context.MODE_PRIVATE);
        if (!SF.contains("uuid")) {
            return true;
        } else {
            //load up user detail from shared_preference to data holder
            DH.setUserSelf(new User(SF.getInt("id", -1), SF.getString("username", null), SF.getString("uuid", null)));
            return false;
        }
    }

    private void createNewUser(String username) {
        UserImpl.CreateUser(username);
    }

    private void createEventAPI() {
        eventAPI = RestClient.getClient().create(EventAPI.class);
    }

    Callback<List<EventBean>> eventsCallback = new Callback<List<EventBean>>() {
        @Override
        public void onResponse(Call<List<EventBean>> call, Response<List<EventBean>> response) {
            if (response.isSuccessful()) {
                ArrayList<EventBean> events = (ArrayList) response.body();
/**
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mList.setLayoutManager(layoutManager);

                RecyclerView.Adapter mAdapter = new EventAdapter(events);

                mList.setAdapter(mAdapter);
**/

                EventArrayAdapter apiArrayAdapter = new EventArrayAdapter(getApplicationContext(), 0, events);
                mList.setAdapter(apiArrayAdapter);

            } else {
                Log.d("EventsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<EventBean>> call, Throwable t) {
            Log.d("Error",t.getMessage());
        }
    };
}
