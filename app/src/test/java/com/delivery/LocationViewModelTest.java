package com.delivery;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import android.content.Intent;

import com.delivery.data.network.model.DeliveryItemResponseModel;
import com.delivery.ui.activity.details.LocationViewModel;
import com.delivery.utils.AppConstants;
import com.jraska.livedata.TestObserver;

import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class LocationViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    private LocationViewModel viewModel;
    @Mock
    private Intent intent;

    @Before
    public void setUp() {
        viewModel = new LocationViewModel();
    }

    @Test
    public void intentDataAssertion() {
        final DeliveryItemResponseModel mDeliveryItemResponseModel =
                new DeliveryItemResponseModel("desc", "image_url", "1", null);

        final DeliveryItemResponseModel mDeliveryItemResponseModelDifferent =
                new DeliveryItemResponseModel("desc", "image_url", "2", null);
        Mockito.when(intent.getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT)).thenReturn(mDeliveryItemResponseModel);
        viewModel.loadDeliverableItemData(intent);
        try {
            TestObserver.test(viewModel.getDeliveryItemMutableLiveData()).
                    awaitValue().
                    assertValue(mDeliveryItemResponseModel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
