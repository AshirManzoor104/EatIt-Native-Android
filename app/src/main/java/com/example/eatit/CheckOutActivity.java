package com.example.eatit;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {
    private TextInputLayout tilAddress;
    private EditText etAddress;
    private Spinner spCity;
    private TextInputLayout tilContact;
    private EditText etContact;
    private RadioButton rdoCashOnDelivery;
    private RelativeLayout submitOrderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_check_out);
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

        tilAddress=findViewById(R.id.til_address);
        etAddress=findViewById(R.id.et_adddress);
        spCity=findViewById(R.id.sp_cities);
        tilContact=findViewById(R.id.til_contact_number);
        etContact=findViewById(R.id.et_contact_number);
        rdoCashOnDelivery=findViewById(R.id.rdoCash);
        submitOrderLayout=findViewById(R.id.submit_order_layout);

        submitOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String address=etAddress.getText().toString().trim();
                final String city=spCity.getSelectedItem().toString();

                if (spCity.getSelectedItemPosition()==0) {
                    Toast.makeText(CheckOutActivity.this, "Please Select the city", Toast.LENGTH_SHORT).show();
                }

                final String contactNumber=etContact.getText().toString().trim();
                String paymentMethod="";
                if (rdoCashOnDelivery.isChecked()){
                    paymentMethod="COD";
                }
                final User userObject=SessionHelper.getCurrentUser(CheckOutActivity.this);
                final int userId=userObject.getUserId();
                final CartHelper cartHelper=new CartHelper(CheckOutActivity.this);
                final ArrayList<CartItem>cartList=cartHelper.getAllCartItems();
                final ProgressDialog progressDialog=new ProgressDialog(CheckOutActivity.this);
                progressDialog.setMessage("Please Wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
                final String finalPaymentMethod = paymentMethod;
                StringRequest request=new StringRequest(Request.Method.POST, ApiConfig.SAVE_ORDER_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            if(status ==  1) {
                                Toast.makeText(CheckOutActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CheckOutActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.i("mytag",response);
                            e.printStackTrace();
                            Log.i("mytag",e.getMessage());
                            Toast.makeText(CheckOutActivity.this, "unfortunate error", Toast.LENGTH_SHORT).show();
                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        progressDialog.dismiss();
                        Toast.makeText(CheckOutActivity.this, "CheckOut Activity error", Toast.LENGTH_SHORT).show();


                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params=new HashMap<>();
                        params.put("user_id", String.valueOf(userId));
                        params.put("delivery_address",address);
                        params.put("city",city);
                        params.put("contact_number",contactNumber);
                        params.put("payment_method", finalPaymentMethod);
                        JSONArray cartArray=new JSONArray();
                        for (int i=0;i<cartList.size();i++){
                            CartItem cItem=cartList.get(i);
                            JSONObject cartObject=new JSONObject();
                            try {
                                cartObject.put("p_id",cItem.getProduct().getProductId());
                                cartObject.put("Price",cItem.getProduct().getProductPrice());
                                cartObject.put("quantity",cItem.getQuantity());
                                cartArray.put(cartObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("mytag",cartArray.toString());
                        params.put("cartList",cartArray.toString());
                        params.put("total_amount", String.valueOf(cartHelper.getTotalBillOfCart()));


                        return params;

                    }
                };
                 Volley.newRequestQueue(getApplicationContext()).add(request);
            }
        });


    }

}
