package com.comp9323.food.fooddeal;

import android.content.ActivityNotFoundException;
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
import android.widget.Toast;

import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodDealApi;
import com.comp9323.restclient.api.FoodDealService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDealFragment extends Fragment implements FoodDealRvAdapter.Listener {
    private static final String TAG = "FoodDealFragment";

    private FoodDealRvAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_deal_rv, container, false);

        initRvAdapter();
        initRecyclerView(view);
        initSwipeRefreshLayout(view);

        getFoodDeals();

        return view;
    }

    private void initRvAdapter() {
        adapter = new FoodDealRvAdapter();
        adapter.setListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.food_deal_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.food_deal_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // reset the whole adapter
                initRvAdapter();
                recyclerView.setAdapter(adapter);
                getFoodDeals();
            }
        });
    }

    private void getFoodDeals() {
        FoodDealService.getFoodDeals(new Callback<List<FoodDeal>>() {
            @Override
            public void onResponse(Call<List<FoodDeal>> call, Response<List<FoodDeal>> response) {
                if (response.isSuccessful()) {
                    // update adapter
                    adapter.setFoodDeals(response.body());
                    adapter.notifyDataSetChanged();
                    // turn off refresh animation on swipe ups, if its on
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FoodDeal>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onFoodDealLikeBtnClicked(final Integer id, String rating) {
        FoodDeal foodDeal = new FoodDeal();
        foodDeal.setRating(Integer.toString(Integer.parseInt(rating) + 1));
        FoodDealService.patchFoodDeal(id, foodDeal, new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                if (response.isSuccessful()) {
                    response.body().setId(id);
                    int itemPos = id - 1;
                    adapter.updateFoodDeal(itemPos, response.body());
                    adapter.notifyItemChanged(itemPos);
                }
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onFoodDealDislikeBtnClicked(final Integer id, String rating) {
        FoodDeal foodDeal = new FoodDeal();
        foodDeal.setRating(Integer.toString(Integer.parseInt(rating) - 1));
        FoodDealService.patchFoodDeal(id, foodDeal, new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                if (response.isSuccessful()) {
                    response.body().setId(id);
                    int itemPos = id - 1;
                    adapter.updateFoodDeal(itemPos, response.body());
                    adapter.notifyItemChanged(itemPos);
                }
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onFoodDealViewClicked(FoodDeal foodDeal) {
        String link = foodDeal.getEventLink();
        if (link.length() > 0) {
            if (!link.startsWith("http")) {
                link = "http://" + link;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setPackage("com.android.chrome");
            try {
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                getContext().startActivity(intent);
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Food Deal does not have an attached link", Toast.LENGTH_LONG).show();
        }
    }
}