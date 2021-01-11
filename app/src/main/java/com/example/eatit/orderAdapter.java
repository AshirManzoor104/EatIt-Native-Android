package com.example.eatit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.orderHolder>  {
    private ArrayList<OrderDetail>dataset;
    private AdapterView.OnItemClickListener onItemClickListener;

    public orderAdapter(ArrayList<OrderDetail> dataset, AdapterView.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
      //  View namebar = parent.findViewById(R.id.tvCancelOrder);
      //  ((ViewGroup) namebar.getParent()).removeView(namebar);
        return new orderHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final orderHolder holder, int position) {
       // holder.tvproductprice.setText(String.valueOf(dataset.get(position).getProductPrice()));
        holder.tvOrderid.setText(String.valueOf("Order Id: "+dataset.get(position).getOrderId()));
        holder.tvOrderprice.setText(String.valueOf("Total Price: "+dataset.get(position).getTotalAmount()));




    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class orderHolder extends RecyclerView.ViewHolder {
         TextView tvOrderid;
         TextView tvOrderprice;

        public orderHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderid=itemView.findViewById(R.id.tvOrderId);
            tvOrderprice=itemView.findViewById(R.id.tvOrderPrice);

        }
    }
}
