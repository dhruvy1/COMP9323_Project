package com.comp9323.food.foodplace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;

public class FoodPlaceDetailFragment extends android.app.Fragment {

    private FoodPlace foodPlace;

    public FoodPlaceDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_place_rv_item_details, container, false);
        return view;
    }

    public void setFoodPlace(FoodPlace foodPlace) {
        this.foodPlace = foodPlace;
    }
}
