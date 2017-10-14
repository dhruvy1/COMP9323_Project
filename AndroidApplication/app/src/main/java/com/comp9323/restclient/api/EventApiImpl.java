package com.comp9323.restclient.api;

import com.comp9323.data.DataHolder;
import com.comp9323.data.DateTimeConverter;
import com.comp9323.data.beans.Event;
import com.comp9323.restclient.RestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private static DateTimeConverter dtConvert = new DateTimeConverter();

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

    public static void postEvent(String name, String location, String startDate, String endDate,
                                 String startTime, String endTime, String description,
                                 String createdBy) {
        // TODO: ADD Photo/Image uploading
        Event newEvent = new Event();
        newEvent.setName(name);
        newEvent.setPlaceName(location);
        newEvent.setStartDate(dtConvert.convertA2SDate(startDate));
        newEvent.setEndDate(dtConvert.convertA2SDate(endDate));
        newEvent.setStartTime(dtConvert.convertA2STime(startTime));
        newEvent.setEndTime(dtConvert.convertA2STime(endTime));
        newEvent.setDescription(description);
        newEvent.setCreatedBy(createdBy);
        postEvent(newEvent);
    }

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
