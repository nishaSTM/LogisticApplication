package com.delivery.listener;

import com.delivery.model.DeliveryItem;
import com.delivery.viewmodel.DeliveryItemViewModel;

public interface DeliveryItemListener {
    void onDeliveryItemClick(DeliveryItem deliveryItem);

    DeliveryItemViewModel getDeliveryItemViewModel();
}
