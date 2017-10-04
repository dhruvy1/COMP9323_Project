package com.comp9323.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

//import com.comp9323.Events.EventArrayAdapter;
import com.comp9323.Events.EventFragment;
import com.comp9323.Food.FoodContainer;
import com.comp9323.Food.FoodDeal.FoodDealFragment;
import com.comp9323.Food.FoodPlace.FoodPlaceFragment;
import com.comp9323.Food.FoodPlace.dummy.DummyContent;
import com.comp9323.RestAPI.APIInterface.EventInterface;
import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.FoodDeal;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FoodDealFragment.OnListFragmentInteractionListener, FoodPlaceFragment.OnListFragmentInteractionListener, EventFragment.OnListFragmentInteractionListener{

    public static final String USR_PERF = "APP_USR_INFO";

//    private EventInterface eventInterface;
//    private ListView mList;
    private FrameLayout mContentContainer;
    //private RecyclerView mList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_event:
                    EventFragment eventFragment = new EventFragment();
                    FragmentTransaction eventTransaction = getSupportFragmentManager().beginTransaction();
                    eventTransaction.replace(R.id.content, eventFragment);
                    eventTransaction.addToBackStack(null).commit();
/**
                    //mList = (RecyclerView) findViewById(R.id.recycler_view);

                    mList = (ListView) findViewById(R.id.listview);
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            EventBean event = (EventBean) adapterView.getItemAtPosition(i);

                            Toast clicked = Toast.makeText(getApplicationContext(), "Item selected " + event.getName(), Toast.LENGTH_LONG);
                            clicked.show();

                            //EventItemFragment eventPage = new EventItemFragment();

                    }
                    });

                    eventInterface.getEvents(0).enqueue(eventsCallback);
*/
                    return true;
                case R.id.navigation_food:
                    //FoodDealFragment newFragment = new FoodDealFragment();
                    FoodContainer newFragment = new FoodContainer();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    Toast dash = Toast.makeText(getApplicationContext(), "Item selected navigation dashboard", Toast.LENGTH_SHORT);
                    dash.show();
                    return true;
                case R.id.navigation_qna:
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

//        createEventAPI();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.mContentContainer = (FrameLayout) findViewById(R.id.content);

    }

/**
    private void createEventAPI() {
        eventInterface = RestClient.getClient().create(EventInterface.class);
    }

    Callback<List<EventBean>> eventsCallback = new Callback<List<EventBean>>() {
        @Override
        public void onResponse(Call<List<EventBean>> call, Response<List<EventBean>> response) {
            if (response.isSuccessful()) {
                ArrayList<EventBean> events = (ArrayList) response.body();

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mList.setLayoutManager(layoutManager);

                RecyclerView.Adapter mAdapter = new EventAdapter(events);

                mList.setAdapter(mAdapter);

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
*/

    @Override
    public void onListFragmentInteraction(FoodDeal item) {
        Log.d("Food Deal Interaction", "item pressed: " + item.getMessage());
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d("Food Deal Interaction", "item pressed: " + item.toString());
    }

    @Override
    public void onListFragmentInteraction(EventBean item) {
        Log.d("Event Interaction", "item pressed: " + item.toString());
    }
}
