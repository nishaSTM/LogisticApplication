package com.delivery.ui.activity.details;


import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.delivery.model.DeliveryItemResponseModel;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {
    private final DeliveryItemResponseModel deliveryItemResponseModel;
    public LocationViewModel(DeliveryItemResponseModel deliveryItemResponseModel) {
        this.deliveryItemResponseModel=deliveryItemResponseModel;
    }

    public DeliveryItemResponseModel getDeliveryItemResponseModel() {
        return deliveryItemResponseModel;
    }

    @SuppressWarnings("unused")
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    public String getImageUrl()
    {
        return deliveryItemResponseModel.imageUrl;
    }
    public String getDescription() {
        return deliveryItemResponseModel.description;
    }

}
