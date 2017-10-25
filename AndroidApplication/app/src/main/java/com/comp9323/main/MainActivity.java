package com.comp9323.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.comp9323.event.EventFragment;
import com.comp9323.qa.QAWebView;
import com.comp9323.food.FoodContainer;

/**
 * Main logic controller of the application,
 * this Activity will stay until the application is close
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static boolean FIRST_START_UP =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.main_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        if (FIRST_START_UP) {
            FIRST_START_UP = false;
            // Load up events on startup
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_placeholder, new EventFragment());
            transaction.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.main_navigation_event:
                EventFragment eventFragment = new EventFragment();
                ft.replace(R.id.main_placeholder, eventFragment);
                ft.commit();

                return true;
            case R.id.main_navigation_food:
                FoodContainer fragment = new FoodContainer();
                ft.replace(R.id.main_placeholder, fragment);
                ft.commit();

                return true;
            case R.id.main_navigation_qa:
                QAWebView webView = new QAWebView();
                ft.replace(R.id.main_placeholder, webView);
                ft.commit();

                return true;
            default:
                return false;
        }
    }
}
