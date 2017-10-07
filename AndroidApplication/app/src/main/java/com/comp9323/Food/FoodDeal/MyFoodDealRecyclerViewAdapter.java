package com.comp9323.Food.FoodDeal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comp9323.AsycnTask.DownloadPhoto;
import com.comp9323.AsycnTask.FoodDealAsycn;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;
import com.comp9323.Food.FoodDeal.FoodDealFragment.OnListFooDealInteractionListener;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FoodDeal} and makes a call to the
 * specified {@link OnListFooDealInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFoodDealRecyclerViewAdapter extends RecyclerView.Adapter<MyFoodDealRecyclerViewAdapter.FoodDealViewHolder> {

    //private final List<FoodDeal> mFoodDeals;
    private static boolean mIsLoading = false;
    private static boolean mIsReachEnd = false;
    private final OnListFooDealInteractionListener FoodDeal_Fragment_listener;
//    private final int VIEW_TYPE_ITEM = 0;
//    private final int VIEW_TYPE_LOADING = 1;

    public MyFoodDealRecyclerViewAdapter(FoodDealFragment.OnListFooDealInteractionListener listener) {
      //  mFoodDeals = foodDeals;
        FoodDeal_Fragment_listener = listener;
       // Log.d("Adapter", "size: "+ mFoodDeals.size());
    }

    @Override
    public FoodDealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fooddeal_item, parent, false);
            return new FoodDealViewHolder(view);
//        }else if (viewType == VIEW_TYPE_LOADING){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fooddeal_item, parent, false);
//            return new LastViewHolder(view);
//        }else
//            return null;
    }

    @Override
    public void onBindViewHolder(final FoodDealViewHolder holder, int position) {

        // set value that display in the list
        holder.mFoodDeal = SingletonDataHolder.getInstance().getFoodDealList().get(position);
        holder.mTextView.setText(holder.mFoodDeal.getMessage());
        holder.mRating.setText((holder.mFoodDeal.getRating()));
        //Set pulled image
        //TODO function is working, but it will take up lots of processing time and messed other Asycn 
//        if (holder.mFoodDeal.getPhotoLink().length() > 0 ){
//            //slow if first load
//            new DownloadPhoto(holder.mImageView, FoodDealFragment.mAdapter).execute(holder.mFoodDeal.getPhotoLink());
//        }

        //like and dislike button
        holder.mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SingletonDataHolder.getInstance().getContext(), "onclick", Toast.LENGTH_SHORT).show();
                new FoodDealAsycn(FoodDealFragment.mAdapter).execute(FoodDealAsycn.RATING, ""+holder.mFoodDeal.getId(), "1");
            }
        });
        holder.mDislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FoodDealAsycn(FoodDealFragment.mAdapter).execute(FoodDealAsycn.RATING, ""+holder.mFoodDeal.getId(), "-1");
            }
        });

        //hyperlink image
        if (holder.mFoodDeal.getCreatedBy().compareTo(FoodDeal.FACEBOOK) == 0) {
            if (holder.mFoodDeal.getEventLink().length() > 0) {
                holder.mTextView.setText( addIconAtBeginning(holder.mTextView.getText(), R.drawable.hyperlink) );
            }
        }else {
            holder.mTextView.setText( addIconAtBeginning(holder.mTextView.getText(), R.drawable.internallink) );
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != FoodDeal_Fragment_listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    FoodDeal_Fragment_listener.onListFragmentInteraction(holder.mFoodDeal);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return SingletonDataHolder.getInstance().getFoodDealList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
        //return SingletonDataHolder.getInstance().getFoodDealList().get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public boolean ifLoading(){
        return mIsLoading;
    }
    public void setIsLoading(boolean bool){
        mIsLoading = bool;
    }
    public static boolean ifReachEnd(){
        return mIsReachEnd;
    }
    public static void setIsReachEnd(boolean bool){mIsReachEnd = bool;}

    //Item view of the recycle List
    //need to match the @layout list_item.xml
    public class FoodDealViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTextView;
        public final ImageView mImageView;
        public FoodDeal mFoodDeal;
        public final ImageButton mLikeButton;
        public final ImageButton mDislikeButton;
        public final TextView mRating;

        public FoodDealViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.FoodDeal_Name);
            mImageView = view.findViewById(R.id.FoodDeal_Image);
            mLikeButton = view.findViewById(R.id.FoodDeal_Like);
            mDislikeButton = view.findViewById(R.id.FoodDeal_Dislike);
            mRating = view.findViewById(R.id.FoodDeal_Rating);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }
    }

    private SpannableStringBuilder addIconAtBeginning(CharSequence s, int resId){
        Drawable icon;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon = SingletonDataHolder.getInstance().getContext().getDrawable(resId);
        } else {
            icon = SingletonDataHolder.getInstance().getContext().getResources().getDrawable(resId);
        }
        icon.setBounds(0, 0, 75, 75);
        ImageSpan ip = new ImageSpan(icon);
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append("  ");
        sb.append(s);
        sb.setSpan(ip, 0, 1, Spanned.SPAN_COMPOSING);
        return sb;
    }
}
