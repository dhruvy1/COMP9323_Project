package com.comp9323.event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.Toolbar;
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
import com.comp9323.data.DateTimeConverter;
import com.comp9323.data.beans.Event;
import com.comp9323.main.R;
import com.comp9323.restclient.service.EventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment implements EventRvAdapter.Listener {
    private static final String TAG = "EventFragment";

    private EventRvAdapter rvAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_rv, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        initRvAdapter();
        initRecyclerView(view);
        initSwipeRefreshLayout(view);

        getEvents();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_events, menu);

        View t = menu.findItem(R.id.karma_point).setActionView(R.layout.menu_karma_point_view).getActionView();
        TextView textView = t.findViewById(R.id.karma_point_view);
        textView.setText(DataHolder.getInstance().getUser().getKarmarPoint());

        Spinner spinner = (Spinner) menu.findItem(R.id.event_spinner).getActionView();
        initSpinner(spinner);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.event_spinner:
                // Do nothing
                return true;
            case R.id.add_event:
                // Show add events view
                EventNewFormFragment eventFormFragment = new EventNewFormFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, eventFormFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            default:
                // Let the superclass handle it since the item is not recognised
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRvAdapter() {
        rvAdapter = new EventRvAdapter();
        rvAdapter.setListener(this);
    }
    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rvAdapter);
    }

    private void initSwipeRefreshLayout(View view) {
        swipeRefreshLayout = view.findViewById(R.id.event_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // reset the whole adapter
                initRvAdapter();
                recyclerView.setAdapter(rvAdapter);
                getEvents();
            }
        });
    }

    private void initSpinner(Spinner spinner) {
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this.getContext(), R.array.sort_array, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                rvAdapter.sortEvents(pos);
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getEvents() {
        EventService.getEvents(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    List<Event> events = new ArrayList<>();
                    Date today = DateTimeConverter.getToday();

                    for (Event ev : response.body()) {
                        try {
                            Date start = dateFormat.parse(ev.getStartDate().toString());
                            Log.d(TAG, start.toString());
                            Log.d(TAG, today.toString());
                            if (!today.after(start)) {
                                events.add(ev);
                            }
                        } catch (ParseException ex) { Log.d(TAG, ex.getMessage()); }
                    }

                    updateAdapter(events);

                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void updateAdapter(List<Event> events) {
        rvAdapter.setEvents(events);
        rvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEventLikeBtnClicked(final Integer id, String rating) {
        Event event = new Event();
        event.setRating(Integer.toString(Integer.parseInt(rating) + 1));
        EventService.patchEvent(id, event, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    @Override
    public void onEventDislikeBtnClicked(Integer id, String rating) {
        Event event = new Event();
        event.setRating(Integer.toString(Integer.parseInt(rating) - 1));
        EventService.patchEvent(id, event, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


}
