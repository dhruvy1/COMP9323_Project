package com.comp9323.RestAPI.APIImpl;

import com.comp9323.RestAPI.APIInterface.EventInterface;
import com.comp9323.RestAPI.APIInterface.RestClient;
import com.comp9323.RestAPI.Beans.EventBean;
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

    public static void getEvents(int page) {
        eventAPI.getEvents(page).enqueue(new Callback<List<EventBean>>() {
            @Override
            public void onResponse(Call<List<EventBean>> call, Response<List<EventBean>> response) {
                List<EventBean> events = response.body();
                SingletonDataHolder.getInstance().addEvents(events);
            }

            @Override
            public void onFailure(Call<List<EventBean>> call, Throwable t) {
                call.cancel();
            }
        });
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
