package com.comp9323.event;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.comp9323.asynctask.EventAsyncTask;
import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.Event;
import com.comp9323.main.R;

public class EventFragment extends Fragment {

    private EventRvAdapter mAdapter;
    public static final View[] expandedView = {null};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

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
//                }
            }
        };
        mAdapter = new EventRvAdapter(mListener);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            new EventAsyncTask(mAdapter).execute(1);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DataHolder.getInstance().clearEvents();
    }

    public interface Listener {
        // TODO: Update argument type and name
        void onEventListItemClicked(Event item, View view, int position);
    }
}
