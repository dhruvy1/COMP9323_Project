package com.comp9323.Food.FoodDeal;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FoodDeal} and makes a call to the
 * specified {@link FoodDealFragment.OnListFooDealInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFoodDealRecyclerViewAdapter extends RecyclerView.Adapter<MyFoodDealRecyclerViewAdapter.FoodDealViewHolder> {

    private final List<FoodDeal> mFoodDeals;
    private static boolean mIsLoading = false;
    private final FoodDealFragment.OnListFooDealInteractionListener FoodDeal_Fragment_listener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    //private OnLoadMoreListener onLoadMoreListener;


//    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
//        this.onLoadMoreListener = mOnLoadMoreListener;
//    }


    public MyFoodDealRecyclerViewAdapter(List<FoodDeal> foodDeals, FoodDealFragment.OnListFooDealInteractionListener listener) {
        mFoodDeals = foodDeals;
        FoodDeal_Fragment_listener = listener;
        Log.d("Adapter", "size: "+ mFoodDeals.size());
    }

    @Override
    public FoodDealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fooddeal_item, parent, false);
            return new FoodDealViewHolder(view);
        }else if (viewType == VIEW_TYPE_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_fooddeal_item, parent, false);
            return new LastViewHolder(view);
        }else
            return null;
    }

    @Override
    public void onBindViewHolder(final FoodDealViewHolder holder, int position) {

        if (holder instanceof LastViewHolder){
            LastViewHolder loadingViewHolder = (LastViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }else {
            //TODO set value that display in the list
            holder.mFoodDeal = mFoodDeals.get(position);
            holder.mTextView.setText(mFoodDeals.get(position).getMessage());

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
    }
    @Override
    public int getItemCount() {
        return mFoodDeals.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mFoodDeals.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    //listener for pulling deal from db when list reach the end
    private interface OnLoadMoreListener{
        void onLoadMore();
    }


    //Item view of the recycle List
    //need to match the @layout list_item.xml
    public class FoodDealViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTextView;
        public FoodDeal mFoodDeal;

        public FoodDealViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.FoodDeal_Name);

            //dynamic set image
            if (mFoodDeal != null && mFoodDeal.getPhotoLink().length() >0) {
                Drawable img = null;
                try {
                    img = Drawable.createFromStream((InputStream)new URL(mFoodDeal.getPhotoLink()).getContent(), "food_deal_item_image");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (img != null) {
                    mTextView.setCompoundDrawables(null, img, null, null);
                }
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }
    }

    public class LastViewHolder extends FoodDealViewHolder{
        public ProgressBar progressBar;

        public LastViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.FoodList_progressBar);
        }
    }

    public boolean ifLoading(){
        return mIsLoading;
    }
    public void setIsloading(boolean bool){
        mIsLoading = bool;
    }
}
