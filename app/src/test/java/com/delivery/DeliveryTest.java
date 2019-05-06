package com.delivery;

import com.delivery.model.DeliveryItem;
import com.delivery.model.Result;

import com.delivery.network.DeliveryApi;
import com.delivery.network.DeliveryService;
import com.delivery.viewmodel.DeliveryViewModel;
import com.delivery.utils.AppConstants;
import com.jraska.livedata.TestObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    private DeliveryViewModel deliveryViewModel;

    @Mock
    DeliveryService deliveryService;

    @Mock
    DeliveryApi deliveryApi;

    @Mock
    private Call<List<DeliveryItem>> deliveryItemList;

    private Throwable throwable;

    @Before
    public void setUp() {
        deliveryViewModel = new DeliveryViewModel();
    }


    @Test(expected = InterruptedException.class)
    public void testDeliveryViewModelResponseSuccess() throws InterruptedException {
        List<DeliveryItem> list = new ArrayList<>();
        DeliveryItem itemResponseModel = new DeliveryItem("desc", "imagUrl", "id", null);
        list.add(itemResponseModel);
        Mockito.doAnswer(invocation -> {
            Callback<List<DeliveryItem>> call = invocation.getArgument(0);
            call.onResponse(deliveryItemList, Response.success(list));
            return null;
        }).when(deliveryItemList).enqueue(Mockito.any());
        Mockito.when(deliveryApi.getAllDeliveryItems(0, AppConstants.PAGE_LIMIT)).thenReturn(deliveryItemList);
        Mockito.when(deliveryService.getDeliveryApi()).thenReturn(deliveryApi);
        deliveryViewModel.loadDeliveriesNetwork(0);

        TestObserver.test(deliveryViewModel.getDeliveryList()).awaitValue().assertHasValue().
                map(Result::getData)
                .assertValue(list);


    }

    @Test(expected = InterruptedException.class)
    public void testDeliveryViewModelResponseFailure() throws InterruptedException {
        Mockito.doAnswer(invocation -> {
            Callback<List<DeliveryItem>> call = invocation.getArgument(0);
            throwable = new Throwable("Bad Request");
            call.onFailure(deliveryItemList, throwable);
            return null;
        }).when(deliveryItemList).enqueue(Mockito.any());
        Mockito.when(deliveryApi.getAllDeliveryItems(0, AppConstants.PAGE_LIMIT)).thenReturn(deliveryItemList);
        Mockito.when(deliveryService.getDeliveryApi()).thenReturn(deliveryApi);
        deliveryViewModel.loadDeliveriesNetwork(0);

        TestObserver.test(deliveryViewModel.getDeliveryList()).awaitValue().
                map(Result::getError).
                assertValue(throwable.getMessage());

    }
}
