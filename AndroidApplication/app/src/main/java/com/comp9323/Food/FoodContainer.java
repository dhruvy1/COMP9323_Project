package com.comp9323.Food;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.comp9323.Food.FoodDeal.FoodDealFragment;
import com.comp9323.Food.FoodPlace.FoodPlaceFragment;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodContainer.OnFoodContainerInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodContainer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodContainer extends Fragment{

    private FrameLayout mFoodContainer;
    private FoodDealFragment mDealFragment;
    private FoodPlaceFragment mPlaceFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_fooddeal:
                    FoodDealFragment newFragment = new FoodDealFragment();
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.food_list_container, newFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    Toast dash = Toast.makeText(getContext(), "Item selected navigation dashboard", Toast.LENGTH_SHORT);
                    dash.show();
                    return true;
                case R.id.navigation_foodplace:
                    FoodPlaceFragment newFragmentA = new FoodPlaceFragment();
                    FragmentTransaction transactionA = getChildFragmentManager().beginTransaction();
                    transactionA.replace(R.id.food_list_container, newFragmentA);
                    transactionA.addToBackStack(null);
                    transactionA.commit();

                    Toast toastA = Toast.makeText(getContext(), "Item selected navigation dashboard", Toast.LENGTH_SHORT);
                    toastA.show();
                    return true;
            }
            return false;
        }
    };

    public FoodContainer() {    }

    public static FoodContainer newInstance() {
        FoodContainer fragment = new FoodContainer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_container, container, false);
        if (view == null) {
            Log.d("FOOD container", "view isn= null");
        }
        BottomNavigationView navigation = view.findViewById(R.id.food_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        this.mFoodContainer = (FrameLayout) view.findViewById(R.id.food_list_container);
        openFoodDealAtFirstPlace();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void openFoodDealAtFirstPlace(){
        FoodDealFragment newFragment = new FoodDealFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.food_list_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public interface OnFoodContainerInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
