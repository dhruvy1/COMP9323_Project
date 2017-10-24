package com.comp9323.restclient.service;

import com.comp9323.data.beans.FoodDeal;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodDealApi;

import java.util.List;

import retrofit2.Callback;

public class FoodDealService {
    private static final String TAG = "FoodDealService";

    private static final FoodDealApi api = RestClient.getClient().create(FoodDealApi.class);

    //23:59:59
    private static final String TIME_PATTERN = "^([01]?\\d|2[0-3]):([0-5]?\\d):([0-5]?\\d)$";

    //YYYY-MM-DD
    private static final String DATE_PATTERN = "^(((((1[26]|2[048])00)|[12]\\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|[12]\\d))))|((([12]\\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|1\\d|2[0-8])))))$";

//    public static void postFoodDeal(String postId, String message, String updateTime,
//                                    String photoLink, String eventLink, String updateDate,
//                                    String rating, String createdBy) throws Exception {
//        if (updateTime == null || !Pattern.matches(TIME_PATTERN, updateTime))
//            throw new Exception("Time Not Match");
//        if (updateDate == null || !Pattern.matches(DATE_PATTERN, updateDate))
//            throw new Exception("Date Not Match");
//        FoodDeal fd = new FoodDeal(postId, message, updateTime, photoLink, eventLink, updateDate, rating, createdBy);
//
//        postFoodDeal(fd);
//    }
//
//    public static void putFoodDeal(int id, String postId, String message, String updateTime,
//                                   String photoLink, String eventLink, String updateDate,
//                                   String rating, String createdBy) throws Exception {
//        FoodDeal foodDeal = new FoodDeal();
//        if (postId != null && postId.length() > 0)
//            foodDeal.setPostId(postId);
//        if (message != null && message.length() > 0)
//            foodDeal.setPostId(message);
//        if (photoLink != null && photoLink.length() > 0)
//            foodDeal.setPostId(photoLink);
//        if (eventLink != null && eventLink.length() > 0)
//            foodDeal.setPostId(eventLink);
//        if (updateTime != null && updateTime.length() > 0) {
//            if (!Pattern.matches(TIME_PATTERN, updateTime)) {
//                throw new Exception("Time Not Match");
//            }
//            foodDeal.setPostId(updateTime);
//        }
//        if (updateDate != null && updateDate.length() > 0) {
//            if (Pattern.matches(DATE_PATTERN, updateDate)) {
//                throw new Exception("Date Not Match");
//            }
//            foodDeal.setPostId(updateDate);
//        }
//        if (rating != null && rating.length() > 0)
//            foodDeal.setRating(rating);
//        if (createdBy != null && createdBy.length() > 0)
//            foodDeal.setCreatedBy(createdBy);
//
//        putFoodDeal(id, foodDeal);
//    }
//
//    public static void patchFoodDeal(int id, String postId, String message, String updateTime,
//                                     String photoLink, String eventLink, String updateDate,
//                                     String rating, String createdBy) throws Exception {
//        FoodDeal foodDeal = new FoodDeal();
//        if (postId != null && postId.length() > 0)
//            foodDeal.setPostId(postId);
//        if (message != null && message.length() > 0)
//            foodDeal.setPostId(message);
//        if (photoLink != null && photoLink.length() > 0)
//            foodDeal.setPostId(photoLink);
//        if (eventLink != null && eventLink.length() > 0)
//            foodDeal.setPostId(eventLink);
//        if (updateTime != null && updateTime.length() > 0) {
//            if (!Pattern.matches(TIME_PATTERN, updateTime)) {
//                throw new Exception("Time Not Match");
//            }
//            foodDeal.setPostId(updateTime);
//        }
//        if (updateDate != null && updateDate.length() > 0) {
//            if (Pattern.matches(DATE_PATTERN, updateDate)) {
//                throw new Exception("Date Not Match");
//            }
//            foodDeal.setPostId(updateDate);
//        }
//        if (rating != null && rating.length() > 0)
//            foodDeal.setRating(rating);
//        if (createdBy != null && createdBy.length() > 0)
//            foodDeal.setCreatedBy(createdBy);
//
//        patchFoodDeal(id, foodDeal);
//    }

    public static void getFoodDeal(int id, Callback<FoodDeal> callback) {
        api.getFoodDeal(id).enqueue(callback);
    }

    public static void getFoodDeals(Callback<List<FoodDeal>> callback) {
        api.getFoodDeals().enqueue(callback);
    }

    public static void postFoodDeal(FoodDeal foodDeal, Callback<FoodDeal> callback) {
        api.postFoodDeal(foodDeal).enqueue(callback);
    }

    public static void deleteFoodDeal(int id, Callback<Void> callback) {
        api.deleteFoodDeal(id).enqueue(callback);
    }

    public static void putFoodDeal(int id, FoodDeal foodDeal, Callback<FoodDeal> callback) {
        api.putFoodDeal(id, foodDeal).enqueue(callback);
    }

    public static void patchFoodDeal(int id, FoodDeal foodDeal, Callback<FoodDeal> callback) {
        api.patchFoodDeal(id, foodDeal).enqueue(callback);
    }
}
