package com.delivery;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.ui.activity.details.LocationViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocationViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();


    LocationViewModel locationViewModel;

    @Before
    public void setUp() {
        final DeliveryItemResponseModel deliveryItemResponseModel =
                new DeliveryItemResponseModel("desc", "image_url", "1", null);

        locationViewModel = new LocationViewModel(deliveryItemResponseModel);

    }

    @Test
    public void intentDataAssertion() {

        DeliveryItemResponseModel deliveryItemResponseModel = locationViewModel.getDeliveryItemResponseModel();
        Assert.assertNotNull(deliveryItemResponseModel);

    }


}