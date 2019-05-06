package com.delivery.ui.adapter;


import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.delivery.R;
import com.delivery.databinding.ItemDeliveryBinding;
import com.delivery.listener.DeliveryItemListener;
import com.delivery.model.DeliveryItem;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.ViewHolder> {
    private List<DeliveryItem> deliveryItemList;
    private final DeliveryItemListener deliveryItemListener;
    private LayoutInflater layoutInflater;

    public DeliverAdapter(DeliveryItemListener deliveryItemListener) {
        this.deliveryItemListener = deliveryItemListener;
        deliveryItemList = new ArrayList<>();
    }

    public void setItems(List<DeliveryItem> items) {
        deliveryItemList = items;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        final ItemDeliveryBinding itemDeliveryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_delivery, parent, false);
        return new ViewHolder(itemDeliveryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindDeliveryItem(getItem(position));
        holder.itemDeliveryBinding.parentView.setOnClickListener(v -> deliveryItemListener.onDeliveryItemClick(getItem(position)));
    }

    @Override
    public int getItemCount() {
        return deliveryItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
                itemDeliveryBinding.setDeliveryItemViewModel(deliveryItemListener.getDeliveryItemViewModel());
            }
            itemDeliveryBinding.getDeliveryItemViewModel().setDeliveryItem(deliveryItem);
            itemDeliveryBinding.executePendingBindings();
        }
    }
}
