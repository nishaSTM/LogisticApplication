package com.delivery.network;

import com.delivery.model.DeliveryItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeliveryApi {
    @GET("deliveries/")
    Call<List<DeliveryItem>> getAllDeliveryItems(@Query("offset") Integer offset,
                                                 @Query("limit") Integer limit);
}