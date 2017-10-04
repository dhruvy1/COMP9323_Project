package com.comp9323.Food.FoodPlace;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;
import com.comp9323.Food.FoodPlace.FoodPlaceFragment.OnListFoodPlaceInteractionListener;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mPlace = SingletonDataHolder.getInstance().getFoodPlace(position);
        holder.mNameView.setText(holder.mPlace.getName());
        //holder.mLocationView.setText(holder.mPlace.getLocation());
        if (holder.mPlace.getGoogleRating().length() > 0)
            holder.mRatingView.setRating(Float.parseFloat(holder.mPlace.getGoogleRating()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFoodPlaceInteraction(holder.mPlace);
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
    public boolean ifReachEnd(){
        return mIsReachEnd;
    }
    public void setIsReachEnd(boolean bool){mIsReachEnd = bool;}

    //Item view of the recycle List
    //need to match the @layout list_item.xml
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
       // public final TextView mLocationView;
        public final RatingBar mRatingView;
        public FoodPlace mPlace;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            Log.d("Veiw id", ""+ view.getId());
            Log.d("Veiw id", "item title"+ R.id.FoodPlace_Item_Title);
            Log.d("Veiw id", "item layout"+ R.id.FoodPlace_Item_Layout);
            Log.d("Veiw id", "item place list"+ R.id.foodplace_list);
            Log.d("Veiw id", "item vertical"+ R.id.FoodPlace_Item_Layout2);
            Log.d("Veiw id", "item detail"+ R.id.FoodPlace_Item_Detail);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view == mView.findViewById(R.id.FoodPlace_Item_Title)) {
                        LinearLayout detail = mView.findViewById(R.id.FoodPlace_Item_Detail);
                        if (detail.getVisibility() != View.VISIBLE)
                            detail.setVisibility(View.VISIBLE);
                        else
                            detail.setVisibility(View.GONE);
                    }
                }
            });
            mNameView = view.findViewById(R.id.FoodPlace_Name);
            //mLocationView = view.findViewById(R.id.FoodPlace_Location);
            mRatingView = view.findViewById(R.id.FoodPlace_RatingBar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
