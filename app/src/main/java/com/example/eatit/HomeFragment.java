package com.example.eatit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SliderLayout sliderLayout;
    private ProgressBar pbCategory;
    private RecyclerView rvCategory;
    private Context mContext;
    private ArrayList<slideItem> slidesList;
    private ArrayList<Category> categoryList;
    private CategoryHomeAdapter categoryHomeAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sliderLayout=view.findViewById(R.id.slider);
        rvCategory=view.findViewById(R.id.rvCaegories);
        pbCategory=view.findViewById(R.id.progressBarHome);
        mContext=getContext();

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        fetchdatafromServer();

    }

    private void fetchdatafromServer() {
        pbCategory.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(ApiConfig.HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pbCategory.setVisibility(View.GONE);
                try {
                    JSONObject jsonResponse=new JSONObject(response);
                    JSONArray slidesArray=jsonResponse.getJSONArray("Slides");
                    JSONArray categoriesArray=jsonResponse.getJSONArray("Categories");
                    Gson gson=new Gson();
                    Type slideListtype=new TypeToken<ArrayList<slideItem>>(){}.getType();
                    Type categoryListType=new TypeToken<ArrayList<Category>>(){}.getType();

                    slidesList=gson.fromJson(slidesArray.toString(),slideListtype);
                    categoryList=gson.fromJson(categoriesArray.toString(),categoryListType);
                    for (slideItem slideItem: slidesList ){
                        TextSliderView sliderView=new TextSliderView(mContext);
                        sliderView.setPicasso(Picasso.with(mContext));
                        sliderView.description(slideItem.getSlideCaption());
                        sliderView.image(slideItem.getSlideImage());
                        sliderLayout.addSlider(sliderView);
                    }
                   categoryHomeAdapter=new CategoryHomeAdapter(categoryList, new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           Intent intent=new Intent(mContext,ProductActivity.class);
                           intent.putExtra("category",categoryList.get(i));
                           startActivity(intent);
                       }
                   });
                    rvCategory.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                     rvCategory.setAdapter(categoryHomeAdapter);
                } catch (JSONException e) {


                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i("mytag",error+"");
                pbCategory.setVisibility(View.GONE);
                Toast.makeText(mContext, "Unable to fetch data from server", Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(mContext.getApplicationContext()).add(request);

    }

    @Override
    public void onPause() {
        super.onPause();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderLayout.startAutoCycle();
    }
}
