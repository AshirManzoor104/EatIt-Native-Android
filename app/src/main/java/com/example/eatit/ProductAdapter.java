package com.example.eatit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.productHolder> {
    private ArrayList<Product>dataset;
    private AdapterView.OnItemClickListener onItemClickListener;

    public ProductAdapter(ArrayList<Product> dataset, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_product,parent,false);
        return new productHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final productHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getProductImage()).into(holder.ivproductimage);
        holder.tvproductprice.setText(String.valueOf(dataset.get(position).getProductPrice()));
        holder.tvproductname.setText(dataset.get(position).getProductName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(null,holder.itemView,holder.getAdapterPosition(),0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class productHolder extends RecyclerView.ViewHolder{
        ImageView ivproductimage;
        TextView tvproductname;
        TextView tvproductprice;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            ivproductimage=itemView.findViewById(R.id.ivProductImage);
            tvproductname=itemView.findViewById(R.id.tvProductName);
            tvproductprice=itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
