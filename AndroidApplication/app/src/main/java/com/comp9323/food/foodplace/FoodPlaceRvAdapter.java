package com.comp9323.food.foodplace;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Adapter class that handle recycle view of food place
 */
public class FoodPlaceRvAdapter extends RecyclerView.Adapter<FoodPlaceRvAdapter.ViewHolder> {
    private static final String TAG = "FoodPlaceRvAdapter";
    private static final int SORT_NULL = 0;
    public static final int SORT_BY_NANE = 1;
    public static final int SORT_BY_RATING = 2;
    public static final int ASCENDING = 0;
    public static final int DESCENDING = 1;

    private Context context;
    private Listener listener;
    private Bundle savedInstance;

    private List<FoodPlace> foodPlaces;
    private List<Integer> expandedList;
    private int[] sorting;

    public FoodPlaceRvAdapter(Context context) {
        this.context = context;
        foodPlaces = new ArrayList<>();
        expandedList = new ArrayList<>();
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
        showFoodPlaceDetails(holder, position);
    }

    /**
     * function that handle onclick on recycle view which expand or collapse the food deal view holder
     * @param holder view holder that will be change
     * @param position position of view holder in the recycle list
     */
    private void showFoodPlaceDetails(ViewHolder holder, int position) {
        if (expandedList.contains(position)) {
            holder.foodPlaceItemDetailContainer.setVisibility(View.VISIBLE);
            initFoodPlaceDetails(holder, position);
        } else {
            holder.foodPlaceItemDetailContainer.setVisibility(View.GONE);
        }
    }

