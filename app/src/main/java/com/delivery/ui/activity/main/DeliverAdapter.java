package com.delivery.ui.activity.main;


import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.delivery.R;
import com.delivery.databinding.ItemDeliveryBinding;
import com.delivery.model.DeliveryItemResponseModel;
import java.util.ArrayList;
import java.util.List;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.ViewHolder> {
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleThreshold;
    private boolean loading = false;
    private List<DeliveryItemResponseModel> mItems;



    public DeliverAdapter(RecyclerView currentRecyclerView, final DeliveryActivity context) {
        mItems = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) currentRecyclerView.getLayoutManager();
        currentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager != null ? linearLayoutManager.getItemCount() : 0;
                lastVisibleItem = linearLayoutManager != null ? linearLayoutManager.findLastVisibleItemPosition() : 0;

                if ((!loading && totalItemCount <= (lastVisibleItem + visibleThreshold))) {
                    context.onLoadMore(totalItemCount);
                    loading = true;
                }
            }
        });

        visibleThreshold = 2;
    }

    public void setItems(List<DeliveryItemResponseModel> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void setLoading() {
        loading = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDeliveryBinding itemDeliveryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_delivery,
                        parent, false);
        return new ViewHolder(itemDeliveryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.bindDeliveryItem(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private DeliveryItemResponseModel getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ItemDeliveryBinding itemDeliveryBinding;
        private ViewHolder(ItemDeliveryBinding itemDeliveryBinding) {
            super(itemDeliveryBinding.getRoot());
            this.itemDeliveryBinding=itemDeliveryBinding;
        }

        void bindDeliveryItem(DeliveryItemResponseModel deliveryItemResponseModel) {
            if (itemDeliveryBinding.getDeliveryItemViewModel() == null) {
                itemDeliveryBinding.setDeliveryItemViewModel(
                        new DeliveryItemViewModel(deliveryItemResponseModel));
            } else {
                itemDeliveryBinding.setDeliveryItemViewModel(itemDeliveryBinding.getDeliveryItemViewModel());
            }
            itemDeliveryBinding.executePendingBindings();
        }
    }
}
