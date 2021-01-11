package com.example.eatit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout etFullName;
    private TextInputEditText tilFullName;
    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private TextInputLayout tilPassword;
    private TextInputEditText etPassword;
    private Button btnSignUp;
    private TextView tvLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_sign_up);


        etFullName=findViewById(R.id.tilfullName);
        tilFullName=findViewById(R.id.etfullName);
        tilEmail=findViewById(R.id.tilEmail);
        etEmail=findViewById(R.id.etEmail);
        tilPassword=findViewById(R.id.tilPassword);
        etPassword=findViewById(R.id.etPassword);
        btnSignUp=findViewById(R.id.btnSignUp);
        tvLogIn=findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etFullName.setErrorEnabled(false);
                tilEmail.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);

                String fullName=tilFullName.getText().toString();
                String email=etEmail.getText().toString().trim();
                String password=etPassword.getText().toString();
                if (fullName.isEmpty()){
                    etFullName.setError("Name is Required");
                    etFullName.setErrorEnabled(true);

                }

               else if (email.isEmpty()){
                    tilEmail.setError("Email is required");
                    tilEmail.setErrorEnabled(true);
                }else if(password.isEmpty()) {
                    tilPassword.setErrorEnabled(true);
                    tilPassword.setError("Password is requied");

                }else if (password.length()<4){
                    tilPassword.setError("Password must be atleast 4 characters");
                    tilPassword.setErrorEnabled(true);
                }else {
                    signUp(fullName,email,password);
                }
            }


        });
        tvLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,SigninActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUp(final String fullName, final String email, final String password) {
        final ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, ApiConfig.SIGN_UP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonRespponse=new JSONObject(response);

                    int status=jsonRespponse.getInt("status");
                    String message=jsonRespponse.getString("message");
                    if (status==0){
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                        JSONObject usrObject=jsonRespponse.getJSONObject("user");
                        Gson gson=new Gson();
                        User user=gson.fromJson(usrObject.toString(),User.class);
                        SessionHelper.createUserSession(SignUpActivity.this,user);
                        finish();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("mytag",response);
                    Log.i("mytag",e.getMessage());
                    Toast.makeText(SignUpActivity.this, "unable to process data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(SignUpActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("fullName",fullName);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);


    }
    }

