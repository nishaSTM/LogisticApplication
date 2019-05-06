package com.delivery;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.delivery.model.DeliveryItem;
import com.delivery.viewmodel.LocationViewModel;

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
    private LocationViewModel locationViewModel;

    @Before
    public void setUp() {
        final DeliveryItem deliveryItem =
                new DeliveryItem("desc", "image_url", "1", null);
        locationViewModel = new LocationViewModel();
        locationViewModel.setDeliveryItem(deliveryItem);
    }

    @Test
    public void intentDataAssertion() {

        DeliveryItem deliveryItem = locationViewModel.getDeliveryItem();
        Assert.assertNotNull(deliveryItem);
    }
}