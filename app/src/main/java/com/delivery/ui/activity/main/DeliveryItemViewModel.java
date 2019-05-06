package com.delivery.ui.activity.main;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.ui.activity.details.LocationActivity;
import com.delivery.utils.AppConstants;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

public class DeliveryItemViewModel extends ViewModel {

    private DeliveryItemResponseModel deliveryItemResponseModel;

    public DeliveryItemViewModel(DeliveryItemResponseModel deliveryItemResponseModel) {
       this.deliveryItemResponseModel=deliveryItemResponseModel;
    }
    public DeliveryItemResponseModel getDeliveryItemResponseModel() {
        return deliveryItemResponseModel;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(view.getContext(), LocationActivity.class);
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, deliveryItemResponseModel);
        view.getContext().startActivity(intent);
    }
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

    public void setDeliveryItem(DeliveryItemResponseModel deliveryItemViewModel) {
        this.deliveryItemResponseModel=deliveryItemResponseModel;


    }
}
