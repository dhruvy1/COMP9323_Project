package com.comp9323.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.comp9323.Events.MyEventRecyclerViewAdapter;
import com.comp9323.RestAPI.APIImpl.EventImpl;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.myapplication.R;

/**
 * Created by timta on 28/09/2017.
 */

public class GetEventData extends AsyncTask<Integer, Void, Boolean> {
    private final MyEventRecyclerViewAdapter mAdapter;
//    LinearLayout l = (LinearLayout) findViewById(R.id.event_progress);

    public GetEventData(MyEventRecyclerViewAdapter mAdapter) {
        super();
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onPreExecute() {
//        l.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(Integer... integers) {
        Log.d("A", "Getting event data from server....");
        EventImpl.getEvents(integers[0]);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean b) {
//        l.setVisibility(View.GONE);

        mAdapter.notifyDataSetChanged();
        Log.d("A*", "Event data extracted");
    }
}
