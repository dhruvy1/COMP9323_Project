package com.comp9323.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.comp9323.Food.FoodDeal.FoodDealFragment;
import com.comp9323.Food.FoodDeal.MyFoodDealRecyclerViewAdapter;
import com.comp9323.RestAPI.APIImpl.FoodDealImpl;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;

/**
 * Created by thomas on 25/9/2017.
 */

public class FoodDealAsycn extends AsyncTask<String, Void, Boolean> {
    private final MyFoodDealRecyclerViewAdapter mAdapter;
    public static final String GET_LIST = "1";
    public static final String RATING = "2";

    public FoodDealAsycn(final MyFoodDealRecyclerViewAdapter adapter){
        super();
        mAdapter = adapter;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.d("A*", "Pulling FoodDeal start");
            String method = strings[0];

            if (method.equals(GET_LIST)) {
                int pageNumber = Integer.parseInt(strings[1]);
                boolean ifSuccess = FoodDealImpl.getFoodDeals(pageNumber);
                //TODO Template need to change, that decrease the page number when call is failed
                if (!ifSuccess)
                    FoodDealFragment.mPage--;
                return ifSuccess;
            } else if (method.equals(RATING)) {
                int id = Integer.parseInt(strings[1]);
                int rating = Integer.parseInt(strings[2]);
                int counter = 0;
                //****This may casing error if Internet Error or Concurrency problem
                //get newest version from database
                FoodDealImpl.getFoodDeal(id);
                //update local item
                FoodDeal fd = SingletonDataHolder.getInstance().getFoodDealwithID(id);
                fd.setRating("" + (Integer.parseInt(fd.getRating()) + rating));
                SingletonDataHolder.getInstance().updateFoodDealRating(fd);
                //update server
//            try {
//                return FoodDealImpl.editFoodDealByFields(id, null, null, null, null, null, null,fd.getRating(), null);
//            } catch (Exception e) {
//                Toast.makeText(SingletonDataHolder.getInstance().getContext(), "Date or Time format not match", Toast.LENGTH_SHORT).show();
//                return false;
//            }
                return true;
            } else {
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
