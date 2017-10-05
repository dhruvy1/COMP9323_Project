package com.comp9323.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;

import com.comp9323.Food.FoodDeal.MyFoodDealRecyclerViewAdapter;
import com.comp9323.RestAPI.APIImpl.FoodDealImpl;

/**
 * Created by thomas on 25/9/2017.
 */

public class FoodDealAsycn extends AsyncTask<String, Void, Boolean> {
    private final MyFoodDealRecyclerViewAdapter mAdapter;
    public static final String GET_LIST = "pull list";

    public FoodDealAsycn(MyFoodDealRecyclerViewAdapter adapter){
        super();
        mAdapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        mAdapter.setIsLoading(true);
        Log.d("A*", "Pulling FoodDeal start");
        String method = strings[0];
        if (method.equals(GET_LIST)) {
            int pageNumber = Integer.parseInt(strings[1]);
            return FoodDealImpl.getFoodDeals(pageNumber);
        }else{
            //TODO add more type of actions
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean bool){
        Log.d("A*", "Pull done");
        super.onPostExecute(bool);
        if (bool)
            mAdapter.notifyDataSetChanged();
        Log.d("A*", "notify Done");
        mAdapter.setIsLoading(false);
    }
}
