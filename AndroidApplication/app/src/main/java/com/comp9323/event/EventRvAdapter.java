package com.comp9323.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.comp9323.data.beans.Event;
import com.comp9323.data.beans.EventResponse;
import com.comp9323.main.R;

import java.util.ArrayList;
import java.util.List;

public class EventRvAdapter extends RecyclerView.Adapter<EventRvAdapter.ViewHolder> {
    private static final String TAG = "EventRvAdapter";

    private final EventFragment.Listener mListener;
    private List<Event> events;

    public EventRvAdapter(EventFragment.Listener listener) {
        mListener = listener;
        events = new ArrayList<>();
    }

    public void setEventResponse(List<Event> events) {
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = events.get(position);

        holder.mEventNameView.setText(holder.mItem.getName());
        holder.mEventLocationView.setText(holder.mItem.getPlaceName() + ", " + holder.mItem.getStreet());
        holder.mEventTimeView.setText(holder.mItem.getEventTime());
        holder.mEventDescription.setText(holder.mItem.getDescription());

        if (holder.mItem.getSourceUrl().length() > 0) {
            Glide.with(holder.mEventImage.getContext()).load(holder.mItem.getSourceUrl()).into(holder.mEventImage);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onEventListItemClicked(holder.mItem, holder.mView, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mEventNameView;
        public final TextView mEventLocationView;
        public final TextView mEventTimeView;
        public final TextView mEventDescription;
        public final ImageView mEventImage;
        public Event mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventNameView = view.findViewById(R.id.event_name);
            mEventLocationView = view.findViewById(R.id.event_location);
            mEventTimeView = view.findViewById(R.id.event_time_frame);
            mEventDescription = view.findViewById(R.id.event_description);
            mEventImage = view.findViewById(R.id.event_photo);
        }
    }
}
