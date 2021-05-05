package org.me.gcu.remote;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class ApiService {
    public static final String BASE_URL = "http://quakes.bgs.ac.uk/";

    // to log the network operations
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

    // thread management
    RxJava2CallAdapterFactory callAdapterFactory = RxJava2CallAdapterFactory
            .createWithScheduler(Schedulers.io());

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(callAdapterFactory)
            .build();

    public ApiEndpoints api = retrofit.create(ApiEndpoints.class);
}
