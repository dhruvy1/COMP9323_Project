package com.comp9323.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;

import com.comp9323.FoodDeal.MyFoodDealRecyclerViewAdapter;
import com.comp9323.RestAPI.APIImpl.FoodDealImpl;

/**
 * Created by thomas on 25/9/2017.
 */

public class PullingFoodDeals extends AsyncTask<Integer, Void, Boolean> {
    private final MyFoodDealRecyclerViewAdapter mAdapter;

    public PullingFoodDeals(MyFoodDealRecyclerViewAdapter adapter){
        super();
        mAdapter = adapter;
    }
    @Override
    protected Boolean doInBackground(Integer... integers) {
        Log.d("A*", "Pulling FoodDeal start");
        int pageNumber = integers[0];
        return FoodDealImpl.getFoodDeals(pageNumber);
    }

    @Override
    protected void onPostExecute(Boolean bool){
        Log.d("A*", "Pull done");
        mAdapter.notifyDataSetChanged();
        Log.d("A*", "notify Done");
    }
}
