package com.comp9323.Events;

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

import com.comp9323.Events.EventFragment.OnListFragmentInteractionListener;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.R;

import java.io.InputStream;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).getName());
        //holder.mContentView.setText(mValues.get(position).getPlaceName());
        holder.mItem = SingletonDataHolder.getInstance().getEvent(position);
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
                    mListener.onListFragmentInteraction(holder.mItem, holder.mView, position);
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
        public final TextView mEventDescription;
        public final ImageView mEventImage;
        public EventBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventNameView = view.findViewById(R.id.event_name);
            mEventLocationView = view.findViewById(R.id.event_location);
            mEventTimeView = view.findViewById(R.id.event_timeframe);
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
        MyEventRecyclerViewAdapter mlistener;

        public DownloadImageTask(ImageView imageView, MyEventRecyclerViewAdapter listener) {
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
