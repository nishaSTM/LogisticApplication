package com.delivery.viewmodel;


import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.delivery.model.DeliveryItem;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

public class DeliveryItemViewModel extends ViewModel {

    private DeliveryItem deliveryItem;

    public DeliveryItemViewModel() {

    }

 /*   public DeliveryItem getDeliveryItem() {
         return deliveryItem;
     }
*/
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    public String getImageUrl() {
        return deliveryItem.getImage();
    }

    public String getDescription() {
        return deliveryItem.getId()+deliveryItem.getDescription();
    }

    public void setDeliveryItem(DeliveryItem deliveryItem) {
        Log.d("deliveryItem##",deliveryItem+"");

        this.deliveryItem = deliveryItem;

    }
}
