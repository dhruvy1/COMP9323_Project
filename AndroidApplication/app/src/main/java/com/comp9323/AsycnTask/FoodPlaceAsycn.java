package com.comp9323.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;

import com.comp9323.Food.FoodPlace.MyFoodPlaceRecyclerViewAdapter;
import com.comp9323.RestAPI.APIImpl.FoodPlaceImpl;

/**
 * Created by thomas on 26/9/2017.
 */

public class FoodPlaceAsycn extends AsyncTask<String, Void, Boolean> {
    private final MyFoodPlaceRecyclerViewAdapter mAdapter ;
    public static final String GET_LIST = "pull list";

    public FoodPlaceAsycn(MyFoodPlaceRecyclerViewAdapter adapter){
        super();
        mAdapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        //TODO change to database connection
        Log.d("A*", "pulling FoodPlace start");
        if (!mAdapter.ifLoading()) {
            mAdapter.setIsLoading(true);
            String method = strings[0];
            if (method.equals(GET_LIST)) {
                int pageNumber = Integer.parseInt(strings[1]);
                return FoodPlaceImpl.getFoodPlaces(pageNumber);
            } else {
                //TODO add more type of actions
            }

        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean)
            mAdapter.notifyDataSetChanged();
        mAdapter.setIsLoading(false);
    }
}
