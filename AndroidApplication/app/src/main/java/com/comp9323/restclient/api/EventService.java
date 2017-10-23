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
public class EventService {
    private static final String TAG = "EventService";

    private static final EventApi api = RestClient.getClient().create(EventApi.class);

    public void getEvent(int id, Callback<Event> callback) {
        api.getEvent(id).enqueue(callback);
    }

    public static void getEvents(Callback<List<Event>> callback) {
        api.getEvents().enqueue(callback);
    }

    public static void postEvent(Event event, Callback<Event> callback) {
        api.postEvent(event).enqueue(callback);
    }

    public static void deleteEvent(int id, Callback<Void> callback) {
        api.deleteEvent(id).enqueue(callback);
    }

    public static void putEvent(int id, Event event, Callback<Event> callback) {
        api.putEvent(id, event).enqueue(callback);
    }

    public static void patchEvent(int id, Event event, Callback<Event> callback) {
        api.patchEvent(id, event).enqueue(callback);
    }
}
