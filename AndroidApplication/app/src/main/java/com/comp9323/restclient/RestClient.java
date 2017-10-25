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
    private static final String BASE_URL = "http://52.65.129.3:8000/api/";

    // Logging for  HTTP requests and responses
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    // HTTP client to make rest calls
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    // Retrofit used to generates URIs and parses Json using Gson
    // Retrofit uses OkHttpClient to make HTTP calls to REST server
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build();

    // get the client to make REST calls
    public static Retrofit getClient() {
        return retrofit;
    }
}
