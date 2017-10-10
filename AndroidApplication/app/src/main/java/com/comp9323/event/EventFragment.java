package com.comp9323.event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.comp9323.data.beans.Event;
import com.comp9323.data.beans.EventResponse;
import com.comp9323.main.R;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.EventApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";

    private EventRvAdapter mAdapter;
    private CompositeDisposable mCompositeDisposable;
    public static final View[] expandedView = {null};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_rv, container, false);

        Listener mListener = new Listener() {
            @Override
            public void onEventListItemClicked(Event item, View view, int position) {
                RelativeLayout detail = view.findViewById(R.id.event_details);
                if (detail.getVisibility() != View.VISIBLE) {
                    detail.setVisibility(View.VISIBLE);
                    if (expandedView[0] != null) {
                        expandedView[0].findViewById(R.id.event_details).setVisibility(View.GONE);
                    }
                    expandedView[0] = view;
                } else {
                    detail.setVisibility(View.GONE);
                    expandedView[0] = null;
                }
            }
        };

        mCompositeDisposable = new CompositeDisposable();

        mAdapter = new EventRvAdapter(mListener);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(mAdapter);

        getEvents();

        return view;
    }

    private void getEvents() {
        EventApi api = RestClient.getClient().create(EventApi.class);

        mCompositeDisposable.add(api.getEvents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<EventResponse>() {
                    @Override
                    public void accept(EventResponse eventResponse) throws Exception {
                        updateAdapter(eventResponse);
                    }
                }));
    }

    private void updateAdapter(EventResponse eventResponse) {
        mAdapter.setEventResponse(eventResponse);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();
    }

    public interface Listener {
        // TODO: Update argument type and name
        void onEventListItemClicked(Event item, View view, int position);
    }
}
