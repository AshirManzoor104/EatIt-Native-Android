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

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.CategoryHomeHolder> {
         private ArrayList<Category> dataset;
         private AdapterView.OnItemClickListener onItemClickListener;

    public CategoryHomeAdapter(ArrayList<Category> dataset, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoryHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_home_layout,parent,false);

        return new CategoryHomeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryHomeHolder holder, int position) {

        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getCatImage()).into(holder.ivCatImage);

        holder.tvCatName.setText(dataset.get(position).getCatName());
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

    public class CategoryHomeHolder extends RecyclerView.ViewHolder{
        ImageView ivCatImage;
        TextView tvCatName;

        public CategoryHomeHolder(@NonNull View itemView) {
            super(itemView);
            ivCatImage=itemView.findViewById(R.id.rivCategory);
            tvCatName=itemView.findViewById(R.id.tvCategory);
        }
    }
}
