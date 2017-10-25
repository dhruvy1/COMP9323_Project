package com.comp9323.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.comp9323.data.DateTimeConverter;
import com.comp9323.data.beans.Event;
import com.comp9323.main.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventRvAdapter extends RecyclerView.Adapter<EventRvAdapter.ViewHolder> {
    private static final String TAG = "EventRvAdapter";

    private List<Event> events;
    private List<Integer> expandedList;
    private Listener listener;

    public EventRvAdapter() {
        events = new ArrayList<>();
        expandedList = new ArrayList<>();
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_event_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.event = events.get(position);
        holder.evNameView.setText(holder.event.getName());
        holder.evTimeView.setText(holder.event.getEventTime());
        holder.evRating.setText(holder.event.getRating());
        holder.evCreatedBy.setText("By " + holder.event.getCreatedBy());

        if (holder.event.getSourceUrl().length() > 0) {
            Glide.with(holder.evImage.getContext()).load(holder.event.getSourceUrl()).into(holder.evImage);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandedList.contains(position)) {
                    expandedList.remove(Integer.valueOf(position));
                } else {
                    expandedList.add(position);
                }
                showEventDetails(holder, position);
            }
        });

        showEventDetails(holder, position);
        initLocation(holder);
        initEventLikeBtn(holder);
        initEventDislikeBtn(holder);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    private void showEventDetails(ViewHolder holder, int position) {
        if (expandedList.contains(position)) {
            holder.evDetailsContainer.setVisibility(View.VISIBLE);
            initEventDetails(holder, position);
        } else {
            holder.evDetailsContainer.setVisibility(View.GONE);
        }
    }

    private void initEventDetails(final ViewHolder holder, final int position) {
        holder.evDescription.setText(holder.event.getDescription());
    }

    private void initLocation(final ViewHolder holder) {
        if (holder.event.getPlaceName().isEmpty() && holder.event.getStreet().isEmpty()) {
            holder.evLocationView.setVisibility(View.GONE);
            holder.evLocationPin.setVisibility(View.GONE);
        } else if (holder.event.getPlaceName().isEmpty() || holder.event.getStreet().isEmpty()) {
            holder.evLocationView.setText(holder.event.getPlaceName() + holder.event.getStreet());
        } else {
            holder.evLocationView.setText(holder.event.getPlaceName() + ", " + holder.event.getStreet());
        }
    }

    private void initEventLikeBtn(final ViewHolder holder) {
        holder.evLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEventLikeBtnClicked(holder.event.getId(), holder.event.getRating());
                holder.evLikeBtn.setImageResource(R.drawable.ic_thumb_up_blue_24dp);
                holder.evDislikeBtn.setImageResource(R.drawable.ic_thumb_down_black_24dp);
                int rating = Integer.parseInt(holder.evRating.getText().toString());
                holder.evRating.setText(String.valueOf(rating + 1));
            }
        });
    }

    private void initEventDislikeBtn(final ViewHolder holder) {
        holder.evDislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEventDislikeBtnClicked(holder.event.getId(), holder.event.getRating());
                holder.evLikeBtn.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                holder.evDislikeBtn.setImageResource(R.drawable.ic_thumb_down_blue_24dp);
                int rating = Integer.parseInt(holder.evRating.getText().toString());
                holder.evRating.setText(String.valueOf(rating - 1));
            }
        });
    }

    public void sortEvents(int pos) {
        expandedList.clear();

        switch(pos) {
            case 0:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        return e2.getName().compareToIgnoreCase(e1.getName());
                    }
                });
                break;
            case 1:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        return e1.getName().compareToIgnoreCase(e2.getName());
                    }
                });
                break;
            case 2:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        String start = e1.getStartDate() + " " + e2.getStartTime();
                        String end = e2.getEndDate() + " " + e2.getEndTime();
                        return DateTimeConverter.checkDateBeforeServer(end, start);
                    }
                });
                break;
            case 3:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        String start = e1.getStartDate() + " " + e2.getStartTime();
                        String end = e2.getEndDate() + " " + e2.getEndTime();
                        return DateTimeConverter.checkDateBeforeServer(start, end);
                    }
                });
                break;
            case 4:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        return e2.getRating().compareToIgnoreCase(e1.getRating());
                    }
                });
                break;
            case 5:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        return e1.getRating().compareToIgnoreCase(e2.getRating());
                    }
                });
                break;
            default:
                Collections.sort(events, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        return e1.getId().compareTo(e2.getId());
                    }
                });
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView evNameView;
        public final TextView evLocationView;
        public final TextView evTimeView;
        public final TextView evDescription;
        public final ImageView evImage;
        public final ImageView evLocationPin;
        public final ImageButton evLikeBtn;
        public final ImageButton evDislikeBtn;
        public final TextView evRating;
        public final TextView evCreatedBy;

        public RelativeLayout evDetailsContainer;
        public Event event;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            evNameView = view.findViewById(R.id.event_name);
            evLocationView = view.findViewById(R.id.event_location);
            evTimeView = view.findViewById(R.id.event_time_frame);
            evDescription = view.findViewById(R.id.event_description);
            evImage = view.findViewById(R.id.event_photo);
            evLocationPin = view.findViewById(R.id.event_location_pin);
            evDetailsContainer = view.findViewById(R.id.event_details_container);
            evLikeBtn = view.findViewById(R.id.ev_like_btn);
            evDislikeBtn = view.findViewById(R.id.ev_dislike_btn);
            evRating = view.findViewById(R.id.ev_rating);
            evCreatedBy = view.findViewById(R.id.ev_created_by);
        }

    }

    public interface Listener {
        void onEventLikeBtnClicked(Integer id, String rating);

        void onEventDislikeBtnClicked(Integer id, String rating);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
