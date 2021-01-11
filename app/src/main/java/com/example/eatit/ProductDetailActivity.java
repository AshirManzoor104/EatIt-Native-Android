package com.example.eatit;

import android.os.Build;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProductDetailActivity extends AppCompatActivity {
    private  Product selectedproduct;
    private PhotoView ivProductImage;
    private TextView tvProductName;
    private TextView tvProductPrice;
    private TextView tvProductDescription;
    private ImageButton ibremove;
    private ImageButton ibadd;
    private TextView tvquantity;
    private Button btnAddCart;
    private CartHelper cartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedproduct= (Product) getIntent().getSerializableExtra("product");
       if( Build.VERSION.SDK_INT>=21){
           getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
       }

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
       ibremove=findViewById(R.id.ibRemove);
       ibadd=findViewById(R.id.ibAdd);
       tvquantity=findViewById(R.id.tvQuantity);
       btnAddCart=findViewById(R.id.btnAddToCart);
       cartHelper=new CartHelper(ProductDetailActivity.this);

       ivProductImage=findViewById(R.id.ivProductImage);
       tvProductName=findViewById(R.id.tvProductPrice);
       tvProductPrice=findViewById(R.id.tvProductPrice);
       tvProductDescription=findViewById(R.id.tvProductDesuription);
        Picasso.with(getApplicationContext()).load(selectedproduct.getProductImage()) .into(ivProductImage);
        tvProductName.setText(selectedproduct.getProductName());
        tvProductDescription.setText(selectedproduct.getProductDescription());
        NumberFormat nf=new DecimalFormat("#,###");

        tvProductPrice.setText("Rs."+ nf.format(selectedproduct.getProductPrice()));

        ibadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldQty=Integer.parseInt(tvquantity.getText().toString());
                if (oldQty<10) {
                    int newQty = oldQty + 1;
                    tvquantity.setText(newQty+"");
                }
            }
        });
        ibremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldQty=Integer.parseInt(tvquantity.getText().toString());
                if (oldQty>1) {
                    int newQty = oldQty - 1;
                    tvquantity.setText(String.valueOf(newQty));
                }
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity=Integer.parseInt(tvquantity.getText().toString());
               long cartId= cartHelper.addItemToCart(selectedproduct,quantity);

               if (cartId==-1){
                   Toast.makeText(ProductDetailActivity.this, "unable to add product in cart", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(ProductDetailActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
               }
            //    cartHelper.addItemToCart()
            }
        });


    }

}
