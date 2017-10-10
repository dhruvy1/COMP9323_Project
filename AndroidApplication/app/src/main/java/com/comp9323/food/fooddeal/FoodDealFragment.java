package com.comp9323.food.fooddeal;

import android.content.ActivityNotFoundException;
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

import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.FoodDealApi;
import com.comp9323.restclient.api.FoodDealApiImpl;

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

public class FoodDealFragment extends Fragment implements FoodDealRvAdapter.Listener {
    private static final String TAG = "FoodDealFragment";

    private static final int SECONDS_TO_POLL_SERVER = 1;

    private FoodDealRvAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CompositeDisposable compositeDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_deal_rv, container, false);

        compositeDisposable = new CompositeDisposable();

        initRvAdapter();
        initRecyclerView(view);
        initSwipeRefreshLayout(view);

        getFoodDeals();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
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
        FoodDealApi api = RestClient.getClient().create(FoodDealApi.class);
        compositeDisposable.clear(); // clear previous async tasks
        compositeDisposable.add(api.getFoodDeals()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        // polls server every # seconds
                        return objectObservable.delay(SECONDS_TO_POLL_SERVER, TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // indicate the subscribe will be on the main thread
                .subscribeOn(Schedulers.io()) // do async in the io thread
                .subscribe(new Consumer<List<FoodDeal>>() { // add a listener to the observable
                    @Override
                    public void accept(List<FoodDeal> foodDeals) throws Exception {
                        updateAdapter(foodDeals); // update adapter
                        if (swipeRefreshLayout.isRefreshing()) {
                            // turn off refresh animation on swipe ups, if its on
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }));
    }

    private void updateAdapter(List<FoodDeal> foodDeals) {
        adapter.setFoodDeals(foodDeals);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFoodDealLikeBtnClicked(Integer id, String rating) {
        FoodDeal foodDeal = new FoodDeal();
        foodDeal.setRating(Integer.toString(Integer.parseInt(rating) + 1));
        FoodDealApiImpl fai = new FoodDealApiImpl();
        fai.patchFoodDeal(id, foodDeal);
    }

    @Override
    public void onFoodDealDislikeBtnClicked(Integer id, String rating) {
        FoodDeal foodDeal = new FoodDeal();
        foodDeal.setRating(Integer.toString(Integer.parseInt(rating) - 1));
        FoodDealApiImpl fai = new FoodDealApiImpl();
        fai.patchFoodDeal(id, foodDeal);
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