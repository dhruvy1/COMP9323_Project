//package com.comp9323.asynctask;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.comp9323.food.fooddeal.FoodDealFragment;
//import com.comp9323.food.fooddeal.FoodDealRvAdapter;
//import com.comp9323.restclient.api.FoodDealApiImpl;
//import com.comp9323.data.beans.FoodDeal;
//import com.comp9323.data.DataHolder;
//
///**
// * Created by thomas on 25/9/2017.
// */
//
//public class FoodDealAsyncTask extends AsyncTask<String, Void, Boolean> {
//    private final FoodDealRvAdapter adapter;
//    public static final String GET_LIST = "1";
//    public static final String RATING = "2";
//
//    public FoodDealAsyncTask(FoodDealRvAdapter adapter) {
//        this.adapter = adapter;
//    }
//
//    @Override
//    protected Boolean doInBackground(String... strings) {
//        adapter.setIsLoading(true);
//        Log.d("A*", "Pulling FoodDeal start");
//        String method = strings[0];
//        if (method.equals(GET_LIST)) {
//            int pageNumber = Integer.parseInt(strings[1]);
//            boolean ifSuccess = FoodDealApiImpl.getFoodDeals(pageNumber);
//            //TODO Template need to change, that decrease the page number when call is failed
//            if (!ifSuccess)
//                FoodDealFragment.page--;
//            return ifSuccess;
//        } else if (method.equals(RATING)) {
//            int id = Integer.parseInt(strings[1]);
//            int rating = Integer.parseInt(strings[2]);
//            int counter = 0;
//            //****This may casing error if Internet Error or Concurrency problem
//            //get newest version from database
//            FoodDealApiImpl.getFoodDeal(id);
//            //update local item
//            FoodDeal fd = DataHolder.getInstance().getFoodDealwithID(id);
//            fd.setRating("" + (Integer.parseInt(fd.getRating()) + rating));
//            DataHolder.getInstance().updateFoodDealRating(fd);
//            //update server
//            try {
//                return FoodDealApiImpl.patchFoodDeal(id, null, null, null, null, null, null,fd.getRating(), null);
//            } catch (Exception e) {
//                Toast.makeText(DataHolder.getInstance().getContext(), "Date or Time format not match", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//          //  return true;
//        } else {
//            //TODO add more type of actions
//            return false;
//        }
//    }
//
//    @Override
//    protected void onPostExecute(Boolean bool) {
//        Log.d("A*", "Pull done");
//        super.onPostExecute(bool);
//        if (bool)
//            adapter.notifyDataSetChanged();
//        Log.d("A*", "notify Done");
//        adapter.setIsLoading(false);
//    }
//}
