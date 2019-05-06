package com.delivery;

import android.app.Application;

import com.delivery.network.DeliveryService;

public class App extends Application {

    private static App app;
    private static DeliveryService deliveryService;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        deliveryService = new DeliveryService();
    }

    public static DeliveryService getDeliveryService() {

        return deliveryService;
    }

    public static App getInstance() {
        return app;
    }


}
