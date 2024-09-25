package com.example.tastytray;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {//for binding product data

    private List<Product> productList;
    private OnItemClickListener listener;//Listener for itemclick

    public interface OnItemClickListener {//Interface to define itemclick listener
        void onItemClick(int position);
    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);//Inflate the sample layout for viewproduct activity
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {//using models setter geter to bind data
        Product product = productList.get(position);
        holder.bind(product, listener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {//After creating every new adapter, must be create a view holder class for hold sample layout data/refferece

        TextView tvProductName, tvProductPrice, tvProductQuantity;
        ImageView ivProductPhoto;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            ivProductPhoto = itemView.findViewById(R.id.ivProductPhoto);
        }

        public void bind(final Product product, final OnItemClickListener listener) {
            tvProductName.setText(product.getName());
            tvProductPrice.setText(String.valueOf(product.getPrice()));
            tvProductQuantity.setText(String.valueOf(product.getQuantity()));

            if (product.getPhoto() != null && product.getPhoto().length > 0) { //Set photo if exists, otherwise default image
                ivProductPhoto.setImageBitmap(BitmapFactory.decodeByteArray(product.getPhoto(), 0, product.getPhoto().length));
            } else {
                // Set a default image or handle case where photo is null or empty
                ivProductPhoto.setImageResource(R.drawable.burgur);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());//use this in home activity
                    }
                }
            });
        }
    }
}
