package com.comp9323.food.fooddeal;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.comp9323.asynctask.FoodDealAsyncTask;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;

public class FoodDealFragment extends Fragment {

    public static int page = 1;

    protected static FoodDealRvAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_deal_rv, container, false);

        setupRvAdapter();

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.food_deal_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // set onLoadListener to check for end of list
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!adapter.ifLoading() && !FoodDealRvAdapter.ifReachEnd()) {
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    int visibleThreshold = 1;
                    if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        page++;
                        new FoodDealAsyncTask(adapter).execute(FoodDealAsyncTask.GET_LIST, page + "");
                    }
                }
            }
        });

        // set refresh listener
        swipeRefreshLayout = view.findViewById(R.id.food_deal_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataHolder.getInstance().clearFoodDealList();
                FoodDealRvAdapter.setIsReachEnd(false);
                pullList(1);
            }
        });

        // pull item from server after launch if no item in the list
        if (DataHolder.getInstance().getFoodDealList().size() == 0) {
            pullList(1);
        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DataHolder.getInstance().clearFoodDealList();
    }

    private void setupRvAdapter() {
        adapter = new FoodDealRvAdapter(new Listener() {
            @Override
            public void onFoodDealItemClicked(FoodDeal item) {
                String link = item.getEventLink();
                if (link.length() > 0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setPackage("com.android.chrome");
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
        });
    }

    private void pullList(int page) {
        FoodDealFragment.page = page;
        new FoodDealAsyncTask(adapter).execute(FoodDealAsyncTask.GET_LIST, FoodDealFragment.page + "");
        swipeRefreshLayout.setRefreshing(false);
    }

    public void patchRating(String id, String rating) {
        new FoodDealAsyncTask(adapter).execute(FoodDealAsyncTask.RATING, id, rating);
    }


    public interface Listener {
        // TODO: Update argument type and name
        void onFoodDealItemClicked(FoodDeal item);
    }
}