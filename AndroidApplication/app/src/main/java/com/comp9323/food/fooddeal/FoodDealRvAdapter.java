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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_food_deal_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.foodDeal = foodDeals.get(position);

        holder.foodDealMessage.setText(holder.foodDeal.getMessage());
        holder.foodDealRating.setText(holder.foodDeal.getRating());
        holder.fdCreatedBy.setText("By " + holder.foodDeal.getCreatedBy() + "");

        loadFoodDealImage(holder);

        initTitle(holder);
        initLocation(holder);
        initFoodDealLikeBtn(holder);
        initFoodDealDislikeBtn(holder);
        initFoodDealViewClick(holder);

        addEventLinkPlaceHolder(holder);
    }

    @Override
    public int getItemCount() {
        return foodDeals.size();
    }

    /**
     * Load the food deal image using the Glide Module
     *
     * @param holder
     */
    private void loadFoodDealImage(ViewHolder holder) {
        if (holder.foodDeal.getPhotoLink().length() > 0) {
            // show the image placeholder
            holder.foodDealImage.setVisibility(View.VISIBLE);
            // load the image into the image placeholder
            Glide.with(holder.foodDealImage.getContext()).load(holder.foodDeal.getPhotoLink()).into(holder.foodDealImage);
        } else {
            // hide the image placeholder, because there is no image to display
            holder.foodDealImage.setVisibility(View.GONE);
        }
    }

    /**
     * Show the title
     *
     * @param holder
     */
    private void initTitle(ViewHolder holder) {
        if (!holder.foodDeal.getTitle().isEmpty()) {
            // there is a title, show the title
            holder.fdTitle.setVisibility(View.VISIBLE);
            holder.fdTitle.setText(holder.foodDeal.getTitle());
        } else {
            // there is no title, hide the title placeholder
            holder.fdTitle.setVisibility(View.GONE);
        }
    }

    /**
     * Show the location
     *
     * @param holder
     */
    private void initLocation(ViewHolder holder) {
        if (!holder.foodDeal.getTitle().isEmpty()) {
            // there is a location, show the location
            holder.fdLocation.setVisibility(View.VISIBLE);
            holder.fdLocation.setText("Location: " + holder.foodDeal.getLocation() + "");
        } else {
            // there is no location, hide the location placeholder
            holder.fdLocation.setVisibility(View.GONE);
        }
    }

    /**
     * Allow action to be performed when the like button is clicked
     *
     * @param holder
     */
    private void initFoodDealLikeBtn(final ViewHolder holder) {
        holder.foodDealLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // let the listener handle the like button api calls
                listener.onFoodDealLikeBtnClicked(holder.foodDeal.getId(), holder.foodDeal.getRating());
                // change the colour of the like button to blue
                holder.foodDealLikeBtn.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                // change the color of the dislike button back to black in case its blue
                holder.foodDealDislikeBtn.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                // update the text of the rating
                int rating = Integer.parseInt(holder.foodDealRating.getText().toString());
                holder.foodDealRating.setText(String.valueOf(rating + 1));
            }
        });
    }

    /**
     * Allow action to be performed when the dislike button is clicked
     *
     * @param holder
     */
    private void initFoodDealDislikeBtn(final ViewHolder holder) {
        holder.foodDealDislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // let the listener handle the dislike button api calls
                listener.onFoodDealDislikeBtnClicked(holder.foodDeal.getId(), holder.foodDeal.getRating());
                // change the colour of the like button to black in case its blue
                holder.foodDealLikeBtn.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                // change the colour of the dislike button to blue
                holder.foodDealDislikeBtn.setImageResource(R.drawable.ic_thumb_down_blue_24dp);
                // update the text of the rating
                int rating = Integer.parseInt(holder.foodDealRating.getText().toString());
                holder.foodDealRating.setText(String.valueOf(rating - 1));
            }
        });
    }

    /**
     * Allow clicking on a food deal
     *
     * @param holder the food deal item that was clicked
     */
    private void initFoodDealViewClick(final ViewHolder holder) {
        holder.foodDealView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // let the listener handle the clicked food deal
                listener.onFoodDealViewClicked(holder.foodDeal);
            }
        });
    }

    /**
     * Show icons to food deals that can be clicked to show another link
     *
     * @param holder
     */
    private void addEventLinkPlaceHolder(ViewHolder holder) {
        // Add hyper link icon to front of event links
        if (holder.foodDeal.getEventLink().length() > 0) {
            // it has an event link
            if (holder.foodDeal.getCreatedBy().compareTo(FoodDeal.FACEBOOK) == 0) {
                // event link to Facebook, add a globe icon
                holder.foodDealMessage.setText(addIconAtBeginning(holder.foodDealMessage.getText(),
                        R.drawable.hyper_link, holder.foodDealView.getContext()));
            } else {
                // event link not to Facebook, add a share icon
                holder.foodDealMessage.setText(addIconAtBeginning(holder.foodDealMessage.getText(),
                        R.drawable.internal_link, holder.foodDealView.getContext()));
            }
        }
    }

    /**
     * Adds icon to the front of the view
     */
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
        public final TextView fdTitle;
        public final TextView fdLocation;
        public final TextView fdCreatedBy;

        public ViewHolder(View view) {
            super(view);
            this.foodDealView = view;
            foodDealMessage = view.findViewById(R.id.food_deal_message);
            foodDealImage = view.findViewById(R.id.food_deal_image);
            foodDealLikeBtn = view.findViewById(R.id.food_deal_like_btn);
            foodDealDislikeBtn = view.findViewById(R.id.food_deal_dislike_btn);
            foodDealRating = view.findViewById(R.id.food_deal_rating);
            fdTitle = view.findViewById(R.id.fd_title);
            fdLocation = view.findViewById(R.id.fd_location);
            fdCreatedBy = view.findViewById(R.id.fd_created_by);
        }
    }

    public interface Listener {
        /**
         * Handle the event of the like button being clicked
         * Will send a patch call to update the rating of the food deal
         *
         * @param id     the id of the food deal
         * @param rating the current rating
         */
        void onFoodDealLikeBtnClicked(Integer id, String rating);

        /**
         * Handle the event of the dislike button being clicked
         * Will send a patch call to update the rating of the food deal
         *
         * @param id     the id of the food deal
         * @param rating the current rating
         */
        void onFoodDealDislikeBtnClicked(Integer id, String rating);

        /**
         * Handle the event of the view being clicked
         * Will show the event in the browser, if it has an event link, else nothing happens
         *
         * @param foodDeal the food deal that was clicked on
         */
        void onFoodDealViewClicked(FoodDeal foodDeal);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
