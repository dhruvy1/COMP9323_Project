package com.comp9323.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp9323.data.beans.Event;
import com.comp9323.main.R;

import java.util.List;

/**
 * Created by timta on 15/09/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> eventList;
    private static MyClickListener myClickListener;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.event_title.setText(event.getName());
        holder.event_loc.setText(event.getPlaceName() + event.getStreet());
        holder.event_start.setText(event.getStartTime());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView event_title, event_loc, event_start;

        public ViewHolder(View itemView) {
            super(itemView);
            event_title = itemView.findViewById(R.id.event_item_name);
            event_loc = itemView.findViewById(R.id.event_item_location);
            event_start = itemView.findViewById(R.id.event_item_start);
        }
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}
