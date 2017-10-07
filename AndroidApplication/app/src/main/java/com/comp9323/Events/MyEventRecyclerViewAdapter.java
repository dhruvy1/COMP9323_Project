package com.comp9323.Events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp9323.Events.EventFragment.OnListFragmentInteractionListener;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EventBean} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> {

    //private final List<EventBean> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyEventRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        //mValues = items;
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getName());
        //holder.mContentView.setText(mValues.get(position).getPlaceName());
        EventBean eventBean = SingletonDataHolder.getInstance().getEvent(position);
        holder.mItem = eventBean;
        holder.mEventNameView.setText(eventBean.getName());
        holder.mEventLocationView.setText(eventBean.getPlaceName());
        holder.mEventTimeView.setText(eventBean.getEventTime());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return mValues.size();
        return SingletonDataHolder.getInstance().getEvents().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mEventNameView;
        public final TextView mEventLocationView;
        public final TextView mEventTimeView;
        public EventBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventNameView = view.findViewById(R.id.event_name);
            mEventLocationView = view.findViewById(R.id.event_location);
            mEventTimeView = view.findViewById(R.id.event_timeframe);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventLocationView.getText() + "'";
        }
    }
}
