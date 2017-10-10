package com.comp9323.restclient.api;

import android.util.Log;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.restclient.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodPlaceApiImpl {
    private final static String TAG = "FoodPlaceApiImpl";

    private static final FoodPlaceApi api = RestClient.getClient().create(FoodPlaceApi.class);

    public static void postFoodPlace(String name, String location, String priceLevel,
                                     String googleRating, String latitude, String longitude,
                                     String photoLink, String rating, String createdBy) {
        FoodPlace fp = new FoodPlace(name, location, priceLevel, googleRating, latitude, longitude, photoLink, rating, createdBy);

        postFoodPlace(fp);
    }

    public static void putFoodPlace(int id, String name, String location, String priceLevel,
                                    String googleRating, String latitude, String longitude,
                                    String photoLink, String rating, String createdBy) {
        FoodPlace fp = new FoodPlace();
        if (name != null && name.length() > 0)
            fp.setName(name);
        if (location != null && location.length() > 0)
            fp.setLocation(location);
        if (priceLevel != null && priceLevel.length() > 0)
            fp.setPriceLevel(priceLevel);
        if (googleRating != null && googleRating.length() > 0)
            fp.setGoogleRating(googleRating);
        if (latitude != null && latitude.length() > 0)
            fp.setLatitude(latitude);
        if (longitude != null && longitude.length() > 0)
            fp.setLongitude(longitude);
        if (photoLink != null && photoLink.length() > 0)
            fp.setPhotoLink(photoLink);
        if (rating != null && rating.length() > 0)
            fp.setRating(rating);
        if (createdBy != null && createdBy.length() > 0)
            fp.setCreatedBy(createdBy);

        putFoodPlace(id, fp);
    }

    public static void patchFoodPlace(int id, String name, String location, String priceLevel,
                                      String googleRating, String latitude, String longitude,
                                      String photoLink, String rating, String createdBy) {
        FoodPlace template = new FoodPlace();
        if (name != null && name.length() > 0)
            template.setName(name);
        if (location != null && location.length() > 0)
            template.setLocation(location);
        if (priceLevel != null && priceLevel.length() > 0)
            template.setPriceLevel(priceLevel);
        if (googleRating != null && googleRating.length() > 0)
            template.setGoogleRating(googleRating);
        if (latitude != null && latitude.length() > 0)
            template.setLatitude(latitude);
        if (longitude != null && longitude.length() > 0)
            template.setLongitude(longitude);
        if (photoLink != null && photoLink.length() > 0)
            template.setPhotoLink(photoLink);
        if (rating != null && rating.length() > 0)
            template.setRating(rating);
        if (createdBy != null && createdBy.length() > 0)
            template.setCreatedBy(createdBy);

        patchFoodPlace(id, template);
    }

    // TODO probably convert this use to RxJava
//    public static boolean getFoodPlace(int id) {
//        Log.d()("Rest Call", "Start get FoodPlace");
//        final boolean[] ifSuccess = {false};
//        api.getFoodPlace(id).enqueue(new Callback<FoodPlace>() {
//            @Override
//            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
//                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
//                FoodPlace fd = response.body();
//                if (fd != null) {
//                    DataHolder.getInstance().findAndReplaceFoodPlace(fd);//TODO Should i store like this
//                    ifSuccess[0] = true;
//                    Log.d()("Rest Call", "End get Food Deals");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FoodPlace> call, Throwable t) {
//                Log.d("REST CALL", "~~FAILED~~");
//                call.cancel();
//            }
//        });
//
//        return ifSuccess[0];
//    }

    public static void postFoodPlace(FoodPlace fp) {
        Log.d(TAG, "Start Post Food Place");
        api.postFoodPlace(fp).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.d(TAG, "~~End Post Food Place~~");
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }

    public static void deleteFoodPlace(int id) {
        Log.d(TAG, "Start Delete Food Place");
        api.deleteFoodPlace(id).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.d(TAG, "End Delete Food Place");
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }

    public static void putFoodPlace(int id, FoodPlace fp) {
        Log.d(TAG, "Start Put Food Place");
        api.putFoodPlace(id, fp).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.d(TAG, "~~End Put Food Place~~");
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }

    public static void patchFoodPlace(int id, FoodPlace foodPlace) {
        Log.d(TAG, "Start Patch Food Place");
        api.patchFoodPlace(id, foodPlace).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d(TAG, "Response is successful? " + response.isSuccessful());
                Log.d(TAG, "~~End Patch Food Place~~");
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d(TAG, "~~FAILED~~");
                Log.d(TAG, t.toString());
                call.cancel();
            }
        });
    }
}
