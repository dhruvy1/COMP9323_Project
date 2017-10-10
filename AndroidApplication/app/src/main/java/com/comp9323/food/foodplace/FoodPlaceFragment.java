package com.comp9323.food.foodplace;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodPlaceApi;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FoodPlaceFragment extends Fragment implements FoodPlaceRvAdapter.Listener {
    private static final String TAG = "FoodPlaceFragment";

    private static final int SECONDS_TO_POLL_SERVER = 1;

    private FoodPlaceRvAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static final View[] expandedView = {null};

    private CompositeDisposable compositeDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_place_rv, container, false);

        compositeDisposable = new CompositeDisposable();

        initRvAdapter();
        initRecyclerView(view);
        initSwipeRefreshLayout(view);

        getFoodPlaces();
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                if (!FoodPlaceRvAdapter.ifReachEnd()) {
//                    int totalItemCount = linearLayoutManager.getItemCount();
//                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                    int visibleThreshold = 1;
//                    if (!adapter.ifLoading() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                        page++;
//                        new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.GET_LIST, page + "");
//                    }
//                }
//            }
//        });

//        //set refresh listener
//        swipeRefreshLayout = view.findViewById(R.id.food_place_swipe_refresh);
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

//        //pull item from server after launch if no item in the list
//        if (DataHolder.getInstance().getFoodPlaceList().size() == 0) {
//            pullList(1);
//        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    private void initRvAdapter() {
        adapter = new FoodPlaceRvAdapter(getContext());
        adapter.setListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.food_place_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.food_place_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRvAdapter();
                recyclerView.setAdapter(adapter);
                getFoodPlaces();
            }
        });
    }

    private void getFoodPlaces() {
        FoodPlaceApi api = RestClient.getClient().create(FoodPlaceApi.class);
        compositeDisposable.clear();
        compositeDisposable.add(api.getFoodPlaces()
//                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
//                        return objectObservable.delay(SECONDS_TO_POLL_SERVER, TimeUnit.SECONDS);
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<FoodPlace>>() {
                    @Override
                    public void accept(List<FoodPlace> foodPlaces) throws Exception {
                        updateAdapter(foodPlaces);
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }));
    }

    private void updateAdapter(List<FoodPlace> foodPlaces) {
        adapter.setFoodPlaces(foodPlaces);
        adapter.notifyDataSetChanged();
    }


//    private void setupAdapter(Bundle savedInstanceState) {
//        final Bundle copy = savedInstanceState;
//        listener = new Listener() {
//            @Override
//            public void onFoodPlaceItemClicked(FoodPlace item, View view, int position) {
//                //expand and collapse item
//                final FoodPlace place = item;
//                LinearLayout detail = view.findViewById(R.id.food_place_item_detail_container);
//                if (detail.getVisibility() != View.VISIBLE) {
//                    detail.setVisibility(View.VISIBLE);
//
//                    if (expandedView[0] != null) { //collapse other foodDealView
//                        expandedView[0].findViewById(R.id.food_place_item_detail_container).setVisibility(View.GONE);
//                    }
//                    expandedView[0] = view;
//
//                    //set map
//                    MapView mapview = view.findViewById(R.id.food_place_item_map);
//                    if (mapview != null) {
//                        mapview.getMapAsync(new OnMapReadyCallback() {
//                            @Override
//                            public void onMapReady(GoogleMap googleMap) {
//                                googleMap.clear();
//                                LatLng position = new LatLng(Double.parseDouble(place.getLatitude()), Double.parseDouble(place.getLongitude()));
//                                googleMap.addMarker(
//                                        new MarkerOptions()
//                                                .position(position)
//                                                .title(place.getName())
//                                );
//                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 16.0f));
//                            }
//                        });
//                    }
//                    mapview.onCreate(copy);
//                    mapview.onStart();
//
//                    mapview.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Uri gmmIntentUri = Uri.parse("geo:" + place.getLatitude() + "," + place.getLongitude());
//                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                            mapIntent.setPackage("com.google.android.apps.maps");
//                            startActivity(mapIntent);
//                        }
//                    });
//
//                    //set voting button
//                    ImageButton dislike = view.findViewById(R.id.food_place_item_dislike_btn);
//                    if (!dislike.hasOnClickListeners())
//                        dislike.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int id = place.getId();
//                                new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.RATING, id + "", "-1");
//                            }
//                        });
//
//                    ImageButton like = view.findViewById(R.id.food_place_item_like_btn);
//                    if (!like.hasOnClickListeners()) {
//                        like.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int id = place.getId();
//                                new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.RATING, id + "", "1");
//                            }
//                        });
//                    }
//                } else {
//                    detail.setVisibility(View.GONE);
//                    expandedView[0] = null;
//                }
//            }
//        };
//        adapter = new FoodPlaceRvAdapter(listener);
//    }

//    private void pullList(int newPage) {
//        page = newPage;
//        new FoodPlaceAsyncTask(adapter).execute(FoodPlaceAsyncTask.GET_LIST, newPage + "");
//        swipeRefreshLayout.setRefreshing(false);
//    }

//    public interface Listener {
//        // TODO: Update argument type and name
//        void onFoodPlaceItemClicked(FoodPlace item, View view, int position);
//    }
}
