package com.comp9323.RestAPI.APIImpl;

import android.util.Log;

import com.comp9323.RestAPI.APIInterface.FoodPlaceInterface;
import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodPlaceImpl{
    private static final FoodPlaceInterface apiInterface = RestClient.getClient().create(FoodPlaceInterface.class);

    public static boolean createFoodPlace(String name, String location, String priceLevel, String googleRating, String latitude, String longitude ) {
        FoodPlace fp = new FoodPlace(name, location, priceLevel, googleRating, latitude, longitude);
        return createFoodPlace(fp);
    }
    public static boolean editFoodPlaceByFields(int id, String name, String location, String priceLevel, String googleRating, String latitude, String longitude) {
        FoodPlace template = new FoodPlace();
        if(name != null && name.length() > 0)
            template.setName(name);
        if(location != null && location.length() > 0)
            template.setLocation(location);
        if(priceLevel != null && priceLevel.length() > 0)
            template.setPriceLevel(priceLevel);
        if(googleRating != null && googleRating.length() > 0)
            template.setGoogleRating(googleRating);
        if(latitude != null && latitude.length() > 0)
            template.setLatitude(latitude);
        if(longitude != null && longitude.length() > 0)
            template.setLongitude(longitude);

        return editFoodPlaceByFields(id,template);
    }

    public static boolean editFoodPlace(int id, String name, String location, String priceLevel, String googleRating, String latitude, String longitude) {
        FoodPlace fp = SingletonDataHolder.getInstance().getFoodPlacewithID(id);
        if(name != null && name.length() > 0)
            fp.setName(name);
        if(location != null && location.length() > 0)
            fp.setLocation(location);
        if(priceLevel != null && priceLevel.length() > 0)
            fp.setPriceLevel(priceLevel);
        if(googleRating != null && googleRating.length() > 0)
            fp.setGoogleRating(googleRating);
        if(latitude != null && latitude.length() > 0)
            fp.setLatitude(latitude);
        if(longitude != null && longitude.length() > 0)
            fp.setLongitude(longitude);
        
        return editFoodPlace(id, fp);
    }


    public static boolean getFoodPlaces(int page) {
        Log.v("Rest Call", "Start Create Food Deal");
        final boolean[] ifSuccess = {false};
        final String[] flag = {null};
        apiInterface.getFoodPlaces(page).enqueue(new Callback<Vector<FoodPlace>>() {
            @Override
            public void onResponse(Call<Vector<FoodPlace>> call, Response<Vector<FoodPlace>> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                Vector<FoodPlace> FoodPlaces = response.body();
                if (FoodPlaces != null) {
                    ifSuccess[0] = response.isSuccessful();
                    for (FoodPlace fd : FoodPlaces) {
                        SingletonDataHolder.getInstance().addFoodPlace(fd);
                        Log.d("Rest Debug Print", fd.getId() + "");
                    }
                }
                flag[0] = "done";
                Log.v("Rest Call", "End Create Food Deal");
            }

            @Override
            public void onFailure(Call<Vector<FoodPlace>> call, Throwable t) {
                Log.d("Rest Call", "~~FAILED~~");
                flag[0] = "fail";
                call.cancel();
            }
        });
        while(flag[0] == null){
            Log.d("Rest call", "Size of list :" +SingletonDataHolder.getInstance().getFoodPlaceList().size());
        }
        return ifSuccess[0];
    }

    public static boolean deleteFoodPlace(int id) {
        Log.v("Rest Call", "Start delete FoodPlace");
        final boolean[] ifSuccess = {false};
        apiInterface.deleteFoodPlace(id).enqueue(new Callback<Response<Void>>() {
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

    public static boolean getFoodPlace(int id) {
        Log.v("Rest Call", "Start get FoodPlace");
        final boolean[] ifSuccess = {false};
        apiInterface.getFoodPlace(id).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                FoodPlace fd = response.body();
                if (fd != null) {
                    SingletonDataHolder.getInstance().addFoodPlace(fd);//TODO Should i store like this
                    ifSuccess[0] = true;
                    Log.v("Rest Call", "End get Food Deals");
                }
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static boolean editFoodPlaceByFields(int id, FoodPlace fp) {
        Log.v("Rest Call", "start Edit FoodPlace By Fields");
        final boolean[] ifSuccess = {false};
        apiInterface.editFoodPlaceByFields(id, fp).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Edit FoodPlace By Fields");
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static boolean editFoodPlace(int id, FoodPlace fp) {
        Log.v("Rest Call", "start Edit FoodPlace");
        final boolean[] ifSuccess = {false};
        apiInterface.editFoodPlace(id, fp).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                ifSuccess[0] = response.isSuccessful();
                Log.v("Rest Call", "End Edit FoodPlace");
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d("REST CALL", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }

    private static boolean createFoodPlace(FoodPlace fp){
        Log.v("Rest Call", "Start Create Food Place");
        final boolean[] ifSuccess = {false};
        apiInterface.createFoodPlace(fp).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d("Rest Call", "Is response success?" + response.isSuccessful());
                FoodPlace newfp = response.body();
                if(newfp != null){
                    //TODO should i store it?
                    SingletonDataHolder.getInstance().addFoodPlace(newfp);
                    ifSuccess[0] = true;
                }
                Log.v("Rest Call", "End Create Food Place");
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.d("Rest Call", "~~FAILED~~");
                call.cancel();
            }
        });
        return ifSuccess[0];
    }
}
