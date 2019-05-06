package com.delivery.viewmodel;


import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.delivery.App;
import com.delivery.model.DeliveryItem;
import com.delivery.model.Result;
import com.delivery.network.DeliveryService;
import com.delivery.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryViewModel extends ViewModel {

    private final MutableLiveData<Result> deliveryResult;
    private final DeliveryService deliveryService;
    public final ObservableInt progressBar;
    public final ObservableInt emptyView;

    private final List<DeliveryItem> deliveryItemArrayList;

    public DeliveryViewModel() {
        this.deliveryService = App.getDeliveryService();
        deliveryResult = new MutableLiveData<>();
        deliveryItemArrayList = new ArrayList<>();
        progressBar = new ObservableInt();
        emptyView = new ObservableInt();
    }

    public MutableLiveData<Result> getDeliveryList() {
        return deliveryResult;
    }

    public void loadDeliveriesNetwork(int offset) {
        progressBar.set(View.VISIBLE);
        Log.d("offset value##",offset+"");
        Call<List<DeliveryItem>> deliveryItemCall = deliveryService.getDeliveryApi().
                getAllDeliveryItems(offset, AppConstants.PAGE_LIMIT);
        deliveryItemCall.enqueue(new DeliveryCallback());

    }

    public void onClickRetryButton(View view) {
        int offset = 0;
        loadDeliveriesNetwork(offset);
    }

    private void setDeliveries(Result deliveryItems) {
        progressBar.set(View.GONE);
        deliveryResult.postValue(deliveryItems);
    }

    /**
     * Callback
     **/
    private class DeliveryCallback implements Callback<List<DeliveryItem>> {

        @Override
        public void onResponse(@NonNull Call<List<DeliveryItem>> call, @NonNull Response<List<DeliveryItem>> response) {
            List<DeliveryItem> deliveryItemResponse = response.body();
            if (deliveryItemResponse == null) {
                Result result = new Result(Result.STATUS.ERROR, deliveryItemArrayList, response.message());
                setDeliveries(result);
            } else {
                deliveryItemArrayList.addAll(deliveryItemResponse);
                Result result = new Result(Result.STATUS.SUCCESS, deliveryItemArrayList, null);
                setDeliveries(result);

            }

        }

        @Override

        public void onFailure(@NonNull Call<List<DeliveryItem>> call, @NonNull Throwable t) {

            Result result = new Result(Result.STATUS.ERROR, deliveryItemArrayList, t.getMessage());
            setDeliveries(result);
        }
    }
}
