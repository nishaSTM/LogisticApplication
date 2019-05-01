package com.delivery;

import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.model.Result;
import com.delivery.data.network.services.DeliveryService;

import com.delivery.ui.activity.main.DeliveryViewModel;
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

    private DeliveryViewModel viewModel;

    @Mock
    DeliveryService deliveryService;

    @Mock
    DeliveryService.DeliveryApi deliveryApi;

    @Mock
    private Call<List<DeliveryItemResponseModel>> mockedCall;

    private Throwable throwable;

    @Before
    public void setUp() {
        viewModel = new DeliveryViewModel(deliveryService);
    }


    @Test
    public void testDeliveryViewModelResponseSuccess() {
        List<DeliveryItemResponseModel> list = new ArrayList<>();
        DeliveryItemResponseModel itemResponseModel = new DeliveryItemResponseModel("desc", "imagUrl", "id", null);
        list.add(itemResponseModel);
        Mockito.doAnswer(invocation -> {
            Callback<List<DeliveryItemResponseModel>> call = invocation.getArgument(0);
            call.onResponse(mockedCall, Response.success(list));
            return null;
        }).when(mockedCall).enqueue(Mockito.any());
        Mockito.when(deliveryApi.getAllDeliveryItems(0, AppConstants.LIMIT)).thenReturn(mockedCall);
        Mockito.when(deliveryService.getDeliveryApi()).thenReturn(deliveryApi);
        viewModel.loadDeliveriesNetwork(0);
        // List<DeliveryItemResponseModel> abc=new ArrayList();
        try {
            TestObserver.test(viewModel.getDeliveryList()).awaitValue().assertHasValue().
                    map(Result::getData)
                    .assertValue(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeliveryViewModelResponseFailure() {
        Mockito.doAnswer(invocation -> {
            Callback<List<DeliveryItemResponseModel>> call = invocation.getArgument(0);
            throwable = new Throwable("Bad Request");
            call.onFailure(mockedCall, throwable);
            return null;
        }).when(mockedCall).enqueue(Mockito.any());
        Mockito.when(deliveryApi.getAllDeliveryItems(0, AppConstants.LIMIT)).thenReturn(mockedCall);
        Mockito.when(deliveryService.getDeliveryApi()).thenReturn(deliveryApi);
        viewModel.loadDeliveriesNetwork(0);
        try {
            TestObserver.test(viewModel.getDeliveryList()).awaitValue().
                    map(Result::getError).
                    assertValue(throwable.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
