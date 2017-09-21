package com.comp9323.FoodDeal;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp9323.FoodDeal.FoodDealFragment.OnListFragmentInteractionListener;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FoodDeal} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFoodDealRecyclerViewAdapter extends RecyclerView.Adapter<MyFoodDealRecyclerViewAdapter.FoodDealViewHolder> {

    private final List<FoodDeal> FoodDeals_;
    private final OnListFragmentInteractionListener FoodDeal_Fragment_listener;

    public MyFoodDealRecyclerViewAdapter(List<FoodDeal> foodDeals, OnListFragmentInteractionListener listener) {
        FoodDeals_ = foodDeals;
        FoodDeal_Fragment_listener = listener;
        Log.d("Adapter", "size: "+ FoodDeals_.size());
    }

    @Override
    public FoodDealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fooddeal_item, parent, false);
        return new FoodDealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FoodDealViewHolder holder, int position) {

        //TODO set value that display in the list
        holder.FoodDeal_ = FoodDeals_.get(position);
        holder.View_title_.setText(FoodDeals_.get(position).getMessage());

        holder.View_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != FoodDeal_Fragment_listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    FoodDeal_Fragment_listener.onListFragmentInteraction(holder.FoodDeal_);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return FoodDeals_.size();
    }

    //Item view of the recycle List
    //need to match the @layout list_item.xml
    public class FoodDealViewHolder extends RecyclerView.ViewHolder {
        public final View View_;
        public final TextView View_title_;
        public FoodDeal FoodDeal_;

        public FoodDealViewHolder(View view) {
            super(view);
            View_ = view;
            View_title_ = (TextView) view.findViewById(R.id.FoodDeal_Name);

            //dynamic set image
            if (FoodDeal_ != null && FoodDeal_.getPhotoLink().length() >0) {
                Drawable img = null;
                try {
                    img = Drawable.createFromStream((InputStream)new URL(FoodDeal_.getPhotoLink()).getContent(), "food_deal_item_image");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (img != null) {
                    View_title_.setCompoundDrawables(null, img, null, null);
                }
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + View_title_.getText() + "'";
        }
    }
}
