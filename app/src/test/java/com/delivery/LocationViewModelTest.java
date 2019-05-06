package com.delivery;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import android.content.Intent;
import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.utils.AppConstants;
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


    private Intent intent;

    @Before
    public void setUp() {

        intent = new Intent();
    }

    @Test
    public void intentDataAssertion() {
        final DeliveryItemResponseModel mDeliveryItemResponseModel =
                new DeliveryItemResponseModel("desc", "image_url", "1", null);
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, mDeliveryItemResponseModel);

        Assert.assertEquals(mDeliveryItemResponseModel, intent.getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT));

    }



}