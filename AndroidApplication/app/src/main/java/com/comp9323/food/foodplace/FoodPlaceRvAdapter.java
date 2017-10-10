package com.comp9323.food.foodplace;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;

import java.util.ArrayList;
import java.util.List;

public class FoodPlaceRvAdapter extends RecyclerView.Adapter<FoodPlaceRvAdapter.ViewHolder> {
    private static final String TAG = "FoodPlaceRvAdapter";

    private Context context;
    private Listener listener;
    private List<FoodPlace> foodPlaces;
    private SparseArray<FoodPlaceDetailFragment> expandedList;

    public FoodPlaceRvAdapter(Context context) {
        this.context = context;
        foodPlaces = new ArrayList<>();
        expandedList = new SparseArray<>();
    }

    public void setFoodPlaces(List<FoodPlace> foodPlaces) {
        this.foodPlaces = foodPlaces;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_food_place_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.foodPlace = foodPlaces.get(position);
        holder.nameView.setText(holder.foodPlace.getName());

        setGoogleRatingBar(holder);

        initFoodPlaceViewClick(holder, position);


//        holder.appRatingView.setText(holder.foodPlace.getRating());
//        holder.addressView.setText(holder.foodPlace.getLocation());


        //set photo
//        if (holder.foodPlace.getPhotoLink().length() > 0) {
//            downloadPhoto(holder.photoView, holder.foodPlace.getPhotoLink());
//        }

//        if (holder.foodPlace.getPhotoLink().length() > 0) {
//            Glide.with(holder.photoView.getContext()).load(holder.foodPlace.getPhotoLink()).into(holder.photoView);
//        }
    }

    @Override
    public int getItemCount() {
        return foodPlaces.size();
    }

    private void initFoodPlaceViewClick(final ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandedList.get(position) != null) {
                    // remove from list
                    removeFoodPlaceDetails(expandedList.get(position));
                } else {
                    // add to list
                    FoodPlaceDetailFragment fragment = new FoodPlaceDetailFragment();
                    fragment.setFoodPlace(holder.foodPlace);
                    expandedList.put(position, fragment);
                    showFoodPlaceDetails(fragment);
                }
            }
        });
    }

    private void showFoodPlaceDetails(FoodPlaceDetailFragment fragment) {
        android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.food_place_item_detail_placeholder, fragment);
        ft.commit();
    }

    private void removeFoodPlaceDetails(FoodPlaceDetailFragment fragment) {
        android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    private void setGoogleRatingBar(ViewHolder holder) {
        if (holder.foodPlace.getGoogleRating().length() > 0) {
            holder.ratingView.setRating(Float.parseFloat(holder.foodPlace.getGoogleRating()));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoodPlace foodPlace;
        public final View view;
        public final TextView nameView;
        public final RatingBar ratingView;

//        public final TextView appRatingView;
//        public final TextView addressView;
//        public final MapView mapView;
//        public final ImageView photoView;
//        public final ImageButton likeBtn;
//        public final ImageButton dislikeBtn;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            nameView = view.findViewById(R.id.food_place_item_name);
            ratingView = view.findViewById(R.id.food_place_item_rating_bar);

//            appRatingView = view.findViewById(R.id.food_place_item_in_app_rating);
//            addressView = view.findViewById(R.id.food_place_item_address);
//            mapView = view.findViewById(R.id.food_place_item_map);
//            photoView = view.findViewById(R.id.food_place_item_img);
//            likeBtn = view.findViewById(R.id.food_place_item_like_btn);
//            dislikeBtn = view.findViewById(R.id.food_place_item_dislike_btn);
        }
    }

    public interface Listener {

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
