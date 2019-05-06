package com.delivery.ui.activity.details;


import android.os.Bundle;

import com.delivery.R;
import com.delivery.databinding.ActivityLocationBinding;
import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.model.LocationCoordinatesResponseModel;
import com.delivery.utils.AppConstants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, LifecycleOwner {

    private LocationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        setupBindings();

    }

    private void setupBindings() {
        ActivityLocationBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        DeliveryItemResponseModel deliveryItemResponseModel = getIntent().getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT);
        viewModel = new LocationViewModel(deliveryItemResponseModel);
        activityBinding.setLocationViewModel(viewModel);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DeliveryItemResponseModel deliveryObject = viewModel.getDeliveryItemResponseModel();
        LocationCoordinatesResponseModel location = null;
        if (deliveryObject != null)
            location = deliveryObject.getLocation();
        if (location != null) {
            LatLng loc = new LatLng(location.getLat(), location.getLng());

            googleMap.addMarker(new MarkerOptions().position(loc)
                    .title(location.getAddress()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            CameraUpdateFactory.zoomTo(12.0f);
        }
    }


}

