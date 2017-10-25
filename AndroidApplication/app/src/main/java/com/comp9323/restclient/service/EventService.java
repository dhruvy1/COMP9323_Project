package com.comp9323.restclient.service;

import com.comp9323.data.beans.Event;
import com.comp9323.restclient.RestClient;
import com.comp9323.restclient.api.EventApi;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by timta on 28/09/2017.
 */
public class EventService {
    private static final String TAG = "EventService";

    private static final EventApi api = RestClient.getClient().create(EventApi.class);

    /**
     * Get a single event from the server
     *
     * @param id       the id of the event
     * @param callback the callback where the response of the server will be returned
     */
    public void getEvent(int id, Callback<Event> callback) {
        api.getEvent(id).enqueue(callback);
    }

    /**
     * Get all the events from the server
     *
     * @param callback the callback where the response of the server will be returned
     */
    public static void getEvents(Callback<List<Event>> callback) {
        api.getEvents().enqueue(callback);
    }

    /**
     * Post a new event to the server
     *
     * @param event    the new event
     * @param callback the callback where the response of the server will be returned
     */
    public static void postEvent(Event event, Callback<Event> callback) {
        api.postEvent(event).enqueue(callback);
    }

    /**
     * Delete an event from the server
     *
     * @param id       the event id
     * @param callback the callback where the response of the server will be returned
     */
    public static void deleteEvent(int id, Callback<Void> callback) {
        api.deleteEvent(id).enqueue(callback);
    }

    /**
     * Put an event into the server
     *
     * @param id       the id of the event
     * @param event    the event
     * @param callback the callback where the response of the server will be returned
     */
    public static void putEvent(int id, Event event, Callback<Event> callback) {
        api.putEvent(id, event).enqueue(callback);
    }

    /**
     * Patch an event in the server
     *
     * @param id       the id of the event
     * @param event    the fields of the event to be patched
     * @param callback the callback where the response of the server will be returned
     */
    public static void patchEvent(int id, Event event, Callback<Event> callback) {
        api.patchEvent(id, event).enqueue(callback);
    }
}
