package com.delivery.ui.activity.details;


import android.content.Intent;

import androidx.annotation.NonNull;

import com.delivery.data.network.model.DeliveryItemResponseModel;
import com.delivery.utils.AppConstants;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class LocationViewModel extends ViewModel {

    private final MutableLiveData<DeliveryItemResponseModel> deliveryItemMutableLiveData = new MutableLiveData<>();

    public void loadDeliverableItemData(@NonNull Intent intent) {
        DeliveryItemResponseModel deliveryItemExtra = intent.getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT);
        deliveryItemMutableLiveData.postValue(deliveryItemExtra);
    }

    public MutableLiveData<DeliveryItemResponseModel> getDeliveryItemMutableLiveData() {
        return deliveryItemMutableLiveData;
    }
}
