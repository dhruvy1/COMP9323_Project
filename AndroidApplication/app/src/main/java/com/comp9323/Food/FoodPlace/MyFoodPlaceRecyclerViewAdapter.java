package com.comp9323.Food.FoodPlace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.comp9323.AsycnTask.FoodPlaceAsycn;
import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;
import com.comp9323.Food.FoodPlace.FoodPlaceFragment.OnListFoodPlaceInteractionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FoodPlace} and makes a call to the
 * specified {@link  OnListFoodPlaceInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFoodPlaceRecyclerViewAdapter extends RecyclerView.Adapter<MyFoodPlaceRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private static boolean mIsLoading = false;
    private static boolean mIsReachEnd = false;
    private final OnListFoodPlaceInteractionListener mListener;

    public MyFoodPlaceRecyclerViewAdapter( OnListFoodPlaceInteractionListener listener) {
        //mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_foodplace_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mPlace = SingletonDataHolder.getInstance().getFoodPlace(position);
        holder.mNameView.setText(holder.mPlace.getName());
        holder.mAddressView.setText(holder.mPlace.getLocation());
        holder.mAppRatingView.setText(holder.mPlace.getRating());
        //set google rating if found
        if (holder.mPlace.getGoogleRating().length() > 0)
            holder.mRatingView.setRating(Float.parseFloat(holder.mPlace.getGoogleRating()));
        //set photo
        if(holder.mPlace.getPhotoLink().length() > 0)
            DownloadPhoto(holder.mPhotoView, holder.mPlace.getPhotoLink());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFoodPlaceInteraction(holder.mPlace, holder.mView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return SingletonDataHolder.getInstance().getFoodPlaceList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
        //return SingletonDataHolder.getInstance().getFoodDealList().get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    public boolean ifLoading(){
        return mIsLoading;
    }
    public void setIsLoading(boolean bool){mIsLoading = bool;}
    public static boolean ifReachEnd(){
        return mIsReachEnd;
    }
    public static void setIsReachEnd(boolean bool){mIsReachEnd = bool;}

    //Item view of the recycle List
    //need to match the @layout list_item.xml
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
            Log.d("Veiw id", ""+ view.getId());
            Log.d("Veiw id", "item layout"+ R.id.FoodPlace_Item_Layout);


            mNameView = view.findViewById(R.id.FoodPlace_Name);
            mAddressView = view.findViewById(R.id.FoodPlace_Address_TextView);
            mAppRatingView = view.findViewById(R.id.FoodPlace_InApp_Rating_TextView);
            mRatingView = view.findViewById(R.id.FoodPlace_RatingBar);
            mMapView = view.findViewById(R.id.FoodPlace_Map);
            mPhotoView = view.findViewById(R.id.FoodPlace_Image);
            mLikeButton = view.findViewById(R.id.FoodPlace_Like_button);
            mDislikeButton = view.findViewById(R.id.FoodPlace_Dislike_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

    public static void DownloadPhoto(final ImageView imageView, String url){
        Bitmap photo= null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            photo = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        if (photo != null)
            imageView.setImageBitmap(photo);
    }
}
