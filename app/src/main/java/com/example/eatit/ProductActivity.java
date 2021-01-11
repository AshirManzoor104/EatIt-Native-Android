package com.example.eatit;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private ProgressBar pbproduct;
    private RecyclerView rvproduct;
    private ArrayList<Product> productList;
    private ProductAdapter productAdapter;
    private  Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        setContentView(R.layout.activity_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pbproduct=findViewById(R.id.pbProduct);
        rvproduct=findViewById(R.id.rvProduct);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         selectedCategory= (Category) getIntent().getSerializableExtra("category");
        setTitle(selectedCategory.getCatName());

        fetchProuctdatafromserver();


    }

    private void fetchProuctdatafromserver() {
        pbproduct.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(ApiConfig.PRODUCT_URL +"?cat_id="+selectedCategory.getCatId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pbproduct.setVisibility(View.GONE);
                try {
                    JSONObject responsObject=new JSONObject(response);
                    int status=responsObject.getInt("status");
                    if (status==0){
                        String message=responsObject.getString("message");
                        Toast.makeText(ProductActivity.this, message, Toast.LENGTH_SHORT).show();
                    }else {
                        final JSONArray productArray=responsObject.getJSONArray("products");
                        Gson gson=new Gson() ;
                        Type productListType=new TypeToken<ArrayList<Product>>(){}.getType();
                        productList=gson.fromJson(productArray.toString(),productListType);

                        productAdapter=new ProductAdapter(productList, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Intent intent=new Intent(ProductActivity.this,ProductDetailActivity.class);
                                intent.putExtra("product",productList.get(i));
                                startActivity(intent);
                            }
                        });

                        rvproduct.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

                        rvproduct.setAdapter(productAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProductActivity.this, "Parsing Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pbproduct.setVisibility(View.GONE);
                Toast.makeText(ProductActivity.this, "Volley Error", Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

}
