package com.delivery.ui.details;


import android.os.Bundle;

import com.delivery.R;
import com.delivery.databinding.ActivityLocationBinding;
import com.delivery.model.DeliveryItem;
import com.delivery.model.LocationCoordinates;
import com.delivery.utils.AppConstants;
import com.delivery.viewmodel.LocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, LifecycleOwner {

    private LocationViewModel locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        setupBindings();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DeliveryItem deliveryItemObject = locationViewModel.getDeliveryItem();
        LocationCoordinates location = null;
        if (deliveryItemObject != null)
            location = deliveryItemObject.getLocation();
        if (location != null) {
            LatLng loc = new LatLng(location.getLat(), location.getLng());

            googleMap.addMarker(new MarkerOptions().position(loc)
                    .title(location.getAddress()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            CameraUpdateFactory.zoomTo(12.0f);
        }
    }

    private void setupBindings() {
        ActivityLocationBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        DeliveryItem deliveryItem = getIntent().getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
               // new LocationViewModel(deliveryItem);
        locationViewModel.setDeliveryItem(deliveryItem);
        activityBinding.setLocationViewModel(locationViewModel);
    }


}

