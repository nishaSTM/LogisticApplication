package com.delivery.viewmodel;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.delivery.model.DeliveryItem;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private DeliveryItem deliveryItem;

    public DeliveryItem getDeliveryItem() {
        return deliveryItem;
    }

    @SuppressWarnings("unused")
    @BindingAdapter("image")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    public String getImage() {
        return deliveryItem.getImage();
    }

    public String getDescription() {
        return deliveryItem.getDescription();
    }

    public void setDeliveryItem(DeliveryItem deliveryItem) {
        this.deliveryItem = deliveryItem;
    }
}
