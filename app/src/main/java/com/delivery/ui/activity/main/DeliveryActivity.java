package com.delivery.ui.activity.main;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.delivery.R;

import com.delivery.data.OnLoadMoreListener;
import com.delivery.model.DeliveryItemResponseModel;

import com.delivery.model.Result;
import com.delivery.data.network.services.ConnectionLiveData;
import com.delivery.ui.activity.details.LocationActivity;
import com.delivery.utils.AppConstants;
import com.google.android.material.snackbar.Snackbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DeliveryActivity extends AppCompatActivity implements DeliverAdapter.OnDeliveryAdapter, OnLoadMoreListener {


    private DeliverAdapter deliverAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.emptyView)
    FrameLayout emptyView;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    private DeliveryViewModel viewModel;


    private DeliveryViewModel createViewModel() {

        DeliveryViewModelFactory factory = new DeliveryViewModelFactory();
        return ViewModelProviders.of(this, factory).get(DeliveryViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        deliverAdapter = new DeliverAdapter(this, recyclerView, DeliveryActivity.this);
        recyclerView.setAdapter(deliverAdapter);
        viewModel = createViewModel();
        viewModel.getLoadingStatus().observe(this, new LoadingObserver());
        viewModel.getDeliveryList().observe(this, new DeliveryItemObserver());

        loadingDataFromNetwork(0);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, connection -> {
            if (connection != null && connection.getIsConnected()) {

                deliverAdapter.setLoading();

            } else {
                Toast.makeText(DeliveryActivity.this, getResources().getString(R.string.CONNECTION_OFF), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadingDataFromNetwork(int offset) {
        viewModel.loadDeliveriesNetwork(offset);
    }


    @OnClick(R.id.btn_retry)
    void onEmptyViewButtonClick() {
        int offset = 0;

        loadingDataFromNetwork(offset);
    }

    @Override
    public void onDeliveryItemClicked(DeliveryItemResponseModel deliveryItemResponseModel) {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, deliveryItemResponseModel);
        startActivity(intent);

    }

    @Override
    public void onLoadMore(int offset) {

        viewModel.loadDeliveriesNetwork(offset);

    }


    //Observer
    private class LoadingObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            if (isLoading == null) return;
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    private class DeliveryItemObserver implements Observer<Result> {

        @Override
        public void onChanged(@Nullable Result deliveryResult) {


            if (deliveryResult!=null && deliveryResult.getStatus() == Result.STATUS.ERROR) {
                if (deliveryResult.getData() == null || deliveryResult.getData().size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    deliverAdapter.setLoading();
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main_content), deliveryResult.getError(), Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();
                }

            } else {
                if (deliveryResult!=null && deliveryResult.getData().size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    deliverAdapter.setItems(deliveryResult.getData());
                    deliverAdapter.notifyDataSetChanged();
                    deliverAdapter.setLoading();
                }
            }
        }
    }
}
