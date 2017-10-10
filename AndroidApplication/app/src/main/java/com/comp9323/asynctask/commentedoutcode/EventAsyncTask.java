//package com.comp9323.asynctask;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.comp9323.data.DataHolder;
//import com.comp9323.event.EventRvAdapter;
//import com.comp9323.restclient.api.EventApiImpl;
//
///**
// * Created by timta on 28/09/2017.
// */
//
//public class EventAsyncTask extends AsyncTask<Integer, Void, Boolean> {
//    private static final String TAG = "EventAsyncTask";
//
//    private final EventRvAdapter mAdapter;
////    LinearLayout l = (LinearLayout) findViewById(R.id.event_progress);
//
//    public EventAsyncTask(EventRvAdapter mAdapter) {
//        this.mAdapter = mAdapter;
//    }
//
//    @Override
//    protected Boolean doInBackground(Integer... integers) {
//        Log.d(TAG, "Getting event data from server....");
//        EventApiImpl.getEvents();
//        while (DataHolder.getInstance().getEvents().isEmpty()) {
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//            }
//        }
//        return true;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean bool) {
////        l.setVisibility(View.GONE);
//        if (bool) {
//            mAdapter.notifyDataSetChanged();
//            Log.d(TAG, "Event data extracted");
//        }
//    }
//}
