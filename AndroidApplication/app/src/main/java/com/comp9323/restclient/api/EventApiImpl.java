package com.comp9323.restclient.api;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.Event;
import com.comp9323.restclient.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by timta on 28/09/2017.
 */
public class EventApiImpl {
    private static final String TAG = "EventApiImpl";

    private static final EventApi eventAPI = RestClient.getClient().create(EventApi.class);

    //TODO: filter lists by category
    public static void filterEvents(String filter) {
        List<Event> events = DataHolder.getInstance().getEvents();
        List<Event> filteredEvents = new ArrayList<>();
        for (Event e : events) {
            if (true) ;
        }
    }

    // TODO probably convert this to use RxJava
//    public void getEvent(int id) {
//        eventAPI.getEvent(id).enqueue(new Callback<Event>() {
//            @Override
//            public void onResponse(Call<Event> call, Response<Event> response) {
//                Event event = response.body();
//                if (event != null) {
//                    DataHolder.getInstance().addEvent(event);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Event> call, Throwable t) {
//                call.cancel();
//            }
//        });
//    }

    public static void postEvent(Event event) {
        eventAPI.postEvent(event).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                DataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public static void deleteEvent(int id) {
        eventAPI.deleteEvent(id).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                DataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public static void putEvent(int id, Event event) {
        eventAPI.putEvent(id, event).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                DataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public static void patchEvent(int id, Event event) {
        eventAPI.patchEvent(id, event).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                DataHolder.getInstance().saveEventResponse(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
