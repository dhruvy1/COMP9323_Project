package com.comp9323.food.fooddeal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;
import com.comp9323.restclient.api.FoodDealService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDealFragment extends Fragment implements FoodDealRvAdapter.Listener, NewFoodDealFormFragment.Listener {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_food_deal, menu);

//        MenuItem item = menu.findItem(R.id.spinner);
//        Spinner spinner = (Spinner) item.getActionView();
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.food_deal_spinner_list_item_array, R.layout.food_deal_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_food_deal:
                NewFoodDealFormFragment foodDealFormFragment = new NewFoodDealFormFragment();
                foodDealFormFragment.setListener(this);
                foodDealFormFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                foodDealFormFragment.show(getFragmentManager(), "Food Deal Dialog Fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getFoodDeals() {
        FoodDealService.getFoodDeals(new Callback<List<FoodDeal>>() {
            @Override
            public void onResponse(Call<List<FoodDeal>> call, Response<List<FoodDeal>> response) {
                if (response.isSuccessful()) {
                    // update adapter
                    List<FoodDeal> foodDeals = new ArrayList<>();
                    for (FoodDeal foodDeal : response.body()) {

                        Date currentDate = new Date();
                        Calendar currentDateCalendar = Calendar.getInstance();
                        currentDateCalendar.setTime(currentDate);

                        if (foodDeal.getCreatedBy().equals("Facebook")) {
                            Date updatedDate = new Date();

                            Calendar updateDateCalendar = Calendar.getInstance();
                            updateDateCalendar.setTime(updatedDate);
                            updateDateCalendar.add(Calendar.DATE, 1);

                            Date dayAfter = updateDateCalendar.getTime();
//                            Calendar dayAfterCalender = Calendar.getInstance();
//                            dayAfterCalender.setTime(dayAfter);

                            if (currentDate.compareTo(dayAfter) >= 0) {
//                            if (!(currentDateCalendar.get(Calendar.YEAR) >= dayAfterCalender.get(Calendar.YEAR) &&
//                                    currentDateCalendar.get(Calendar.MONTH) >= dayAfterCalender.get(Calendar.MONTH) &&
//                                    currentDateCalendar.get(Calendar.DAY_OF_MONTH) >= dayAfterCalender.get(Calendar.DAY_OF_MONTH))) {
                                foodDeals.add(foodDeal);
                            }
                        } else {
                            // non-facebook
//                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
//                            Date endDateTime = null;
//                            try {
//                                if (foodDeal.getEndDate() != null && foodDeal.getEndTime() != null) {
//                                    endDateTime = dateFormat.parse(foodDeal.getEndDate() + " " + foodDeal.getEndTime());
//                                    if (currentDate.compareTo(endDateTime) >= 0) {
                                        foodDeals.add(foodDeal);
//                                    }
//                                }
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                    Collections.reverse(foodDeals);
                    adapter.setFoodDeals(foodDeals);
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
//                if (response.isSuccessful()) {
//                    response.body().setId(id);
//                    int itemPos = id - 1;
//                    adapter.updateFoodDeal(itemPos, response.body());
//                    adapter.notifyItemChanged(itemPos);
//                }
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
//                if (response.isSuccessful()) {
//                    response.body().setId(id);
//                    int itemPos = id - 1;
//                    adapter.updateFoodDeal(itemPos, response.body());
//                    adapter.notifyItemChanged(itemPos);
//                }
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

    @Override
    public void onSaveClicked(String title, String location, String message) {
        FoodDeal newFoodDeal = new FoodDeal();
//        Date date = new Date();
//        newFoodDeal.setUpdatedDate(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date));
//        newFoodDeal.setUpdatedTime(new SimpleDateFormat("HH:mm:ss", Locale.US).format(date));
        newFoodDeal.setTitle(title);
        newFoodDeal.setLocation(location);
        newFoodDeal.setMessage(message);
        newFoodDeal.setCreatedBy(DataHolder.getInstance().getUser().getUsername());

        FoodDealService.postFoodDeal(newFoodDeal, new Callback<FoodDeal>() {
            @Override
            public void onResponse(Call<FoodDeal> call, Response<FoodDeal> response) {
                getFoodDeals();
            }

            @Override
            public void onFailure(Call<FoodDeal> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}