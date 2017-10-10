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
import com.comp9323.restclient.api.FoodPlaceApiImpl;

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

    private CompositeDisposable compositeDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_place_rv, container, false);

        compositeDisposable = new CompositeDisposable();

        initRvAdapter(savedInstanceState);
        initRecyclerView(view);
        initSwipeRefreshLayout(view, savedInstanceState);

        getFoodPlaces();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }

    private void initRvAdapter(Bundle savedInstance) {
        adapter = new FoodPlaceRvAdapter(getContext());
        adapter.setListener(this);
        adapter.setBundle(savedInstance);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.food_place_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout(View view, final Bundle savedInstanceState) {
        swipeRefreshLayout = view.findViewById(R.id.food_place_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRvAdapter(savedInstanceState);
                recyclerView.setAdapter(adapter);
                getFoodPlaces();
            }
        });
    }

    private void getFoodPlaces() {
        FoodPlaceApi api = RestClient.getClient().create(FoodPlaceApi.class);
        compositeDisposable.clear();
        compositeDisposable.add(api.getFoodPlaces()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        return objectObservable.delay(SECONDS_TO_POLL_SERVER, TimeUnit.SECONDS);
                    }
                })
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

    @Override
    public void onFoodPlaceLikeBtnClicked(Integer id, String rating) {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setRating(Integer.toString(Integer.parseInt(rating) + 1));
        FoodPlaceApiImpl fpai = new FoodPlaceApiImpl();
        fpai.patchFoodPlace(id, foodPlace);
    }

    @Override
    public void onFoodPlaceDislikeBtnClicked(Integer id, String rating) {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setRating(Integer.toString(Integer.parseInt(rating) - 1));
        FoodPlaceApiImpl fpai = new FoodPlaceApiImpl();
        fpai.patchFoodPlace(id, foodPlace);
    }
}
