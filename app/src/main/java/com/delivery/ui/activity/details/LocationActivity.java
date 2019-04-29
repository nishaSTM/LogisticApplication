package com.delivery.ui.activity.details;


import android.os.Bundle;

import androidx.annotation.Nullable;


import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.delivery.R;
import com.delivery.data.network.model.DeliveryItemResponseModel;
import com.delivery.utils.AppConstants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, LifecycleOwner {

    @BindView(R.id.desc)
    TextView desc;

    @BindView(R.id.image)
    AppCompatImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        LocationViewModel viewModel = createViewModel();

        viewModel.getDeliveryItemMutableLiveData().observe(LocationActivity.this, new LocationObserver());

        viewModel.loadDeliverableItemData(getIntent());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private LocationViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LocationViewModel.class);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class LocationObserver implements Observer<DeliveryItemResponseModel> {
        @Override
        public void onChanged(@Nullable DeliveryItemResponseModel deliveryItemResponseModel) {

            if (deliveryItemResponseModel != null) {

                desc.setText(deliveryItemResponseModel.getDescription());
                setImage(deliveryItemResponseModel.getImage());
            }


        }
    }

    private void setImage(String imageUrl) {
        Glide.with(this).load(imageUrl).into(imageView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        DeliveryItemResponseModel deliveryObject = getIntent().getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT);
        LatLng loc = new LatLng(deliveryObject.getLocation().getLat(), deliveryObject.getLocation().getLng());
        googleMap.addMarker(new MarkerOptions().position(loc)
                .title(deliveryObject.getLocation().getAddress()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        CameraUpdateFactory.zoomTo(12.0f);
    }


}

