package com.comp9323.food;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.food.fooddeal.FoodDealFragment;
import com.comp9323.food.foodplace.FoodPlaceFragment;
import com.comp9323.main.R;

public class FoodContainer extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_container, container, false);

        BottomNavigationView navigation = view.findViewById(R.id.food_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        openFoodDealAtFirstPlace();

        return view;
    }

    private void openFoodDealAtFirstPlace() {
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
