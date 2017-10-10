package com.comp9323.restclient.api;

import android.util.Log;

import com.comp9323.food.foodplace.FoodPlaceRvAdapter;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodPlace;
import com.comp9323.data.beans.FoodPlaceResponse;
import com.comp9323.restclient.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodPlaceApiImpl {
    private static final FoodPlaceApi api = RestClient.getClient().create(FoodPlaceApi.class);

    public static boolean postFoodPlace(String name, String location, String priceLevel,
                                        String googleRating, String latitude, String longitude,
                                        String photoLink, String rating, String createdBy) {
        FoodPlace fp = new FoodPlace(name, location, priceLevel, googleRating, latitude, longitude, photoLink, rating, createdBy);

        return postFoodPlace(fp);
    }

    public static boolean putFoodPlace(int id, String name, String location, String priceLevel,
                                       String googleRating, String latitude, String longitude,
                                       String photoLink, String rating, String createdBy) {
        FoodPlace fp = DataHolder.getInstance().getFoodPlaceWithID(id);
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

        return putFoodPlace(id, fp);
    }

    public static boolean patchFoodPlace(int id, String name, String location, String priceLevel,
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

        return patchFoodPlace(id, template);
    }

    public static boolean getFoodPlace(int id) {
        Log.v("Rest Call", "Start get FoodPlace");
        final boolean[] ifSuccess = {false};
        api.getFoodPlace(id).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d("LOG_TAG", "Is response success? " + response.isSuccessful());
                FoodPlace fd = response.body();
                if (fd != null) {
                    DataHolder.getInstance().findAndReplaceFoodPlace(fd);//TODO Should i store like this
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

    public static boolean getFoodPlaces(int page) {
        Log.v("Rest Call", "Start get Food place");
        final boolean[] ifSuccess = {false};
        final boolean[] ifNdone = {true};
        api.getFoodPlaces(20, (page - 1) * 20).enqueue(new Callback<FoodPlaceResponse>() {
            @Override
            public void onResponse(Call<FoodPlaceResponse> call, Response<FoodPlaceResponse> response) {
                Log.d("Rest Call", "Is response success? " + response.isSuccessful());
                FoodPlaceResponse newPackage = response.body();
                if (newPackage != null) {
                    if (newPackage.getResults().size() != 0)
                        ifSuccess[0] = response.isSuccessful();
                    if (newPackage.getNextUrl() == null || newPackage.getNextUrl().compareTo("null") == 0)
                        FoodPlaceRvAdapter.setIsReachEnd(true);
                    for (FoodPlace fd : newPackage.getResults()) {
                        DataHolder.getInstance().addFoodPlace(fd);
                        Log.d("Rest Debug Print", fd.getId() + "");
                    }
                }
                ifNdone[0] = false;
                Log.v("Rest Call", "End pulling Food place");
            }

            @Override
            public void onFailure(Call<FoodPlaceResponse> call, Throwable t) {
                Log.d("Rest Fail", "" + t.getStackTrace().toString());
                Log.d("Rest Call", "~~FAILED~~");
                ifNdone[0] = false;
                call.cancel();
            }
        });
        while (ifNdone[0]) {
            Log.d("Rest call", "Size of list :" + DataHolder.getInstance().getFoodPlaceList().size());
        }

        return ifSuccess[0];
    }

    private static boolean postFoodPlace(FoodPlace fp) {
        Log.v("Rest Call", "Start Create Food Place");
        final boolean[] ifSuccess = {false};
        api.postFoodPlace(fp).enqueue(new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                Log.d("Rest Call", "Is response success?" + response.isSuccessful());
                FoodPlace newfp = response.body();
                if (newfp != null) {
                    //TODO should i store it?
                    DataHolder.getInstance().addFoodPlace(newfp);
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

    public static boolean deleteFoodPlace(int id) {
        Log.v("Rest Call", "Start delete FoodPlace");
        final boolean[] ifSuccess = {false};
        api.deleteFoodPlace(id).enqueue(new Callback<Response<Void>>() {
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

    private static boolean putFoodPlace(int id, FoodPlace fp) {
        Log.v("Rest Call", "start Edit FoodPlace");
        final boolean[] ifSuccess = {false};
        api.putFoodPlace(id, fp).enqueue(new Callback<FoodPlace>() {
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

    private static boolean patchFoodPlace(int id, FoodPlace fp) {
        Log.v("Rest Call", "start Edit FoodPlace By Fields");
        final boolean[] ifSuccess = {false};
        api.patchFoodPlace(id, fp).enqueue(new Callback<FoodPlace>() {
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
}
