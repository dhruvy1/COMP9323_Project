package com.comp9323.food.fooddeal;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comp9323.asynctask.FoodDealAsyncTask;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;
import com.comp9323.food.fooddeal.FoodDealFragment.Listener;

public class FoodDealRvAdapter extends RecyclerView.Adapter<FoodDealRvAdapter.ViewHolder> {

    private static boolean isReachEnd = false;
    private static boolean isLoading = false;
    private final Listener foodDealFragmentListener;

    public FoodDealRvAdapter(Listener listener) {
        foodDealFragmentListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_deal_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // set value that display in the list
        holder.mFoodDeal = DataHolder.getInstance().getFoodDealList().get(position);
        holder.mTextView.setText(holder.mFoodDeal.getMessage());
        holder.mRating.setText((holder.mFoodDeal.getRating()));

        //Set pulled image
        //TODO function is working, but it will take up lots of processing time and messed other Asycn 
//        if (holder.mFoodDeal.getPhotoLink().length() > 0 ){
//            //slow if first load
//            new downloadPhoto(holder.mImageView, FoodDealFragment.adapter).execute(holder.mFoodDeal.getPhotoLink());
//        }

        //like and dislike button
        holder.mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DataHolder.getInstance().getContext(), "onclick", Toast.LENGTH_SHORT).show();
                new FoodDealAsyncTask(FoodDealFragment.adapter).execute(FoodDealAsyncTask.RATING, "" + holder.mFoodDeal.getId(), "1");
            }
        });
        holder.mDislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FoodDealAsyncTask(FoodDealFragment.adapter).execute(FoodDealAsyncTask.RATING, "" + holder.mFoodDeal.getId(), "-1");
            }
        });

        //hyper_link image
        if (holder.mFoodDeal.getCreatedBy().compareTo(FoodDeal.FACEBOOK) == 0) {
            if (holder.mFoodDeal.getEventLink().length() > 0) {
                holder.mTextView.setText(addIconAtBeginning(holder.mTextView.getText(), R.drawable.hyper_link));
            }
        } else {
            holder.mTextView.setText(addIconAtBeginning(holder.mTextView.getText(), R.drawable.internal_link));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != foodDealFragmentListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    foodDealFragmentListener.onFoodDealItemClicked(holder.mFoodDeal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataHolder.getInstance().getFoodDealList().size();
    }

    public static boolean ifReachEnd() {
        return isReachEnd;
    }

    public static void setIsReachEnd(boolean bool) {
        isReachEnd = bool;
    }

    private SpannableStringBuilder addIconAtBeginning(CharSequence s, int resId) {
        Drawable icon;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon = DataHolder.getInstance().getContext().getDrawable(resId);
        } else {
            icon = DataHolder.getInstance().getContext().getResources().getDrawable(resId);
        }
        icon.setBounds(0, 0, 75, 75);
        SpannableStringBuilder sb = new SpannableStringBuilder().append("  ").append(s);
        sb.setSpan(new ImageSpan(icon), 0, 1, Spanned.SPAN_COMPOSING);
        return sb;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean ifLoading() {
        return isLoading;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTextView;
        public final ImageView mImageView;
        public FoodDeal mFoodDeal;
        public final ImageButton mLikeButton;
        public final ImageButton mDislikeButton;
        public final TextView mRating;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = view.findViewById(R.id.food_deal_name);
            mImageView = view.findViewById(R.id.food_deal_image);
            mLikeButton = view.findViewById(R.id.food_deal_like_btn);
            mDislikeButton = view.findViewById(R.id.food_deal_dislike_btn);
            mRating = view.findViewById(R.id.food_deal_rating);
        }
    }
}
