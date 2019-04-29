package com.delivery.data.network.services;


import com.delivery.App;
import com.delivery.data.network.model.DeliveryItemResponseModel;
import com.delivery.utils.InternetUtil;
import com.google.gson.Gson;


import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class DeliveryService {

    private static final String URL = "https://mock-api-mobile.dev.lalamove.com/";

    private DeliveryApi mDeliveryApi;


    private static DeliveryService instance;


    public static DeliveryService getInstance() {
        if (instance == null) {
            instance = new DeliveryService();

        }
        return instance;
    }


    private DeliveryService() {
        setupRetrofitAndOkHttpAnotherMethod(App.getInstance().getCacheDir());
    }


    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header("Cache-Control");
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 5000)
                    .build();
        } else {
            return originalResponse;
        }
    };

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = chain -> {
        Request request = chain.request();
        if (!InternetUtil.isNetworkAvailable(App.getInstance())) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build();
        }
        return chain.proceed(request);
    };

    private void setupRetrofitAndOkHttpAnotherMethod(File cacheDir) {
        File httpCacheDirectory = new File(cacheDir, "offlineCache");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mDeliveryApi = mRetrofit.create(DeliveryApi.class);
    }


    public DeliveryApi getDeliveryApi() {
        return mDeliveryApi;
    }

    public interface DeliveryApi {

        @GET("deliveries/")
        Call<List<DeliveryItemResponseModel>> getAllDeliveryItems(@Query("offset") Integer offset,
                                                                  @Query("limit") Integer limit);
    }

}


