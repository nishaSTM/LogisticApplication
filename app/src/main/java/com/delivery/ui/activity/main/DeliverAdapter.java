package com.delivery.ui.activity.main;


import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delivery.R;
import com.delivery.model.DeliveryItemResponseModel;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class DeliverAdapter extends RecyclerView.Adapter<DeliverAdapter.ViewHolder> {
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleThreshold;
    private boolean loading = false;
    private List<DeliveryItemResponseModel> mItems;
    private final OnDeliveryAdapter mListener;
    public interface OnDeliveryAdapter {
        void onDeliveryItemClicked(DeliveryItemResponseModel deliveryItemResponseModel);
    }


    public DeliverAdapter(OnDeliveryAdapter listener, RecyclerView currentRecyclerView, final DeliveryActivity context) {
        mListener = listener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryItemResponseModel deliveryItemResponseModel = getItem(position);
        holder.setOnClickListener(deliveryItemResponseModel);
        holder.setImage(deliveryItemResponseModel.getImage());
        holder.setDescription(deliveryItemResponseModel.getDescription() + " " + deliveryItemResponseModel.getLocation().getAddress());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private DeliveryItemResponseModel getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.desc)
        TextView desc;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        private void setImage(String imageUrl) {
            Glide.with(itemView.getContext()).load(imageUrl).into(image);
        }

        private void setDescription(String description) {
            desc.setText(description);
        }

        private void setOnClickListener(DeliveryItemResponseModel deliveryItemResponseModel) {
            itemView.setTag(deliveryItemResponseModel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onDeliveryItemClicked((DeliveryItemResponseModel) view.getTag());
        }
    }
}
