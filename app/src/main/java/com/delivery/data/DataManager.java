package com.delivery.data;


import com.delivery.data.network.services.DeliveryService;

public class DataManager {

    private static DataManager sInstance;

    private DataManager() {
    }

    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    public DeliveryService getDeliveryService() {
        return DeliveryService.getInstance();
    }

}
