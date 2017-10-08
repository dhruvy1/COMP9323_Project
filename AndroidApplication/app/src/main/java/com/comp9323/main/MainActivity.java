package com.comp9323.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.comp9323.event.EventArrayAdapter;
import com.comp9323.qa.QAWebView;
import com.comp9323.food.FoodContainer;
import com.comp9323.restclient.api.EventAPI;
import com.comp9323.restclient.RestClient;
import com.comp9323.data.beans.Event;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private EventAPI eventAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createEventAPI();

        BottomNavigationView navigation = findViewById(R.id.main_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private void createEventAPI() {
        eventAPI = RestClient.getClient().create(EventAPI.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.main_navigation_event:
                final ListView mList = findViewById(R.id.main_event_list);

                // TODO should this be in here?
                eventAPI.getEvents().enqueue(new Callback<List<Event>>() {
                    @Override
                    public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Event> events = (ArrayList) response.body();

                            EventArrayAdapter apiArrayAdapter = new EventArrayAdapter(getApplicationContext(), 0, events);
                            mList.setAdapter(apiArrayAdapter);
                        } else {
                            Log.d("EventsCallback", "Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Event>> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });

                return true;
            case R.id.main_navigation_food:
                FoodContainer fragment = new FoodContainer();
                ft.replace(R.id.main_placeholder, fragment);
                ft.addToBackStack(null);
                ft.commit();

                return true;
            case R.id.main_navigation_qa:
                QAWebView webView = new QAWebView();
                ft.replace(R.id.main_placeholder, webView);
                ft.addToBackStack(null);
                ft.commit();

                return true;
            default:
                return false;
        }
    }
}
