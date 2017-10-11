package com.comp9323.restclient.api;

import android.util.Log;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.restclient.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodPlaceService {
    private final static String TAG = "FoodPlaceService";

    private static final FoodPlaceApi api = RestClient.getClient().create(FoodPlaceApi.class);

//    public static void postFoodPlace(String name, String location, String priceLevel,
//                                     String googleRating, String latitude, String longitude,
//                                     String photoLink, String rating, String createdBy) {
//        FoodPlace fp = new FoodPlace(name, location, priceLevel, googleRating, latitude, longitude, photoLink, rating, createdBy);
//
//        postFoodPlace(fp);
//    }
//
//    public static void putFoodPlace(int id, String name, String location, String priceLevel,
//                                    String googleRating, String latitude, String longitude,
//                                    String photoLink, String rating, String createdBy) {
//        FoodPlace fp = new FoodPlace();
//        if (name != null && name.length() > 0)
//            fp.setName(name);
//        if (location != null && location.length() > 0)
//            fp.setLocation(location);
//        if (priceLevel != null && priceLevel.length() > 0)
//            fp.setPriceLevel(priceLevel);
//        if (googleRating != null && googleRating.length() > 0)
//            fp.setGoogleRating(googleRating);
//        if (latitude != null && latitude.length() > 0)
//            fp.setLatitude(latitude);
//        if (longitude != null && longitude.length() > 0)
//            fp.setLongitude(longitude);
//        if (photoLink != null && photoLink.length() > 0)
//            fp.setPhotoLink(photoLink);
//        if (rating != null && rating.length() > 0)
//            fp.setRating(rating);
//        if (createdBy != null && createdBy.length() > 0)
//            fp.setCreatedBy(createdBy);
//
//        putFoodPlace(id, fp);
//    }
//
//    public static void patchFoodPlace(int id, String name, String location, String priceLevel,
//                                      String googleRating, String latitude, String longitude,
//                                      String photoLink, String rating, String createdBy) {
//        FoodPlace template = new FoodPlace();
//        if (name != null && name.length() > 0)
//            template.setName(name);
//        if (location != null && location.length() > 0)
//            template.setLocation(location);
//        if (priceLevel != null && priceLevel.length() > 0)
//            template.setPriceLevel(priceLevel);
//        if (googleRating != null && googleRating.length() > 0)
//            template.setGoogleRating(googleRating);
//        if (latitude != null && latitude.length() > 0)
//            template.setLatitude(latitude);
//        if (longitude != null && longitude.length() > 0)
//            template.setLongitude(longitude);
//        if (photoLink != null && photoLink.length() > 0)
//            template.setPhotoLink(photoLink);
//        if (rating != null && rating.length() > 0)
//            template.setRating(rating);
//        if (createdBy != null && createdBy.length() > 0)
//            template.setCreatedBy(createdBy);
//
//        patchFoodPlace(id, template);
//    }

    public static void getFoodPlace(int id, Callback<FoodPlace> callback) {
        api.getFoodPlace(id).enqueue(callback);
    }

    public static void getFoodPlaces(Callback<List<FoodPlace>> callback) {
        api.getFoodPlaces().enqueue(callback);
    }

    public static void postFoodPlace(FoodPlace foodPlace, Callback<FoodPlace> callback) {
        api.postFoodPlace(foodPlace).enqueue(callback);
    }

    public static void deleteFoodPlace(int id, Callback<Void> callback) {
        api.deleteFoodPlace(id).enqueue(callback);
    }

    public static void putFoodPlace(int id, FoodPlace foodPlace, Callback<FoodPlace> callback) {
        api.putFoodPlace(id, foodPlace).enqueue(callback);
    }

    public static void patchFoodPlace(int id, FoodPlace foodPlace, Callback<FoodPlace> callback) {
        api.patchFoodPlace(id, foodPlace).enqueue(callback);
    }
}
