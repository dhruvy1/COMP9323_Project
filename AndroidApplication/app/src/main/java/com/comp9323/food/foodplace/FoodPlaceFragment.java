package com.comp9323.food.foodplace;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodPlaceApi;
import com.comp9323.restclient.api.FoodPlaceService;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodPlaceFragment extends Fragment implements FoodPlaceRvAdapter.Listener {
    private static final String TAG = "FoodPlaceFragment";

    private FoodPlaceRvAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_place_rv, container, false);

        setHasOptionsMenu(true);

        initRvAdapter(savedInstanceState);
        initRecyclerView(view);
        initSwipeRefreshLayout(view, savedInstanceState);

        getFoodPlaces();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_food_place, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.add_food_place:
                NewFoodPlaceFormFragment foodDealFormFragment = new NewFoodPlaceFormFragment();
                foodDealFormFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                foodDealFormFragment.show(getFragmentManager(), "Food Place Dialog Fragment");
                return true;
            case R.id.food_place_sort_rating:
                adapter.sort(FoodPlaceRvAdapter.SORT_BY_RATING);
                adapter.notifyDataSetChanged();
                changeIcon(item);
                return true;
            case R.id.food_place_sort_name:
                adapter.sort(FoodPlaceRvAdapter.SORT_BY_NANE);
                adapter.notifyDataSetChanged();
                //item.setIcon()
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        FoodPlaceService.getFoodPlaces(new Callback<List<FoodPlace>>() {
            @Override
            public void onResponse(Call<List<FoodPlace>> call, Response<List<FoodPlace>> response) {
                if (response.isSuccessful()) {
                    updateAdapter(response.body()); // update adapter
                    if (swipeRefreshLayout.isRefreshing()) {
                        // turn off refresh animation on swipe ups, if its on
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FoodPlace>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void updateAdapter(List<FoodPlace> foodPlaces) {
        adapter.setFoodPlaces(foodPlaces);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFoodPlaceLikeBtnClicked(final Integer id, String rating) {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setRating(Integer.toString(Integer.parseInt(rating) + 1));
        FoodPlaceService.patchFoodPlace(id, foodPlace, new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                if (response.isSuccessful()) {
                    response.body().setId(id);
                    int itemPos = id - 1;
                    adapter.updateFoodPlace(itemPos, response.body());
                    adapter.notifyItemChanged(itemPos);
                }
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onFoodPlaceDislikeBtnClicked(final Integer id, String rating) {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setRating(Integer.toString(Integer.parseInt(rating) - 1));
        FoodPlaceService.patchFoodPlace(id, foodPlace, new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                if (response.isSuccessful()) {
                    response.body().setId(id);
                    int itemPos = id - 1;
                    adapter.updateFoodPlace(itemPos, response.body());
                    adapter.notifyItemChanged(itemPos);
                }
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    public void changeIcon(MenuItem item){
        int[] status = adapter.getSortingStatus();
        switch(item.getItemId()){
            case R.id.food_place_sort_name:
                if (status[1] == FoodPlaceRvAdapter.ASCENDING){
                    //TODO set descending image
                    //item.setIcon();
                }else{
                    //TODO set descending image
                    //item.setIcon();
                }
                break;
            case R.id.food_place_sort_rating:
                if (status[1] == FoodPlaceRvAdapter.ASCENDING){
                    //TODO set descending image
                    //item.setIcon();
                }else{
                    //TODO set descending image
                    //item.setIcon();
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
