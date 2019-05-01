package com.delivery.ui.activity.details;


import android.os.Bundle;

import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delivery.R;
import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.model.LocationCoordinatesResponseModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, LifecycleOwner {

    @BindView(R.id.desc_location)
    TextView desc_location;

    @BindView(R.id.image_location)
    ImageView imageView;

    private LocationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);

        viewModel = createViewModel();

        viewModel.getDeliveryItemMutableLiveData().observe(LocationActivity.this, new LocationObserver());
        viewModel.loadDeliverableItemData(getIntent());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);
    }

    private LocationViewModel createViewModel() {
        return ViewModelProviders.of(this).get(LocationViewModel.class);
    }

    private class LocationObserver implements Observer<DeliveryItemResponseModel> {
        @Override
        public void onChanged(@Nullable DeliveryItemResponseModel deliveryItemResponseModel) {

            if (deliveryItemResponseModel != null) {
                desc_location.setText(deliveryItemResponseModel.getDescription());
                setImage(deliveryItemResponseModel.getImage());
            }
        }
    }

    private void setImage(String imageUrl) {
        Glide.with(this).load(imageUrl).into(imageView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DeliveryItemResponseModel deliveryObject = viewModel.getDeliveryItemMutableLiveData().getValue();
        LocationCoordinatesResponseModel location = null;
        if (deliveryObject != null)
            location = deliveryObject.getLocation();
        LatLng loc = new LatLng(location.getLat(), location.getLng());

        googleMap.addMarker(new MarkerOptions().position(loc)
                .title(location.getAddress()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        CameraUpdateFactory.zoomTo(12.0f);
    }


}

