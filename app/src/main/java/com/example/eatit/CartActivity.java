package com.example.eatit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private LinearLayout errorLayout;
    private RecyclerView rvCart;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem>cartItemList;
    private CartHelper cartHelper;
    private RelativeLayout rlcheckoutLayout;
    private TextView tvCartTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       cartHelper=new CartHelper(CartActivity.this);
       rvCart=findViewById(R.id.rvCart);
       errorLayout=findViewById(R.id.errorLayout);
       rlcheckoutLayout=findViewById(R.id.checkoutLayout);
       tvCartTotal=findViewById(R.id.tvCartTotal);
       cartItemList=cartHelper.getAllCartItems();
       displayCartTotal();
       if (cartItemList.size()==0){
           errorLayout.setVisibility(View.VISIBLE);
       }
       cartAdapter=new CartAdapter(cartItemList, new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               CartItem selectedCartItem=cartItemList.get(i);
              int numberOfDeletedRows= cartHelper.deleteCartItem(selectedCartItem.getCartId());

              if (numberOfDeletedRows==0){
                  Toast.makeText(CartActivity.this, "unable to remove records", Toast.LENGTH_SHORT).show();

              }else {
                  ArrayList<CartItem> tempList=cartHelper.getAllCartItems();
                  cartItemList.clear();
                  cartItemList.addAll(tempList);
                  cartAdapter.notifyItemRemoved(i);
                  if (cartItemList.size()==0){
                      errorLayout.setVisibility(View.VISIBLE);
                  }
              }
              displayCartTotal();
           }
       }, new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               int oldQty=cartItemList.get(i).getQuantity();
               if (oldQty>1){
                   int newQty=oldQty-1;

                   cartHelper.updateItemInCart(cartItemList.get(i).getCartId(),newQty);
                   ArrayList<CartItem> templist=cartHelper.getAllCartItems();
                   cartItemList.clear();
                   cartItemList.addAll(templist);
                   cartAdapter.notifyItemChanged(i);
                   displayCartTotal();
               }
           }
       }, new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               int oldQty=cartItemList.get(i).getQuantity();
               if (oldQty<10){
                   int newQty=oldQty+1;

                   cartHelper.updateItemInCart(cartItemList.get(i).getCartId(),newQty);
                   ArrayList<CartItem> templist=cartHelper.getAllCartItems();
                   cartItemList.clear();
                   cartItemList.addAll(templist);
                   cartAdapter.notifyItemChanged(i);
                   displayCartTotal();
               }
           }
       });
       rvCart.setLayoutManager(new LinearLayoutManager(CartActivity.this,RecyclerView.VERTICAL,false));

       rvCart.setAdapter(cartAdapter);

       rlcheckoutLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (!SessionHelper.isUserLoggedIn(CartActivity.this)){
                   Intent intent=new Intent(CartActivity.this,SigninActivity.class);
                   intent.putExtra("destination","finish");
                   startActivity(intent);
               }
               else {
                   Intent intent=new Intent(CartActivity.this,CheckOutActivity.class);
                   startActivity(intent);

               }


           }
       });

    }

    private  void displayCartTotal(){
        int cartTotal=cartHelper.getTotalBillOfCart();
        NumberFormat nf=new DecimalFormat("#,###");
        tvCartTotal.setText("Rs. "+nf.format(cartTotal));
    }

}
