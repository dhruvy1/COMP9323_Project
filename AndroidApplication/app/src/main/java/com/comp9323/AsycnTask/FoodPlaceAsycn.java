package com.comp9323.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.comp9323.Food.FoodPlace.MyFoodPlaceRecyclerViewAdapter;
import com.comp9323.RestAPI.APIImpl.FoodPlaceImpl;
import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;

/**
 * Created by thomas on 26/9/2017.
 */

public class FoodPlaceAsycn extends AsyncTask<String, Void, Boolean> {
    private final MyFoodPlaceRecyclerViewAdapter mAdapter ;
    public static final String GET_LIST = "1";
    public static final String RATING = "2";

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
            } else if (method.equals(RATING)) {
                int id = Integer.parseInt(strings[1]);
                int rating = Integer.parseInt(strings[2]);
                int counter =0;
                //****This may casing error if Internet Error or Concurrency problem
                //get newest version from database
                FoodPlaceImpl.getFoodPlace(id);
                //update local item
                FoodPlace fp = SingletonDataHolder.getInstance().getFoodPlaceWithID(id);
                fp.setRating(""+(Integer.parseInt(fp.getRating()) + rating));
                SingletonDataHolder.getInstance().updateFoodPlaceRating(fp);
                //update server
                return FoodPlaceImpl.editFoodPlaceByFields(id, null, null, null, null, null, null, null, fp.getRating(), null);
            }else{
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
        else
            Toast.makeText(SingletonDataHolder.getInstance().getContext(), "Internal Service Error\n please try again", Toast.LENGTH_SHORT).show();
        mAdapter.setIsLoading(false);
    }
}
