package com.comp9323.RestAPI.APIImpl;

import android.util.Log;

import com.comp9323.RestAPI.APIInterface.EventInterface;
import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.EventResponse;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by timta on 28/09/2017.
 */

public class EventImpl {

    private static final EventInterface eventAPI = RestClient.getClient().create(EventInterface.class);

    public static void getEvents() {
        eventAPI.getEvents().enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse responseData = response.body();
                List<EventBean> events = responseData.getResults();
                SingletonDataHolder.getInstance().clearEvents();
                for (EventBean e : events) {
                    SingletonDataHolder.getInstance().addEvent(e);
                }
                Log.d("REST CALL", "CALL SUCCESS!");
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.d("REST CALL", t.getStackTrace().toString()+"");
                Log.d("REST CALL", "~~Fail CALL~~");
                call.cancel();
            }
        });
    }

    //TODO: filter lists by category
    public static void filterEvents(String filter) {
        List<EventBean> events = SingletonDataHolder.getInstance().getEvents();
        List<EventBean> filteredEvents = new ArrayList<>();
        for (EventBean e : events) {
            if (true);
        }
    }

    public void getEvent(int id) {
        eventAPI.getEvent(id).enqueue(new Callback<EventBean>() {
            @Override
            public void onResponse(Call<EventBean> call, Response<EventBean> response) {
                EventBean event = response.body();
                if (event != null) {SingletonDataHolder.getInstance().addEvent(event);}
            }

            @Override
            public void onFailure(Call<EventBean> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void addEvent(EventBean event) {
        eventAPI.addEvent(event).enqueue(new Callback<EventBean>() {
            @Override
            public void onResponse(Call<EventBean> call, Response<EventBean> response) {
                SingletonDataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<EventBean> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void modifyEvent(int id, EventBean event) {
        eventAPI.editEvent(id, event).enqueue(new Callback<EventBean>() {
            @Override
            public void onResponse(Call<EventBean> call, Response<EventBean> response) {
                SingletonDataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<EventBean> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void deleteEvent(int id) {
        eventAPI.deleteEvent(id).enqueue(new Callback<EventBean>() {
            @Override
            public void onResponse(Call<EventBean> call, Response<EventBean> response) {
                SingletonDataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<EventBean> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void replaceEvent(int id, EventBean event) {
        eventAPI.replaceEvent(id, event).enqueue(new Callback<EventBean>() {
            @Override
            public void onResponse(Call<EventBean> call, Response<EventBean> response) {
                SingletonDataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<EventBean> call, Throwable t) {
                call.cancel();
            }
        });
    }

}
