package com.comp9323.event;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.Event;
import com.comp9323.main.R;

import java.io.InputStream;

public class EventRvAdapter extends RecyclerView.Adapter<EventRvAdapter.ViewHolder> {

    private final EventFragment.Listener mListener;

    public EventRvAdapter(EventFragment.Listener listener) {
        mListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = DataHolder.getInstance().getEvent(position);
        holder.mEventNameView.setText(holder.mItem.getName());
        holder.mEventLocationView.setText(holder.mItem.getPlaceName() + ", " + holder.mItem.getStreet());
        holder.mEventTimeView.setText(holder.mItem.getEventTime());
        holder.mEventDescription.setText(holder.mItem.getDescription());
        new DownloadImageTask(holder.mEventImage, this).execute(holder.mItem.getSourceUrl());
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
        //return mValues.size();
        return DataHolder.getInstance().getEvents().size();
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

        @Override
        public String toString() {
            return super.toString() + " '" + mEventLocationView.getText() + "'";
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        EventRvAdapter mlistener;

        public DownloadImageTask(ImageView imageView, EventRvAdapter listener) {
            bmImage = imageView;
            mlistener = listener;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                //mIcon11 = Bitmap.createScaledBitmap(mIcon11, 120, 120, false);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            mlistener.notifyDataSetChanged();
        }
    }
}
