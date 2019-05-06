package com.delivery.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.delivery.R;
import com.delivery.databinding.ActivityMainBinding;
import com.delivery.listener.DeliveryItemListener;
import com.delivery.model.DeliveryItem;
import com.delivery.model.Result;
import com.delivery.ui.adapter.DeliverAdapter;
import com.delivery.utils.AppConstants;
import com.delivery.viewmodel.DeliveryItemViewModel;
import com.delivery.viewmodel.DeliveryViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryActivity extends AppCompatActivity implements DeliveryItemListener {


    private DeliverAdapter deliverAdapter;
    private DeliveryViewModel deliveryViewModel;
    private ActivityMainBinding activityBinding;
    private RecyclerView recyclerView;
    private boolean loading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDeliveryViewModelBindings();
        setUpAdapter();
        deliveryViewModel.getDeliveryList().observe(this, new DeliveryItemObserver());
        onLoadMore(0);
    }

    private void setupDeliveryViewModelBindings() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        deliveryViewModel = ViewModelProviders.of(this).get(DeliveryViewModel.class);
        activityBinding.setDeliveryViewModel(deliveryViewModel);
    }


    private void setUpAdapter() {
        recyclerView = activityBinding.recyclerView;
        deliverAdapter = new DeliverAdapter(this);
        recyclerView.setAdapter(deliverAdapter);
        setScrollListener();

    }

    private void setScrollListener() {
        int visibleThreshold = 2;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager != null ? linearLayoutManager.getItemCount() : 0;
                int lastVisibleItem = linearLayoutManager != null ? linearLayoutManager.findLastVisibleItemPosition() : 0;

                if ((!loading && totalItemCount <= (lastVisibleItem + visibleThreshold))) {
                    loading = true;
                    onLoadMore(totalItemCount);
                }
            }
        });


    }


    private void setLoading() {
        loading = false;
    }

    private void onLoadMore(int offset) {
        deliveryViewModel.loadDeliveriesNetwork(offset);
    }

    @Override
    public void onDeliveryItemClick(DeliveryItem deliveryItem) {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, deliveryItem);
        startActivity(intent);
    }

    @Override
    public DeliveryItemViewModel getDeliveryItemViewModel() {
        return ViewModelProviders.of(this).get(DeliveryItemViewModel.class);
    }


    private class DeliveryItemObserver implements Observer<Result> {

        @Override
        public void onChanged(@Nullable Result deliveryResult) {


            if (deliveryResult != null && deliveryResult.getStatus() == Result.STATUS.ERROR) {
                if (deliveryResult.getData() == null || deliveryResult.getData().size() == 0) {
                    deliveryViewModel.emptyView.set(View.VISIBLE);
                } else {
                    deliveryViewModel.emptyView.set(View.GONE);
                    setLoading();
                    Snackbar snackbar;
                    if (deliveryResult.getError().contains(AppConstants.UNSATISFIABLE_REQUEST_IF_CACHED)) {
                        snackbar = Snackbar.make(findViewById(R.id.main_content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_LONG);
                    } else {
                        snackbar = Snackbar.make(findViewById(R.id.main_content), deliveryResult.getError(), Snackbar.LENGTH_LONG);
                    }

                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(Color.RED);
                    snackbar.show();
                }

            } else {
                if (deliveryResult != null && deliveryResult.getData() != null && deliveryResult.getData().size() == 0) {
                    deliveryViewModel.emptyView.set(View.VISIBLE);
                } else {
                    deliveryViewModel.emptyView.set(View.GONE);
                    if (deliveryResult != null)
                        deliverAdapter.setItems(deliveryResult.getData());
                    deliverAdapter.notifyDataSetChanged();
                    setLoading();
                }
            }
        }
    }
}
