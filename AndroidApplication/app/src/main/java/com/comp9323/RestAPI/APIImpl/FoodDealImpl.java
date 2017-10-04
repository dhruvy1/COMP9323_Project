package com.comp9323.RestAPI.APIImpl;

import android.util.Log;

import com.comp9323.RestAPI.APIInterface.FoodDealInterface;
import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.MainActivity;

import java.util.Vector;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDealImpl {
    private static final FoodDealInterface apiInterface = RestClient.getClient().create(FoodDealInterface.class);
    private static final String TIME_PATTERN = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";
    private static final String DATE_PATTERN = "^(((((1[26]|2[048])00)|[12]\\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|[12]\\d))))|((([12]\\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|1\\d|2[0-8])))))$";

    public static boolean createFoodDeal(String postId, String message, String updateTime, String photoLink, String eventLink, String updateDate) throws Exception {
        if (updateTime == null || !Pattern.matches(TIME_PATTERN, updateTime))
            throw new Exception("Time Not Match");
        if (updateDate == null || !Pattern.matches(DATE_PATTERN, updateDate))
            throw new Exception("Date Not Match");
        FoodDeal fd = new FoodDeal(postId, message, updateTime, photoLink, eventLink, updateDate);
        return createFoodDeal(fd);

    }

    public static boolean editFoodDealByFields(int id, String postId, String message, String updateTime, String photoLink, String eventLink, String updateDate) throws Exception {
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
        return editFoodDealByFields(id, template);
    }

    public static boolean editFoodDeal(int id, String postid, String message, String updatetime, String photolink, String eventlink, String updatedate) throws Exception{
        FoodDeal fd = SingletonDataHolder.getInstance().getFoodDealwithID(id);
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
        return editFoodDeal(id, fd);
    }

    public static boolean getFoodDeals(int page) {
        Log.v("Rest Call", "Start Create Food Deal");
        final boolean[] ifSuccess = {false};
        final boolean[] ifNdone = {true};
        apiInterface.getFoodDeals(page).enqueue(new Callback<Vector<FoodDeal>>() {
            @Override
            public void onResponse(Call<Vector<FoodDeal>> call, Response<Vector<FoodDeal>> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                Vector<FoodDeal> fooddeals = response.body();
                if (fooddeals != null) {
                    if (fooddeals.size() != 0)
                        ifSuccess[0] = response.isSuccessful();

                    for (FoodDeal fd : fooddeals) {
                        SingletonDataHolder.getInstance().addFoodDeal(fd);
                        Log.d("Rest Debug Print", fd.getId() + "");
                    }
                }
                ifNdone[0] = false;
                Log.v("Rest Call", "End Create Food Deal");
            }

            @Override
            public void onFailure(Call<Vector<FoodDeal>> call, Throwable t) {
                Log.d("Rest Call", "~~FAILED~~");
                ifNdone[0] = false;
                ifSuccess[0] = true;
                call.cancel();
            }
        });
        while(ifNdone[0]){
            Log.d("Rest call", "Size of list :" +SingletonDataHolder.getInstance().getFoodDealList().size());
        }
        if (!ifNdone[0] && !ifSuccess[0])
            return false;
        return ifSuccess[0];
    }

    public static boolean deleteFoodDeal(int id) {
        Log.v("Rest Call", "Start delete FoodDeal");
        final boolean[] ifSuccess = {false};
        apiInterface.deleteFoodDeal(id).enqueue(new Callback<Response<Void>>() {
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

    public static boolean getFoodDeal(int id) {
        Log.v("Rest Call", "Start get FoodDeal");
        final boolean[] ifSuccess = {false};
        apiInterface.getFoodDeal(id).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                FoodDeal fd = response.body();
                if (fd != null) {
                    SingletonDataHolder.getInstance().addFoodDeal(fd);//TODO Should i store like this
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

    private static boolean createFoodDeal(FoodDeal fd) {
        Log.v("Rest Call", "Start Create Food Deal");
        final boolean[] ifSuccess = {false};
        apiInterface.createFoodDeal(fd).enqueue(new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                FoodDeal newfd = response.body();
                if (newfd != null) {
                    //TODO should i store it?
                    SingletonDataHolder.getInstance().addFoodDeal(newfd);
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

    private static boolean editFoodDealByFields(int id, FoodDeal fd) {
        Log.v("Rest Call", "start Edit FoodDeal By Fields");
        final boolean[] ifSuccess = {false};
        apiInterface.editFoodDealByFields(id, fd).enqueue(new Callback<FoodDeal>() {
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

    private static boolean editFoodDeal(int id, FoodDeal fd) {
        Log.v("Rest Call", "start Edit FoodDeal");
        final boolean[] ifSuccess = {false};
        apiInterface.editFoodDeal(id, fd).enqueue(new Callback<FoodDeal>() {
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
}
