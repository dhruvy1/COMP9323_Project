package com.comp9323.restclient.api;

import android.util.Log;

import com.comp9323.data.DataHolder;
import com.comp9323.data.beans.Event;
import com.comp9323.data.beans.EventResponse;
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

    private static final EventApi eventAPI = RestClient.getClient().create(EventApi.class);

//    public static void getEvents() {
//        eventAPI.getEvents().enqueue(new Callback<EventResponse>() {
//            @Override
//            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
//                EventResponse responseData = response.body();
//                List<Event> events = responseData.getResults();
//                DataHolder.getInstance().clearEvents();
//                for (Event e : events) {
//                    DataHolder.getInstance().addEvent(e);
//                }
//                Log.d("REST CALL", "CALL SUCCESS!");
//            }
//
//            @Override
//            public void onFailure(Call<EventResponse> call, Throwable t) {
//                Log.d("REST CALL", t.getStackTrace().toString() + "");
//                Log.d("REST CALL", "~~Fail CALL~~");
//                call.cancel();
//            }
//        });
//    }

    //TODO: filter lists by category
    public static void filterEvents(String filter) {
        List<Event> events = DataHolder.getInstance().getEvents();
        List<Event> filteredEvents = new ArrayList<>();
        for (Event e : events) {
            if (true) ;
        }
    }

    public void getEvent(int id) {
        eventAPI.getEvent(id).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event event = response.body();
                if (event != null) {
                    DataHolder.getInstance().addEvent(event);
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public void addEvent(Event event) {
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

    public void modifyEvent(int id, Event event) {
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

    public void deleteEvent(int id) {
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

    public void replaceEvent(int id, Event event) {
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

}
