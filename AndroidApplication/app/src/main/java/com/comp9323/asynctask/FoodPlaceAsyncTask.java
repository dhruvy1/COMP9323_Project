package com.comp9323.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.comp9323.food.foodplace.FoodPlaceRvAdapter;
import com.comp9323.data.DataHolder;
import com.comp9323.restclient.api.FoodPlaceAPIImpl;
import com.comp9323.data.beans.FoodPlace;

/**
 * Created by thomas on 26/9/2017.
 */

public class FoodPlaceAsyncTask extends AsyncTask<String, Void, Boolean> {
    private final FoodPlaceRvAdapter adapter;
    public static final String GET_LIST = "1";
    public static final String RATING = "2";

    public FoodPlaceAsyncTask(FoodPlaceRvAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        //TODO change to database connection
        Log.d("A*", "pulling FoodPlace start");
        if (!adapter.ifLoading()) {
            adapter.setIsLoading(true);
            String method = strings[0];

            if (method.equals(GET_LIST)) {
                int pageNumber = Integer.parseInt(strings[1]);
                return FoodPlaceAPIImpl.getFoodPlaces(pageNumber);
            } else if (method.equals(RATING)) {
                int id = Integer.parseInt(strings[1]);
                int rating = Integer.parseInt(strings[2]);
                int counter = 0;
                //****This may casing error if Internet Error or Concurrency problem
                //get newest version from database
                FoodPlaceAPIImpl.getFoodPlace(id);
                //update local item
                FoodPlace fp = DataHolder.getInstance().getFoodPlaceWithID(id);
                fp.setRating("" + (Integer.parseInt(fp.getRating()) + rating));
                DataHolder.getInstance().updateFoodPlaceRating(fp);
                //update server
                return FoodPlaceAPIImpl.patchFoodPlace(id, null, null, null, null, null, null, null, fp.getRating(), null);
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
            adapter.notifyDataSetChanged();
        else
            Toast.makeText(DataHolder.getInstance().getContext(), "Internal Service Error\n please try again", Toast.LENGTH_SHORT).show();
        adapter.setIsLoading(false);
    }
}
