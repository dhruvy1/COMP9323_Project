package com.comp9323.food.foodplace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FoodPlaceFragment extends Fragment {

    private static int page = 1;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected static FoodPlaceRvAdapter adapter;
    private static Listener listener;
    public static final View[] expandedView = {null};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_place_rv, container, false);

        setupAdapter(savedInstanceState);

        // Set the adapter
        final RecyclerView recyclerView = view.findViewById(R.id.food_place_rv);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!FoodPlaceRvAdapter.ifReachEnd()) {
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    int visibleThreshold = 1;
//                    if (!adapter.ifLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                        page++;
//                        new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.GET_LIST, page + "");
//                    }
                }
            }
        });

        //set refresh listener
        swipeRefreshLayout = view.findViewById(R.id.food_place_swipe_refresh);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.d("Refresh", "call onRefresh");
//                DataHolder.getInstance().clearFoodPlaceList();
//                FoodPlaceRvAdapter.setIsReachEnd(false);
//                pullList(1);
//                Log.d("Refresh", "end onRefresh");
//            }
//        });
//
//        //pull item from server after launch if no item in the list
//        if (DataHolder.getInstance().getFoodPlaceList().size() == 0) {
//            pullList(1);
//        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DataHolder.getInstance().clearFoodPlaceList();
    }

    private void setupAdapter(Bundle savedInstanceState) {
        final Bundle copy = savedInstanceState;
        listener = new Listener() {
            @Override
            public void onFoodPlaceItemClicked(FoodPlace item, View view, int position) {
                //expand and collapse item
                final FoodPlace place = item;
                LinearLayout detail = view.findViewById(R.id.food_place_item_detail_container);
                if (detail.getVisibility() != View.VISIBLE) {
                    detail.setVisibility(View.VISIBLE);

                    if (expandedView[0] != null) { //collapse other foodDealView
                        expandedView[0].findViewById(R.id.food_place_item_detail_container).setVisibility(View.GONE);
                    }
                    expandedView[0] = view;

                    //set map
                    MapView mapview = view.findViewById(R.id.food_place_item_map);
                    if (mapview != null) {
                        mapview.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                googleMap.clear();
                                LatLng position = new LatLng(Double.parseDouble(place.getLatitude()), Double.parseDouble(place.getLongitude()));
                                googleMap.addMarker(
                                        new MarkerOptions()
                                                .position(position)
                                                .title(place.getName())
                                );
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0f));
                            }
                        });
                    }
                    mapview.onCreate(copy);
                    mapview.onStart();

                    mapview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri gmmIntentUri = Uri.parse("geo:" + place.getLatitude() + "," + place.getLongitude());
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    });

                    //set voting button
                    ImageButton dislike = view.findViewById(R.id.food_place_item_dislike_button);
//                    if (!dislike.hasOnClickListeners())
//                        dislike.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int id = place.getId();
//                                new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.RATING, id + "", "-1");
//                            }
//                        });
//
//                    ImageButton like = view.findViewById(R.id.food_place_item_like_button);
//                    if (!like.hasOnClickListeners()) {
//                        like.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int id = place.getId();
//                                new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.RATING, id + "", "1");
//                            }
//                        });
//                    }
                } else {
                    detail.setVisibility(View.GONE);
                    expandedView[0] = null;
                }
            }
        };
        adapter = new FoodPlaceRvAdapter(listener);
    }

//    private void pullList(int newPage) {
//        page = newPage;
//        new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.GET_LIST, newPage + "");
//        swipeRefreshLayout.setRefreshing(false);
//    }

    public interface Listener {
        // TODO: Update argument type and name
        void onFoodPlaceItemClicked(FoodPlace item, View view, int position);
    }
}
