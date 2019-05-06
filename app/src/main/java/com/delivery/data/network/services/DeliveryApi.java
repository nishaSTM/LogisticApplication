package com.delivery.data.network.services;

import com.delivery.model.DeliveryItemResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeliveryApi {
    @GET("deliveries/")
    Call<List<DeliveryItemResponseModel>> getAllDeliveryItems(@Query("offset") Integer offset,
                                                              @Query("limit") Integer limit);
}