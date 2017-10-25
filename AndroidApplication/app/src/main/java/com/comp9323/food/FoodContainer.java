package com.comp9323.food;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.food.fooddeal.FoodDealFragment;
import com.comp9323.food.foodplace.FoodPlaceFragment;
import com.comp9323.main.R;

/**
 * Container fragment that hold the food segment
 */
public class FoodContainer extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_container, container, false);

        setHasOptionsMenu(true);

        BottomNavigationView navigation = view.findViewById(R.id.food_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        openFoodDealAtFirstDeal();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    /**
     * auto load up food deal when this fragment is created
     */
    private void openFoodDealAtFirstDeal() {
        FoodDealFragment newFragment = new FoodDealFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.food_deal_placeholder, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_food_deal:
                FoodDealFragment newFragment = new FoodDealFragment();
                ft.replace(R.id.food_deal_placeholder, newFragment);
                ft.addToBackStack(null);
                ft.commit();

                return true;
            case R.id.navigation_food_place:
                FoodPlaceFragment newFragmentA = new FoodPlaceFragment();
                ft.replace(R.id.food_deal_placeholder, newFragmentA);
                ft.addToBackStack(null);
                ft.commit();

                return true;
            default:
                return false;
        }
    }
}