    /**
     * fill up view holder
     * @param holder view holder will be change
     * @param position position of the view in the recycle list
     */
    private void initFoodPlaceDetails(final ViewHolder holder, final int position) {
        // set image
        if (holder.foodPlace.getPhotoLink().length() > 0) {
            Glide.with(holder.photoView.getContext()).load(holder.foodPlace.getPhotoLink()).into(holder.photoView);
        }

        holder.addressView.setText(holder.foodPlace.getLocation());
        holder.appRatingView.setText(holder.foodPlace.getRating());

        // set like btn onclick listener
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFoodPlaceLikeBtnClicked(holder, position);
            }
        });

        // set dislike btn onclick listener
        holder.dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFoodPlaceDislikeBtnClicked(holder, position);
            }
        });

        initMap(holder);
    }

    /**
     * set Google map in the view
     * @param holder view holder that will be change
     */
    private void initMap(final ViewHolder holder) {
        MapView mapview = holder.mapView;
        mapview.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();
                LatLng position = new LatLng(Double.parseDouble(holder.foodPlace.getLatitude()),
                        Double.parseDouble(holder.foodPlace.getLongitude()));
                googleMap.addMarker(new MarkerOptions().position(position).title(holder.foodPlace.getName()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0f));
            }
        });
        mapview.onCreate(savedInstance);
        mapview.onStart();

        mapview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:" + holder.foodPlace.getLatitude() + "," + holder.foodPlace.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodPlaces.size();
    }

    /**
     * initialize the onclick listener
     * @param holder
     * @param position
     */
    private void initFoodPlaceViewClick(final ViewHolder holder, final int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandedList.contains(position)) {
                    expandedList.remove(Integer.valueOf(position));
                } else {
                    expandedList.add(position);
                }
                showFoodPlaceDetails(holder, position);
            }
        });
    }

    /**
     * function that set google Google Rating bar
     * @param holder view that will be change
     */
    private void setGoogleRatingBar(ViewHolder holder) {
        if (!holder.foodPlace.getGoogleRating().isEmpty()) {
            holder.ratingView.setRating(Float.parseFloat(holder.foodPlace.getGoogleRating()));
        } else {
            holder.ratingView.setRating(0);
        }
    }

    public void setBundle(Bundle savedInstance) {
        this.savedInstance = savedInstance;
    }

    /**
     * replace the entire food place list
     * @param foodPlaces new food place list
     */
    public void setFoodPlaces(List<FoodPlace> foodPlaces) {
        this.foodPlaces = foodPlaces;
    }

    /**
     * function that update the recycle view order
     * @param itemPos new position of the food place
     * @param foodPlace food place the will change
     */
    public void updateFoodPlace(int itemPos, FoodPlace foodPlace) {
        foodPlaces.set(itemPos, foodPlace);
    }

    /**
     * function that redraw like count,
     * should be called when like or dislike button click
     * @param holder view that will be change
     * @param rating increase or decrease of rating
     */
    public void updateLikeDraw(FoodPlaceRvAdapter.ViewHolder holder, String rating) {
        holder.appRatingView.setText(rating);
        holder.appRatingView.invalidate();
    }

    /**
     * Recycle view holder of Food Place
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public FoodPlace foodPlace;
        public final View view;
        public final TextView nameView;
        public final RatingBar ratingView;

        public LinearLayout foodPlaceItemDetailContainer;

        public final ImageView photoView;
        public final TextView addressView;
        public final TextView appRatingView;
        public final ImageButton likeBtn;
        public final ImageButton dislikeBtn;
        public final MapView mapView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            nameView = view.findViewById(R.id.food_place_item_name);
            ratingView = view.findViewById(R.id.food_place_item_rating_bar);

            foodPlaceItemDetailContainer = view.findViewById(R.id.food_place_item_detail_container);

            photoView = view.findViewById(R.id.food_place_item_img);
            addressView = view.findViewById(R.id.food_place_item_address);
            appRatingView = view.findViewById(R.id.food_place_item_in_app_rating);
            likeBtn = view.findViewById(R.id.food_place_item_like_btn);
            dislikeBtn = view.findViewById(R.id.food_place_item_dislike_btn);
            mapView = view.findViewById(R.id.food_place_item_map);
        }
    }

    public interface Listener {
        void onFoodPlaceLikeBtnClicked(ViewHolder holder, int position);
        void onFoodPlaceDislikeBtnClicked(ViewHolder holder, int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * helper function that used handle sorting
     * @param Type sorting type
     */
    public void sort(int Type) {
        expandedList.clear();
        switch (Type) {
            case 0:
                Collections.sort(foodPlaces, new Comparator<FoodPlace>() {
                    @Override
                    public int compare(FoodPlace t1, FoodPlace t2) {
                        return t1.getName().compareToIgnoreCase(t2.getName());
                    }
                });
                break;
            case 1:
                Collections.sort(foodPlaces, new Comparator<FoodPlace>() {
                    @Override
                    public int compare(FoodPlace t1, FoodPlace t2) {
                        return t2.getName().compareToIgnoreCase(t1.getName());
                    }
                });
                break;
            case 2:
                Collections.sort(foodPlaces, new Comparator<FoodPlace>() {
                    @Override
                    public int compare(FoodPlace t1, FoodPlace t2) {
                        float t1Rating = t1.getGoogleRating().isEmpty() ? 0 : Float.parseFloat(t1.getGoogleRating());
                        float t2Rating = t2.getGoogleRating().isEmpty() ? 0 : Float.parseFloat(t2.getGoogleRating());
                        return t1Rating > t2Rating ? -1 : 1;
                    }
                });
                break;
            case 3:
                Collections.sort(foodPlaces, new Comparator<FoodPlace>() {
                    @Override
                    public int compare(FoodPlace t1, FoodPlace t2) {
                        float t1Rating = t1.getGoogleRating().isEmpty() ? 0 : Float.parseFloat(t1.getGoogleRating());
                        float t2Rating = t2.getGoogleRating().isEmpty() ? 0 : Float.parseFloat(t2.getGoogleRating());
                        return t1Rating > t2Rating ? 1 : -1;
                    }
                });
                break;
            default:
                break;
        }
    }
}
