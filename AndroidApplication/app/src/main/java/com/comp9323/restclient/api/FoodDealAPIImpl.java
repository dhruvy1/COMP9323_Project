package com.comp9323.restclient.api;

import android.util.Log;

import com.comp9323.food.fooddeal.FoodDealRvAdapter;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodDealResponse;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.restclient.RestClient;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDealAPIImpl {
    private static final FoodDealAPI api = RestClient.getClient().create(FoodDealAPI.class);

    //23:59:59
    private static final String TIME_PATTERN = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

    //YYYY-MM-DD
    private static final String DATE_PATTERN = "^(((((1[26]|2[048])00)|[12]\\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|[12]\\d))))|((([12]\\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|1\\d|2[0-8])))))$";

    public static boolean postFoodDeal(String postId, String message, String updateTime,
                                       String photoLink, String eventLink, String updateDate,
                                       String rating, String createdBy) throws Exception {
        if (updateTime == null || !Pattern.matches(TIME_PATTERN, updateTime))
            throw new Exception("Time Not Match");
        if (updateDate == null || !Pattern.matches(DATE_PATTERN, updateDate))
            throw new Exception("Date Not Match");
        FoodDeal fd = new FoodDeal(postId, message, updateTime, photoLink, eventLink, updateDate, rating, createdBy);

        return postFoodDeal(fd);

    }

    public static boolean putFoodDeal(int id, String postid, String message, String updatetime,
                                      String photolink, String eventlink, String updatedate,
                                      String rating, String createdBy) throws Exception {
        FoodDeal fd = DataHolder.getInstance().getFoodDealwithID(id);
        if (fd == null) return false;
        if (postid != null && postid.length() > 0)
            fd.setPostId(postid);
        if (message != null && message.length() > 0)
            fd.setPostId(message);
        if (photolink != null && photolink.length() > 0)
            fd.setPostId(photolink);
        if (eventlink != null && eventlink.length() > 0)
            fd.setPostId(eventlink);
        if (updatetime != null && updatetime.length() > 0) {
            if (!Pattern.matches(TIME_PATTERN, updatetime)) {
                throw new Exception("Time Not Match");
            }
            fd.setPostId(updatetime);
        }
        if (updatedate != null && updatedate.length() > 0) {
            if (Pattern.matches(DATE_PATTERN, updatedate)) {
                throw new Exception("Date Not Match");
            }
            fd.setPostId(updatedate);
        }
        if (rating != null && rating.length() > 0)
            fd.setRating(rating);
        if (createdBy != null && createdBy.length() > 0)
            fd.setCreatedBy(createdBy);

        return putFoodDeal(id, fd);
    }

    public static boolean patchFoodDeal(int id, String postId, String message, String updateTime,
                                        String photoLink, String eventLink, String updateDate,
                                        String rating, String createdBy) throws Exception {
        FoodDeal template = new FoodDeal();
        if (postId != null && postId.length() > 0)
            template.setPostId(postId);
        if (message != null && message.length() > 0)
            template.setPostId(message);
        if (photoLink != null && photoLink.length() > 0)
            template.setPostId(photoLink);
        if (eventLink != null && eventLink.length() > 0)
            template.setPostId(eventLink);
        if (updateTime != null && updateTime.length() > 0) {
            if (!Pattern.matches(TIME_PATTERN, updateTime)) {
                throw new Exception("Time Not Match");
            }
            template.setPostId(updateTime);
        }
        if (updateDate != null && updateDate.length() > 0) {
            if (Pattern.matches(DATE_PATTERN, updateDate)) {
                throw new Exception("Date Not Match");
            }
            template.setPostId(updateDate);
        }
        if (rating != null && rating.length() > 0)
            template.setRating(rating);
        if (createdBy != null && createdBy.length() > 0)
            template.setCreatedBy(createdBy);

        return patchFoodDeal(id, template);
    }

    public static boolean deleteFoodDeal(int id) {
        Log.v("Rest Call", "Start delete FoodDeal");
        final boolean[] ifSuccess = {false};
        api.deleteFoodDeal(id).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End delete Food Deals");
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    public static boolean getFoodDeals(int page) {
        Log.v("Rest Call", "Start Create Food Deal");
        final boolean[] ifSuccess = {false};
        final boolean[] ifNdone = {true};
        api.getFoodDeals(3, (page - 1) * 3).enqueue(new Callback<FoodDealResponse>() {
            @Override
            public void onResponse(Call<FoodDealResponse> call, Response<FoodDealResponse> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                FoodDealResponse newPackage = response.body();
                if (newPackage != null) {
                    if (newPackage.getResults().size() != 0)
                        ifSuccess[0] = response.isSuccessful();
                    if (newPackage.getNextUrl() == null || newPackage.getNextUrl().compareTo("null") == 0)
                        // if(newPackage.getCount() == DataHolder.getInstance().getFoodDealList().size())
                        FoodDealRvAdapter.setIsReachEnd(true);
                    for (FoodDeal fd : newPackage.getResults()) {
                        DataHolder.getInstance().addFoodDeal(fd);
                        Log.d("Rest Debug Print", fd.getId() + "");
                    }
                }
                ifNdone[0] = false;
                Log.v("Rest Call", "End Create Food Deal");
            }

            @Override
            public void onFailure(Call<FoodDealResponse> call, Throwable t) {
                Log.d("Rest Call", "~~FAILED~~");
                ifNdone[0] = false;
                call.cancel();
            }
        });
        while (ifNdone[0]) {
            //holding the process until on response is complete
            Log.d("Rest call", "Size of list :" + DataHolder.getInstance().getFoodDealList().size());
        }

        return ifSuccess[0];
    }

    private static boolean postFoodDeal(FoodDeal foodDeal) {
        Log.v("Rest Call", "Start Create Food Deal");
        final boolean[] ifSuccess = {false};
        api.postFoodDeal(foodDeal).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                FoodDeal newfd = response.body();
                if (newfd != null) {
                    //TODO should i store it?
                    DataHolder.getInstance().addFoodDeal(newfd);
                    ifSuccess[0] = true;
                }
                Log.v("Rest Call", "End Create Food Deal");
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d("Rest Call", "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    public static boolean getFoodDeal(int id) {
        Log.v("Rest Call", "Start get FoodDeal");
        final boolean[] ifSuccess = {false};
        api.getFoodDeal(id).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                FoodDeal fd = response.body();
                if (fd != null) {
                    DataHolder.getInstance().findAndReplaceFoodDeal(fd); // TODO i need to store this!
                    ifSuccess[0] = true;
                    Log.v("Rest Call", "End get Food Deals");
                }
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    private static boolean putFoodDeal(int id, FoodDeal foodDeal) {
        Log.v("Rest Call", "start Edit FoodDeal");
        final boolean[] ifSuccess = {false};
        api.putFoodDeal(id, foodDeal).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Edit FoodDeal");
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }

    private static boolean patchFoodDeal(int id, FoodDeal foodDeal) {
        Log.v("Rest Call", "start Edit FoodDeal By Fields");
        final boolean[] ifSuccess = {false};
        api.patchFoodDeal(id, foodDeal).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Edit FoodDeal By Fields");
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });

        return ifSuccess[0];
    }
}
