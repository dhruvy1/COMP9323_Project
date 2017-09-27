package com.comp9323.AsycnTask;

import android.os.AsyncTask;

import com.comp9323.Food.FoodPlace.MyFoodPlaceRecyclerViewAdapter;
import com.comp9323.Food.FoodPlace.dummy.DummyContent;

/**
 * Created by thomas on 26/9/2017.
 */

public class SearchFoodPlace extends AsyncTask<String, Void, Boolean> {
    private final MyFoodPlaceRecyclerViewAdapter mAdapter;

    public SearchFoodPlace(MyFoodPlaceRecyclerViewAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        //TODO change to database connection
        //simulate more food place is pulled from Database
        int end = Integer.parseInt(strings[0]) +5;
        for (int  i = Integer.parseInt(strings[0])+1; i <= end; i++) {
            DummyContent.ITEMS.add(new DummyContent.DummyItem(i+"", "this is new item ", "having id :" + i));
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean)
            mAdapter.notifyDataSetChanged();
    }
}
