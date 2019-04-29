package com.delivery.ui.activity.main;


import androidx.annotation.NonNull;


import com.delivery.data.network.model.DeliveryItemResponseModel;
import com.delivery.data.network.model.Result;
import com.delivery.data.network.services.DeliveryService;
import com.delivery.utils.AppConstants;


import java.util.ArrayList;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryViewModel extends ViewModel {

    private final MutableLiveData<Result> deliveryResult;
    private final MutableLiveData<Boolean> isLoading;

    private final DeliveryService deliveryService;
    private final List<DeliveryItemResponseModel> deliveryItemArrayList;
    private final Result result;

    public DeliveryViewModel(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        deliveryResult = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();

        deliveryItemArrayList = new ArrayList<>();
        result = new Result(null, null, null);
    }

    public MutableLiveData<Result> getDeliveryList() {
        return deliveryResult;
    }

    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    public void loadDeliveriesNetwork(int offset) {
        setIsLoading(true);

        Call<List<DeliveryItemResponseModel>> deliveryItemCall = deliveryService.getDeliveryApi().
                getAllDeliveryItems(offset, AppConstants.LIMIT);
        deliveryItemCall.enqueue(new DeliveryCallback());

    }


    private void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }


    private void setDeliveries(Result deliveryItems) {
        setIsLoading(false);

        deliveryResult.postValue(deliveryItems);
    }


    /**
     * Callback
     **/
    private class DeliveryCallback implements Callback<List<DeliveryItemResponseModel>> {

        @Override
        public void onResponse(@NonNull Call<List<DeliveryItemResponseModel>> call, @NonNull Response<List<DeliveryItemResponseModel>> response) {
            List<DeliveryItemResponseModel> deliveryItemResponse = response.body();


            if (deliveryItemResponse == null) {
                result.setStatus(Result.STATUS.ERROR);
                result.setError(AppConstants.CONNECTION_OFF);
                result.setData(deliveryItemArrayList);

                setDeliveries(result);
            } else {
                deliveryItemArrayList.addAll(deliveryItemResponse);
                result.setStatus(Result.STATUS.SUCCESS);
                result.setData(deliveryItemArrayList);

                setDeliveries(result);
            }

        }

        @Override

        public void onFailure(@NonNull Call<List<DeliveryItemResponseModel>> call, @NonNull Throwable t) {


            result.setStatus(Result.STATUS.ERROR);
            result.setData(deliveryItemArrayList);

            result.setError(t.getMessage());
            setDeliveries(result);


        }
    }
}
