package com.comp9323.food.fooddeal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;
import com.comp9323.restclient.service.FoodDealService;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDealFragment extends Fragment implements FoodDealRvAdapter.Listener, FoodDealNewFormFragment.Listener {
    private static final String TAG = "FoodDealFragment";

    private FoodDealRvAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_deal_rv, container, false);

        setHasOptionsMenu(true);

        initRvAdapter();
        initRecyclerView(view);
        initSwipeRefreshLayout(view);

        getFoodDeals();

        return view;
    }

    /**
     * Initialise the Food Deal Recycler View adapter
     */
    private void initRvAdapter() {
        adapter = new FoodDealRvAdapter();
        adapter.setListener(this);
    }

    /**
     * Initialise the Food Deal Recycler View
     *
     * @param view the parent layout
     */
    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.food_deal_rv); // find the rv in the parent layout
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext())); // set its orientation
        recyclerView.setAdapter(adapter); // set where is gets the items to display on the screen
    }

    /**
     * Initialise the Swipe to Refresh the list feature
     *
     * @param view the parent layout
     */
    private void initSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.food_deal_swipe_refresh); // find the view in the parent layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // reset the whole adapter
                initRvAdapter(); // remake the recycler view adapter
                recyclerView.setAdapter(adapter); // refresh the items in the recycler view
                getFoodDeals(); // get the new food deals
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_food_deal, menu);

        // initialise the karma points
        View t = menu.findItem(R.id.karma_point).setActionView(R.layout.menu_karma_point_view).getActionView();
        TextView textView = t.findViewById(R.id.karma_point_view);
        textView.setText(DataHolder.getInstance().getUser().getKarmaPoint());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_food_deal:
                // show the posting new food deal fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                FoodDealNewFormFragment foodDealFormFragment = new FoodDealNewFormFragment();
                foodDealFormFragment.setListener(this);

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, foodDealFormFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Retrieves the latest food deals from the server
     */
    private void getFoodDeals() {
        // make a REST api call to get food deals
        FoodDealService.getFoodDeals(new Callback<List<FoodDeal>>() {
            @Override
            public void onResponse(Call<List<FoodDeal>> call, Response<List<FoodDeal>> response) {
                if (response.isSuccessful()) { // check if we got the food deals successfully
                    // update adapter
                    List<FoodDeal> foodDeals = response.body();
                    Collections.reverse(foodDeals); // reverse the food deals
                    adapter.setFoodDeals(foodDeals);
                    adapter.notifyDataSetChanged(); // refresh the items in the recycler view
                    // turn off refresh animation on swipe ups, if its on
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false); // hide refreshing animation
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
        foodDeal.setRating(Integer.toString(Integer.parseInt(rating) + 1)); // increment the rating
        // patch the new food deal rating
        FoodDealService.patchFoodDeal(id, foodDeal, new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                // we don't need to do anything if its successful
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
        foodDeal.setRating(Integer.toString(Integer.parseInt(rating) - 1)); // decrement the rating
        // patch the new food deal rating
        FoodDealService.patchFoodDeal(id, foodDeal, new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                // we don't need to do anything if its successful
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onFoodDealViewClicked(FoodDeal foodDeal) {
        String link = foodDeal.getEventLink(); // get the food deal link
        if (link.length() > 0) {
            if (!link.startsWith("http")) {
                link = "http://" + link; // append http to if it is missing it
            }
            // start a new intent to open the chrome browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                getContext().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser not installed so allow user to choose another browser instead
                intent.setPackage(null);
                getContext().startActivity(intent);
            }
        }
    }

    @Override
    public void onSaveClicked(FoodDeal foodDeal) {
        foodDeal.setCreatedBy(DataHolder.getInstance().getUser().getUsername());

        // post the food deal
        FoodDealService.postFoodDeal(foodDeal, new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                if (response.isSuccessful()) { // if the post was successful
                    getFoodDeals(); // update the food deals list
                }
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}