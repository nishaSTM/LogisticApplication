package com.delivery.ui.activity.main;


import android.view.View;
import androidx.annotation.NonNull;
import com.delivery.data.DataManager;
import com.delivery.model.DeliveryItemResponseModel;
import com.delivery.model.Result;
import com.delivery.data.network.services.DeliveryService;
import com.delivery.utils.AppConstants;
import java.util.ArrayList;
import java.util.List;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryViewModel extends ViewModel {

    private final MutableLiveData<Result> deliveryResult;
    private final MutableLiveData<Boolean> isLoading;
    private final DeliveryService deliveryService;
    public final ObservableInt progressBar;
    public final ObservableInt emptyView;

    private final List<DeliveryItemResponseModel> deliveryItemArrayList;

    public DeliveryViewModel() {
        this.deliveryService = DataManager.getInstance().getDeliveryService();
        deliveryResult = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        deliveryItemArrayList = new ArrayList<>();
        progressBar=new ObservableInt();
        emptyView=new ObservableInt();
    }

    public MutableLiveData<Result> getDeliveryList() {
        return deliveryResult;
    }

    MutableLiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    public void loadDeliveriesNetwork(int offset) {
        setIsLoading(true);
        progressBar.set(View.VISIBLE);
        Call<List<DeliveryItemResponseModel>> deliveryItemCall = deliveryService.getDeliveryApi().
                getAllDeliveryItems(offset, AppConstants.LIMIT);
        deliveryItemCall.enqueue(new DeliveryCallback());

    }

    public void onClickRetryButton(View view) {

        int offset=0;
        loadDeliveriesNetwork(offset);
    }
    private void setIsLoading(boolean loading) {
        isLoading.postValue(loading);
    }


    private void setDeliveries(Result deliveryItems) {
        setIsLoading(false);
        progressBar.set(View.GONE);
        deliveryResult.postValue(deliveryItems);
    }


    public MutableLiveData<Boolean> isLoading() {
        return isLoading;
    }

    /**
     * Callback
     **/
    private class DeliveryCallback implements Callback<List<DeliveryItemResponseModel>> {

        @Override
        public void onResponse(@NonNull Call<List<DeliveryItemResponseModel>> call, @NonNull Response<List<DeliveryItemResponseModel>> response) {
            List<DeliveryItemResponseModel> deliveryItemResponse = response.body();
            if (deliveryItemResponse == null) {
                Result result=new Result(Result.STATUS.ERROR,deliveryItemArrayList,response.message());
                setDeliveries(result);
            } else {
                deliveryItemArrayList.addAll(deliveryItemResponse);
                Result result=new Result(Result.STATUS.SUCCESS,deliveryItemArrayList,null);
                setDeliveries(result);

            }

        }

        @Override

        public void onFailure(@NonNull Call<List<DeliveryItemResponseModel>> call, @NonNull Throwable t) {

            Result result=new Result(Result.STATUS.ERROR,deliveryItemArrayList,t.getMessage());
            setDeliveries(result);
        }
    }
}
