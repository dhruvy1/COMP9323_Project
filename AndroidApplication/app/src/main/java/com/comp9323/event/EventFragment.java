package com.comp9323.event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comp9323.data.beans.Event;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.EventApi;

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

public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";

    private static final int MILLISECONDS_TO_POLL_SERVER = 15000;

    private EventRvAdapter adapter;
    private CompositeDisposable compositeDisposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_rv, container, false);

        compositeDisposable = new CompositeDisposable();

        adapter = new EventRvAdapter();

        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        getEvents();

        return view;
    }

    private void getEvents() {
        EventApi api = RestClient.getClient().create(EventApi.class);

        compositeDisposable.add(api.getEvents()
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        // polls server every # seconds
                        return objectObservable.delay(MILLISECONDS_TO_POLL_SERVER, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(List<Event> events) throws Exception {
                        updateAdapter(events);
                    }
                }));
    }

    private void updateAdapter(List<Event> events) {
        adapter.setEvents(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
    }
}
