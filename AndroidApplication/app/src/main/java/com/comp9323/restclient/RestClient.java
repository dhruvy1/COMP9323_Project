package com.comp9323.restclient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thomas on 10/9/2017.
 */

public class RestClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://52.65.129.3:8000/api/";

    public static Retrofit getClient() {

        if (retrofit == null) {
            // Logging for  HTTP requests and responses
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // HTTP client to make rest calls
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            // Retrofit used to generates URIs and parses Json using Gson
            // Retrofit uses OkHttpClient to make HTTP calls to REST server
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit;
    }
}
