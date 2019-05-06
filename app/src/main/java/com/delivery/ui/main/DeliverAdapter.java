package com.delivery.ui.main;


import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.delivery.R;
import com.delivery.databinding.ItemDeliveryBinding;
import com.delivery.listener.DeliveryItemClickListener;
import com.delivery.model.DeliveryItem;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.ViewHolder> {
    private List<DeliveryItem> deliveryItemList;
    private final DeliveryItemClickListener deliveryItemClickListener;

    public DeliverAdapter(DeliveryItemClickListener deliveryItemClickListener) {
        this.deliveryItemClickListener = deliveryItemClickListener;
        deliveryItemList = new ArrayList<>();
    }

    public void setItems(List<DeliveryItem> items) {
        deliveryItemList = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemDeliveryBinding itemDeliveryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_delivery,
                parent, false);
        return new ViewHolder(itemDeliveryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindDeliveryItem(getItem(position));
        holder.itemDeliveryBinding.parentView.setOnClickListener(v -> deliveryItemClickListener.onDeliveryItemClick(getItem(position)));
    }

    @Override
    public int getItemCount() {
        return deliveryItemList.size();
    }

    private DeliveryItem getItem(int position) {
        return deliveryItemList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemDeliveryBinding itemDeliveryBinding;

        private ViewHolder(ItemDeliveryBinding itemDeliveryBinding) {
            super(itemDeliveryBinding.getRoot());
            this.itemDeliveryBinding = itemDeliveryBinding;
        }

        void bindDeliveryItem(DeliveryItem deliveryItem) {

            if (itemDeliveryBinding.getDeliveryItemViewModel() == null) {
                itemDeliveryBinding.setDeliveryItemViewModel(deliveryItemClickListener.getDeliveryItemViewModel());
            }
            itemDeliveryBinding.getDeliveryItemViewModel().setDeliveryItem(deliveryItem);
            itemDeliveryBinding.executePendingBindings();
        }
    }
}
