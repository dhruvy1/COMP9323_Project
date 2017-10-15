package com.comp9323.food.fooddeal;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;

import java.util.ArrayList;
import java.util.List;

public class FoodDealRvAdapter extends RecyclerView.Adapter<FoodDealRvAdapter.ViewHolder> {
    private static final String TAG = "FoodDealRvAdapter";

    private List<FoodDeal> foodDeals;
    private Listener listener;

    public FoodDealRvAdapter() {
        foodDeals = new ArrayList<>();
    }

    public void setFoodDeals(List<FoodDeal> foodDeals) {
        this.foodDeals = foodDeals;
    }

    public void updateFoodDeal(int itemPos, FoodDeal foodDeal) {
        foodDeals.set(itemPos, foodDeal);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_deal_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.foodDeal = foodDeals.get(position);

        holder.foodDealMessage.setText(holder.foodDeal.getMessage());
        holder.foodDealRating.setText((holder.foodDeal.getRating()));

        loadFoodDealImage(holder);

        initFoodDealLikeBtn(holder);
        initFoodDealDislikeBtn(holder);
        initFoodDealViewClick(holder);

        addEventLinkPlaceHolder(holder);
    }

    @Override
    public int getItemCount() {
        return foodDeals.size();
    }

    private void loadFoodDealImage(ViewHolder holder) {
        if (holder.foodDeal.getPhotoLink().length() > 0) {
            holder.foodDealImage.setVisibility(View.VISIBLE);
            Glide.with(holder.foodDealImage.getContext()).load(holder.foodDeal.getPhotoLink()).into(holder.foodDealImage);
        } else {
            holder.foodDealImage.setVisibility(View.GONE);
        }
    }

    private void initFoodDealLikeBtn(final ViewHolder holder) {
        holder.foodDealLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFoodDealLikeBtnClicked(holder.foodDeal.getId(), holder.foodDeal.getRating());
                holder.foodDealLikeBtn.setImageResource(R.drawable.ic_arrow_upward_blue_24dp);
                holder.foodDealDislikeBtn.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
            }
        });
    }

    private void initFoodDealDislikeBtn(final ViewHolder holder) {
        holder.foodDealDislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFoodDealDislikeBtnClicked(holder.foodDeal.getId(), holder.foodDeal.getRating());
                holder.foodDealLikeBtn.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
                holder.foodDealDislikeBtn.setImageResource(R.drawable.ic_arrow_downward_blue_24dp);
            }
        });
    }

    private void initFoodDealViewClick(final ViewHolder holder) {
        holder.foodDealView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFoodDealViewClicked(holder.foodDeal);
            }
        });
    }

    private void addEventLinkPlaceHolder(ViewHolder holder) {
        // Add hyper link icon to front of event links
        if (holder.foodDeal.getCreatedBy().compareTo(FoodDeal.FACEBOOK) == 0) {
            // event link to Facebook
            if (holder.foodDeal.getEventLink().length() > 0) {
                holder.foodDealMessage.setText(addIconAtBeginning(holder.foodDealMessage.getText(),
                        R.drawable.hyper_link, holder.foodDealView.getContext()));
            }
        } else {
            // event link not to Facebook
            holder.foodDealMessage.setText(addIconAtBeginning(holder.foodDealMessage.getText(),
                    R.drawable.internal_link, holder.foodDealView.getContext()));
        }
    }

    private SpannableStringBuilder addIconAtBeginning(CharSequence s, int resId, Context context) {
        Drawable icon;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            icon = context.getDrawable(resId);
        } else {
            icon = context.getResources().getDrawable(resId);
        }
        icon.setBounds(0, 0, 75, 75);
        SpannableStringBuilder sb = new SpannableStringBuilder().append("  ").append(s);
        sb.setSpan(new ImageSpan(icon), 0, 1, Spanned.SPAN_COMPOSING);
        return sb;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoodDeal foodDeal;

        public final View foodDealView;
        public final TextView foodDealMessage;
        public final ImageView foodDealImage;
        public final ImageButton foodDealLikeBtn;
        public final ImageButton foodDealDislikeBtn;
        public final TextView foodDealRating;

        public ViewHolder(View view) {
            super(view);
            this.foodDealView = view;
            foodDealMessage = view.findViewById(R.id.food_deal_message);
            foodDealImage = view.findViewById(R.id.food_deal_image);
            foodDealLikeBtn = view.findViewById(R.id.food_deal_like_btn);
            foodDealDislikeBtn = view.findViewById(R.id.food_deal_dislike_btn);
            foodDealRating = view.findViewById(R.id.food_deal_rating);
        }
    }

    public interface Listener {
        void onFoodDealLikeBtnClicked(Integer id, String rating);

        void onFoodDealDislikeBtnClicked(Integer id, String rating);

        void onFoodDealViewClicked(FoodDeal foodDeal);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
