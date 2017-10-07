package com.comp9323.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.comp9323.Events.EventFragment;
import com.comp9323.Food.FoodContainer;
import com.comp9323.Food.FoodDeal.FoodDealFragment;
import com.comp9323.Food.FoodPlace.FoodPlaceFragment;
import com.comp9323.Food.FoodPlace.dummy.DummyContent;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.FoodDeal;

public class MainActivity extends AppCompatActivity implements FoodDealFragment.OnListFragmentInteractionListener, FoodPlaceFragment.OnListFragmentInteractionListener {

    public static final String USR_PERF = "APP_USR_INFO";

    private FrameLayout mContentContainer;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.mContentContainer = (FrameLayout) findViewById(R.id.content);

        // Load up events on startup
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new EventFragment());
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(FoodDeal item) {
        Log.d("Food Deal Interaction", "item pressed: " + item.getMessage());
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d("Food Deal Interaction", "item pressed: " + item.toString());
    }

}
