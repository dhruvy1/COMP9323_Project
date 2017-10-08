package com.comp9323.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.comp9323.AnQ.QnAWebView;
import com.comp9323.Food.FoodContainer;
import com.comp9323.Events.EventFragment;


public class MainActivity extends AppCompatActivity{

    public static final String USR_PERF = "APP_USR_INFO";
    private ListView mList;
    public enum Page{ EVENT,DEAL, PLACE, QNA };
    //determine which page is user current is
    public static final Page[] CURRENT_PAGE = {Page.EVENT};

    private FrameLayout mContentContainer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_event:
                    //mList = (RecyclerView) findViewById(R.id.recycler_view);
                    //set user current page
                    CURRENT_PAGE[0] = Page.EVENT;

                    EventFragment eventFragment = new EventFragment();
                    FragmentTransaction eventTransaction = getSupportFragmentManager().beginTransaction();
                    eventTransaction.replace(R.id.content, eventFragment);
                    eventTransaction.addToBackStack(null).commit();
                    return true;

                case R.id.navigation_food:
                    //set user current page
                    CURRENT_PAGE[0] = Page.DEAL;

                    FoodContainer newFragment = new FoodContainer();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    Toast dash = Toast.makeText(getApplicationContext(), "Item selected navigation dashboard", Toast.LENGTH_SHORT);
                    dash.show();
                    return true;
                case R.id.navigation_qna:
                    //set user current page
                    CURRENT_PAGE[0] = Page.QNA;

                    QnAWebView newWebview = new QnAWebView();
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.content, newWebview);
                    transaction3.addToBackStack(null);
                    transaction3.commit();

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
}
