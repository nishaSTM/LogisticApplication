package com.delivery.ui.activity.main;


import androidx.annotation.NonNull;
import com.delivery.data.DataManager;
import com.delivery.data.network.services.DeliveryService;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class DeliveryViewModelFactory implements ViewModelProvider.Factory {
    private final DeliveryService deliveryService;


    public DeliveryViewModelFactory() {
        this.deliveryService = DataManager.getInstance().getDeliveryService();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliveryViewModel.class)) {
            return (T) new DeliveryViewModel(deliveryService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
