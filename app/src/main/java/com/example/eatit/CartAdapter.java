package com.example.eatit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private ArrayList<CartItem>dataset;
    private AdapterView.OnItemClickListener removeOnItemClickListener;
    private AdapterView.OnItemClickListener decrementOnItemClickListener;
    private AdapterView.OnItemClickListener incrementOnItemClickListener;

    public CartAdapter(ArrayList<CartItem> dataset, AdapterView.OnItemClickListener removeOnItemClickListener, AdapterView.OnItemClickListener decrementOnItemClickListener, AdapterView.OnItemClickListener incrementOnItemClickListener) {
        this.dataset = dataset;
        this.removeOnItemClickListener = removeOnItemClickListener;
        this.decrementOnItemClickListener = decrementOnItemClickListener;
        this.incrementOnItemClickListener = incrementOnItemClickListener;
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_cart,parent,false);

        return new CartHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, int position) {

        Picasso.with(holder.itemView.getContext()).load(dataset.get(position).getProduct().getProductImage()).into(holder.ivProductImage);
        holder.tvProductName.setText(dataset.get(position).getProduct().getProductName());
        holder.tvProductDiscount.setText("Discount: Rs."+dataset.get(position).getProduct().getProductDiscount());
        int price=dataset.get(position).getProduct().getProductPrice();
        int discount=dataset.get(position).getProduct().getProductDiscount();
        int quantity=dataset.get(position).getQuantity();
        int amount=(price-discount)*quantity;
        holder.tvAmount.setText("Rs."+amount);
        holder.tvQuantity.setText(String.valueOf(quantity));

        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeOnItemClickListener.onItemClick(null,holder.tvRemove,holder.getAdapterPosition(),0);
            }
        });
        holder.ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementOnItemClickListener.onItemClick(null,holder.ibRemove,holder.getAdapterPosition(),0);
            }
        });
        holder.ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementOnItemClickListener.onItemClick(null,holder.ibAdd,holder.getAdapterPosition(),0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder{
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductDiscount;
        TextView tvRemove;
        TextView tvAmount;
        ImageButton ibAdd;
        ImageButton ibRemove;
        TextView tvQuantity;


        public CartHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage=itemView.findViewById(R.id.ivProductImage);
            tvProductName=itemView.findViewById(R.id.tvPoductName);
            tvProductDiscount=itemView.findViewById(R.id.tvProuctDiscount);
            tvRemove=itemView.findViewById(R.id.tvRemove);
            tvAmount=itemView.findViewById(R.id.tvProductAmount);
            ibAdd=itemView.findViewById(R.id.ibAdd);
            ibRemove=itemView.findViewById(R.id.ibRemove);
            tvQuantity=itemView.findViewById(R.id.tvQuantity);
        }
    }
}
