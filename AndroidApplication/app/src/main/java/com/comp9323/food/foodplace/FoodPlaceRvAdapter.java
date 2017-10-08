package com.comp9323.food.foodplace;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.data.DataHolder;
import com.comp9323.main.R;
import com.comp9323.food.foodplace.FoodPlaceFragment.Listener;
import com.google.android.gms.maps.MapView;

import java.io.InputStream;

public class FoodPlaceRvAdapter extends RecyclerView.Adapter<FoodPlaceRvAdapter.ViewHolder> {

    private static boolean mIsLoading = false;
    private static boolean mIsReachEnd = false;
    private final Listener mListener;

    public FoodPlaceRvAdapter(Listener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_food_place_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mPlace = DataHolder.getInstance().getFoodPlace(position);
        holder.mNameView.setText(holder.mPlace.getName());
        holder.mAddressView.setText(holder.mPlace.getLocation());
        holder.mAppRatingView.setText(holder.mPlace.getRating());

        //set google rating if found
        if (holder.mPlace.getGoogleRating().length() > 0) {
            holder.mRatingView.setRating(Float.parseFloat(holder.mPlace.getGoogleRating()));
        }

        //set photo
        if (holder.mPlace.getPhotoLink().length() > 0) {
            downloadPhoto(holder.mPhotoView, holder.mPlace.getPhotoLink());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onFoodPlaceItemClicked(holder.mPlace, holder.mView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataHolder.getInstance().getFoodPlaceList().size();
    }

    public boolean ifLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean bool) {
        mIsLoading = bool;
    }

    public static boolean ifReachEnd() {
        return mIsReachEnd;
    }

    public static void setIsReachEnd(boolean bool) {
        mIsReachEnd = bool;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mAddressView;
        public final TextView mAppRatingView;
        public final MapView mMapView;
        public final RatingBar mRatingView;
        public final ImageView mPhotoView;
        public final ImageButton mLikeButton;
        public final ImageButton mDislikeButton;

        public FoodPlace mPlace;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.food_place_item_name);
            mAddressView = view.findViewById(R.id.food_place_item_address);
            mAppRatingView = view.findViewById(R.id.food_place_item_in_app_rating);
            mRatingView = view.findViewById(R.id.food_place_item_rating_bar);
            mMapView = view.findViewById(R.id.food_place_item_map);
            mPhotoView = view.findViewById(R.id.food_place_item_image);
            mLikeButton = view.findViewById(R.id.food_place_item_like_button);
            mDislikeButton = view.findViewById(R.id.food_place_item_dislike_button);
        }
    }

    public static void downloadPhoto(final ImageView imageView, String url) {
        Bitmap photo = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            photo = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        if (photo != null) {
            imageView.setImageBitmap(photo);
        }
    }
}
