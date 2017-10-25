package com.comp9323.food.foodplace;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodPlace;
import com.comp9323.main.R;
import com.comp9323.restclient.service.FoodPlaceService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * view that display food fragment on screen
 */
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
        MenuItem item = menu.findItem(R.id.food_place_sort_spinner);
        //Spinner s = (Spinner) item.getActionView().findViewById(R.id.food_place_sort_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        View t = menu.findItem(R.id.karma_point).setActionView(R.layout.menu_karma_point_view).getActionView();
        TextView textView = t.findViewById(R.id.karma_point_view);
        textView.setText(DataHolder.getInstance().getUser().getKarmaPoint());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.food_place_spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setOnSpinnerSelectListener(spinner);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_food_place:
                NewFoodPlaceFormFragment foodPlaceFormFragment = new NewFoodPlaceFormFragment();
//                foodDealFormFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                foodDealFormFragment.show(getFragmentManager(), "Food Place Dialog Fragment");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, foodPlaceFormFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * create adapter for the recycle view
     * @param savedInstance saved Instance
     */
    private void initRvAdapter(Bundle savedInstance) {
        adapter = new FoodPlaceRvAdapter(getContext());
        adapter.setListener(this);
        adapter.setBundle(savedInstance);
    }

    /**
     * create recycle list view
     * @param view view that will be created
     */
    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.food_place_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * setup swipe refresh layout
     * @param view view that will be change
     * @param savedInstanceState saved instance
     */
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

    /**
     * function that get food place from database
     */
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

    /**
     * function that reset the food place list
     * @param foodPlaces food place list that will be reset
     */
    private void updateAdapter(List<FoodPlace> foodPlaces) {
        adapter.setFoodPlaces(foodPlaces);
        adapter.sort(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFoodPlaceLikeBtnClicked(final FoodPlaceRvAdapter.ViewHolder holder, final int position) {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setRating(Integer.toString(Integer.parseInt(holder.foodPlace.getRating()) + 1));

        FoodPlaceService.patchFoodPlace(holder.foodPlace.getId(), foodPlace, new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                if (response.isSuccessful()) {
                    FoodPlace newInstance = response.body();
                    newInstance.setId(holder.foodPlace.getId());
                    holder.foodPlace = newInstance;
                    adapter.updateLikeDraw(holder, newInstance.getRating());
                    adapter.updateFoodPlace(position, newInstance);
                }
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onFoodPlaceDislikeBtnClicked(final FoodPlaceRvAdapter.ViewHolder holder, final int position) {
        FoodPlace foodPlace = new FoodPlace();
        foodPlace.setRating(Integer.toString(Integer.parseInt(holder.foodPlace.getRating()) - 1));
        FoodPlaceService.patchFoodPlace(holder.foodPlace.getId(), foodPlace, new Callback<FoodPlace>() {
            @Override
            public void onResponse(Call<FoodPlace> call, Response<FoodPlace> response) {
                if (response.isSuccessful()) {
                    FoodPlace newInstance = response.body();
                    newInstance.setId(holder.foodPlace.getId());
                    holder.foodPlace = newInstance;
                    adapter.updateLikeDraw(holder, newInstance.getRating());
                    adapter.updateFoodPlace(position, newInstance);
                }
            }

            @Override
            public void onFailure(Call<FoodPlace> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    /**
     * function that create sorting spinner on click action
     * @param spinner spinner that will be change
     */
    public void setOnSpinnerSelectListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                adapter.sort(pos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}
