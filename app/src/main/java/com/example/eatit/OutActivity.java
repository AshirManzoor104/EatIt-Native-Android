package com.example.eatit;

import android.database.sqlite.SQLiteDatabase;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.jar.JarEntry;

public class OutActivity extends AppCompatActivity {


    private CartHelper cartHelper;
    private LinearLayout errorLayout;
    private TextView tvCancelorder;
    private RecyclerView rvOrder;
    private orderAdapter orderAdapter;
    private ArrayList<OrderDetail> orderDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Order");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        Bundle bundle=getIntent().getBundleExtra("id");
        bundle=getIntent().getBundleExtra("price");


        cartHelper=new CartHelper(OutActivity.this);

        rvOrder=findViewById(R.id.rvOrder);
        errorLayout=findViewById(R.id.errorLayout);
        fetchorderdatafromserver();
    }

    private void fetchorderdatafromserver() {
        StringRequest stringRequest=new StringRequest(ApiConfig.SHOW_ORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("Slides");
                    Gson gson=new Gson();
                    Type type= new TypeToken<ArrayList<OrderDetail>>(){}.getType();
                    orderDetailList=gson.fromJson(jsonArray.toString(),type);
                    orderAdapter=new orderAdapter(orderDetailList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           /* OrderDetail selectedCartItem=orderDetailList.get(i);
                            int numberOfDeletedRows=cartHelper .deleteOrderItem(selectedCartItem.getOrderId(),selectedCartItem.getTotalAmount());
                            if (numberOfDeletedRows==0){
                                Toast.makeText(OutActivity.this, "unable to remove records", Toast.LENGTH_SHORT).show();

                            }else {

                                orderDetailList.clear();
                              //  orderDetailList.addAll(tempList);
                                orderAdapter.notifyItemRemoved(i);
                                if (orderDetailList.size()==0){
                                    errorLayout.setVisibility(View.VISIBLE);
                                }
                            }*/

                        }
                    });
                    rvOrder.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

                    rvOrder.setAdapter(orderAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(OutActivity.this, "show order error", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(OutActivity.this, "Error Order", Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }




}
