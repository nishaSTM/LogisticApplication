package com.delivery.ui.activity.main;


import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import com.delivery.R;
import com.delivery.data.OnLoadMoreListener;
import com.delivery.databinding.ActivityMainBinding;
import com.delivery.model.Result;
import com.delivery.utils.AppConstants;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryActivity extends AppCompatActivity implements OnLoadMoreListener {


    private DeliverAdapter deliverAdapter;
    private DeliveryViewModel viewModel;
    private ActivityMainBinding activityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBindings();
        setUpAdapter();
       // viewModel.getLoadingStatus().observe(this,new LoadingStatusObserver());
        viewModel.getDeliveryList().observe(this, new DeliveryItemObserver());
        loadingDataFromNetwork(0);
    }

    private void setupBindings() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel=new DeliveryViewModel();
        activityBinding.setDeliveryViewModel(viewModel);
    }

    private void setUpAdapter() {
        RecyclerView recyclerView = activityBinding.recyclerView;
        deliverAdapter = new DeliverAdapter(recyclerView, DeliveryActivity.this);
        recyclerView.setAdapter(deliverAdapter);
    }

    private void loadingDataFromNetwork(int offset) {
        viewModel.loadDeliveriesNetwork(offset);
    }


    @Override
    public void onLoadMore(int offset) {

        viewModel.loadDeliveriesNetwork(offset);

    }

   /* private class LoadingStatusObserver implements Observer<Boolean> {

        @Override
        public void onChanged(@Nullable Boolean isLoading) {
            //noinspection ConstantConditions
            if(isLoading)
          {
              viewModel.progressBar.set(View.VISIBLE);
          }
          else {
              viewModel.progressBar.set(View.GONE);
          }


        }
    }*/
    private class DeliveryItemObserver implements Observer<Result> {

        @Override
        public void onChanged(@Nullable Result deliveryResult) {


            if (deliveryResult != null && deliveryResult.getStatus() == Result.STATUS.ERROR) {
                if (deliveryResult.getData() == null || deliveryResult.getData().size() == 0) {
                     viewModel.emptyView.set(View.VISIBLE);
                } else {
                    viewModel.emptyView.set(View.GONE);
                    deliverAdapter.setLoading();
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
                if (deliveryResult != null &&  deliveryResult.getData()!=null && deliveryResult.getData().size() == 0) {
                    viewModel.emptyView.set(View.VISIBLE);
                } else {
                    viewModel.emptyView.set(View.GONE);
                    deliverAdapter.setItems(deliveryResult.getData());
                    deliverAdapter.notifyDataSetChanged();
                    deliverAdapter.setLoading();
                }
            }
        }
    }
}
