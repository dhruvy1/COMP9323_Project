package com.comp9323.restclient.api;

import android.util.Log;

import com.comp9323.data.beans.FoodDeal;
import com.comp9323.restclient.RestClient;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDealApiImpl {
    private static final String TAG = "FoodDealApiImpl";

    private static final FoodDealApi api = RestClient.getClient().create(FoodDealApi.class);

    //23:59:59
    private static final String TIME_PATTERN = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

    //YYYY-MM-DD
    private static final String DATE_PATTERN = "^(((((1[26]|2[048])00)|[12]\\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|[12]\\d))))|((([12]\\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|1\\d|2[0-8])))))$";

    public static void postFoodDeal(String postId, String message, String updateTime,
                                    String photoLink, String eventLink, String updateDate,
                                    String rating, String createdBy) throws Exception {
        if (updateTime == null || !Pattern.matches(TIME_PATTERN, updateTime))
            throw new Exception("Time Not Match");
        if (updateDate == null || !Pattern.matches(DATE_PATTERN, updateDate))
            throw new Exception("Date Not Match");
        FoodDeal fd = new FoodDeal(postId, message, updateTime, photoLink, eventLink, updateDate, rating, createdBy);

        postFoodDeal(fd);
    }

    public static void putFoodDeal(int id, String postId, String message, String updateTime,
                                   String photoLink, String eventLink, String updateDate,
                                   String rating, String createdBy) throws Exception {
        FoodDeal foodDeal = new FoodDeal();
        if (postId != null && postId.length() > 0)
            foodDeal.setPostId(postId);
        if (message != null && message.length() > 0)
            foodDeal.setPostId(message);
        if (photoLink != null && photoLink.length() > 0)
            foodDeal.setPostId(photoLink);
        if (eventLink != null && eventLink.length() > 0)
            foodDeal.setPostId(eventLink);
        if (updateTime != null && updateTime.length() > 0) {
            if (!Pattern.matches(TIME_PATTERN, updateTime)) {
                throw new Exception("Time Not Match");
            }
            foodDeal.setPostId(updateTime);
        }
        if (updateDate != null && updateDate.length() > 0) {
            if (Pattern.matches(DATE_PATTERN, updateDate)) {
                throw new Exception("Date Not Match");
            }
            foodDeal.setPostId(updateDate);
        }
        if (rating != null && rating.length() > 0)
            foodDeal.setRating(rating);
        if (createdBy != null && createdBy.length() > 0)
            foodDeal.setCreatedBy(createdBy);

        putFoodDeal(id, foodDeal);
    }

    public static void patchFoodDeal(int id, String postId, String message, String updateTime,
                                     String photoLink, String eventLink, String updateDate,
                                     String rating, String createdBy) throws Exception {
        FoodDeal foodDeal = new FoodDeal();
        if (postId != null && postId.length() > 0)
            foodDeal.setPostId(postId);
        if (message != null && message.length() > 0)
            foodDeal.setPostId(message);
        if (photoLink != null && photoLink.length() > 0)
            foodDeal.setPostId(photoLink);
        if (eventLink != null && eventLink.length() > 0)
            foodDeal.setPostId(eventLink);
        if (updateTime != null && updateTime.length() > 0) {
            if (!Pattern.matches(TIME_PATTERN, updateTime)) {
                throw new Exception("Time Not Match");
            }
            foodDeal.setPostId(updateTime);
        }
        if (updateDate != null && updateDate.length() > 0) {
            if (Pattern.matches(DATE_PATTERN, updateDate)) {
                throw new Exception("Date Not Match");
            }
            foodDeal.setPostId(updateDate);
        }
        if (rating != null && rating.length() > 0)
            foodDeal.setRating(rating);
        if (createdBy != null && createdBy.length() > 0)
            foodDeal.setCreatedBy(createdBy);

        patchFoodDeal(id, foodDeal);
    }

    // TODO probably convert this to use RxJava instead
//    public static boolean getFoodDeal(int id) {
//        Log.v(TAG, "Start get FoodDeal");
//        final boolean[] ifSuccess = {false};
//        api.getFoodDeal(id).enqueue(new Callback<FoodDeal>() {
//            @Override
//            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
//                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
//                FoodDeal fd = response.body();
//                if (fd != null) {
//                    DataHolder.getInstance().findAndReplaceFoodDeal(fd); // TODO i need to store this!
//                    ifSuccess[0] = true;
//                    Log.v(TAG, "End get Food Deals");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FoodDeal> call, Throwable t) {
//                Log.d(TAG, "~~FAILED~~");
//                call.cancel();
//            }
//        });
//
//        return ifSuccess[0];
//    }

    public static void postFoodDeal(FoodDeal foodDeal) {
        Log.v(TAG, "Start Post Food Deal");
        api.postFoodDeal(foodDeal).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.v(TAG, "~~End Post Food Deal~~");
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }

    public static void deleteFoodDeal(int id) {
        Log.v(TAG, "Start Delete Food Deal");
        api.deleteFoodDeal(id).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.v(TAG, "~~End Delete Food Deal~~");
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }

    public static void putFoodDeal(int id, FoodDeal foodDeal) {
        Log.v(TAG, "Start Put Food Deal");
        api.putFoodDeal(id, foodDeal).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.v(TAG, "~~End Put Food Deal~~");
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }

    public static void patchFoodDeal(int id, FoodDeal foodDeal) {
        Log.d(TAG, "Start Patching Food Deal");
        api.patchFoodDeal(id, foodDeal).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.d(TAG, "~~End Patching Food Deal~~");
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }
}
