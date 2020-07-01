package com.golink.ecommerceb2b.Vendor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.golink.ecommerceb2b.Models.Category;
import com.golink.ecommerceb2b.Models.Data;
import com.golink.ecommerceb2b.Models.Products;
import com.golink.ecommerceb2b.Models.Profile;
import com.golink.ecommerceb2b.Network.GetUserData;
import com.golink.ecommerceb2b.Network.RetrofitClient;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Utils.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.MODE_PRIVATE;

public class SingleProduct extends Fragment {

    private CircleImageView blackIm, whiteIm, redIm, greenIm, blueIm;
    private TextView nameProduct, priceProduct, colorName,offerPriceProduct,offerPercentageProduct;
    private NumberPicker numberPicker;
    private String id, usertoken, feedkey, feedkeyC,vendor_id;
    private ProgressBar progressD;
    private String vendor;
    private RelativeLayout relativeLay;
   // private RecyclerView productView;
    private List<ProductItems> productItemsList = new ArrayList<>();
    private ProductAdapter productAdapter;
    private SameCategoryProductAdapter adapter;
    private ViewPager viewPager;
    StackAdapter mainAdapter;
    List<String> feedItemsListUniversal = new ArrayList<>();
    private ProgressDialog mPorgress;
    private EditText numPick;
    private Button logAdd;
    private Button logAdd2;
    private boolean check = false;
    private String checkColor;
    String userid,category_id,product_id;
    private RecyclerView passbookView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    final ArrayList<Bitmap> bitmaps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_single_product, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("");
        Bundle b = this.getArguments();

        if(b!=null){
            feedkey = b.getString("feedkey");
            vendor = b.getString("vendor");
            feedkeyC = b.getString("feedkeyC");
            checkColor = b.getString("checkColor");
            vendor_id = b.getString("vendor_id");

           // Toast.makeText(getActivity(), vendor_id, Toast.LENGTH_SHORT).show();
         //   Toast.makeText(getActivity(), feedkeyC, Toast.LENGTH_SHORT).show();

        }

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        relativeLay = view.findViewById(R.id.relativeLay);

        mPorgress = new ProgressDialog(getActivity());

        nameProduct = view.findViewById(R.id.nameProduct);
        priceProduct = view.findViewById(R.id.priceProduct);
        offerPriceProduct = view.findViewById(R.id.offerPriceProduct);
        offerPercentageProduct = view.findViewById(R.id.offerPercentageProduct);
        numberPicker = view.findViewById(R.id.number_picker);
        colorName = view.findViewById(R.id.colorName);

        numPick = view.findViewById(R.id.numPick);
        // logAdd = view.findViewById(R.id.logAdd);
        logAdd2 = view.findViewById(R.id.logAdd2);

        blackIm = view.findViewById(R.id.blackIm);
        whiteIm = view.findViewById(R.id.whiteIm);
        redIm = view.findViewById(R.id.redIm);
        greenIm = view.findViewById(R.id.greenIm);
        blueIm = view.findViewById(R.id.blueIm);

        Button logButton = view.findViewById(R.id.logButton);
        TextView vendorBtn = view.findViewById(R.id.vendorBtn);

        viewPager = (ViewPager) view.findViewById(R.id.gymImage);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabDots);

     //   productView = view.findViewById(R.id.productView);
       // productView.setHasFixedSize(true);

        passbookView = view.findViewById(R.id.singleOrderView);
        passbookView.setHasFixedSize(true);

        progressD = view.findViewById(R.id.progressD);
        progressD.setVisibility(View.VISIBLE);

        /*logAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPicker.setVisibility(View.GONE);
                numPick.setVisibility(View.VISIBLE);
                check = true;
            }
        });*/

        logAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPicker.setVisibility(View.VISIBLE);
                numPick.setVisibility(View.GONE);
                check = false;
            }
        });

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PRODUCT_DETAIl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONObject obj1 = jsonObject.getJSONObject("data");
                        final String name = obj1.getString("name");
                        final String price = obj1.getString("price");
                        final String moq = obj1.getString("moq");
                        final String color = obj1.getString("color");
                        final String offerPrice = obj1.getString("offer_price");
                        final String offerPercentage = obj1.getString("offer_percentage");

                        nameProduct.setText(name);
                        priceProduct.setText("₹ " + price);
                        offerPriceProduct.setText("₹ " + offerPrice);
                        offerPercentageProduct.setText(offerPercentage+"% OFF");
                        numberPicker.setValue(Integer.valueOf(moq));
                        numberPicker.setMin(Integer.valueOf(moq));

                        if(!color.equals("null")){
                            final List<String> colorList = Arrays.asList(color.split(","));

                            if(colorList.contains("black")){
                                blackIm.setVisibility(View.VISIBLE);
                            }
                            if(colorList.contains("white")){
                                whiteIm.setVisibility(View.VISIBLE);
                            }
                            if(colorList.contains("red")){
                                redIm.setVisibility(View.VISIBLE);
                            }
                            if(colorList.contains("green")){
                                greenIm.setVisibility(View.VISIBLE);
                            }
                            if(colorList.contains("blue")){
                                blueIm.setVisibility(View.VISIBLE);
                            }

                            if(checkColor==null){
                                checkColor = colorList.get(0);
                                colorName.setText("Selected Color : " + colorList.get(0));
                            }else{
                                colorName.setText("Selected Color : " + checkColor);
                            }

                            if(getActivity()!=null){
                                feedItemsListUniversal.clear();
                                final RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.PRODUCT_IMAGES, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {

                                            final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                            boolean error = jsonObject.getBoolean("error");

                                            if(!error){


                                                JSONArray jsonArrayCat = jsonObject.getJSONArray("data");
                                                for (int i = 0; i < jsonArrayCat.length(); i++) {

                                                    final JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);
                                                    feedItemsListUniversal.add(jsonObjectCat.getString("image"));

                                                }
                                            }
                                        }
                                        catch (JSONException e) {

                                            e.printStackTrace();
                                        }

                                        mainAdapter = new StackAdapter(feedItemsListUniversal, getActivity(), feedkey);
                                        viewPager.setAdapter(mainAdapter);
                                        tabLayout.setupWithViewPager(viewPager, true);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> paramMap = new HashMap<String, String>();

                                        paramMap.put("userid", id);
                                        paramMap.put("usertoken", usertoken);
                                        paramMap.put("product_id", feedkey);
                                        paramMap.put("color", checkColor);

                                        return paramMap;

                                    }
                                };

                                stringRequest2.setRetryPolicy(new DefaultRetryPolicy(10000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                requestQueue2.add(stringRequest2);

                            }



                        }
                        else
                        {

                            final String preview = obj1.getString("preview_image_path");

                            feedItemsListUniversal.clear();
                            feedItemsListUniversal.add(preview);
                            mainAdapter = new StackAdapter(feedItemsListUniversal, getActivity(), feedkey);
                            viewPager.setAdapter(mainAdapter);
                            tabLayout.setupWithViewPager(viewPager, true);
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                //progressD.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //progressD.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> paramMap = new HashMap<String, String>();

                paramMap.put("userid", id);
                paramMap.put("usertoken", usertoken);
                paramMap.put("product_id", feedkey);

                return paramMap;

            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);

        // other products

        GetUserData getUserData = RetrofitClient.getRetrofit().create(GetUserData.class);
        Call<Profile> userCall = getUserData.getVendorDetails(id,usertoken,vendor_id);
        userCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, retrofit2.Response<Profile> response) {
                if (response.isSuccessful()){
                    Profile user = response.body();
                    if (user.isError() == true){
                        Toast.makeText(getActivity(), user.getMessage(), Toast.LENGTH_SHORT).show();
                        progressD.setVisibility(View.GONE);
                    }
                    else if (user.isError() == false){
                        Data data = user.getData();
                        List<Category> categories = data.getCategory();
                        for (int i=0;i<categories.size();i++){
                            if (categories.get(i).getCategory_id().equals(feedkeyC)){
                              //  Toast.makeText(getActivity(), "he"+categories.get(i).getCategory_id(), Toast.LENGTH_SHORT).show();
                                List<Products> products = categories.get(i).getProducts();
                                for (int j=0;j<products.size();j++){
                                    passbookView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
                                   // adapter = new SameCategoryProductAdapter(getActivity(), products,products.get(j).getProduct_id());
                                    adapter = new SameCategoryProductAdapter(getActivity(), products,feedkey);
                                    passbookView.setAdapter(adapter);
                                    progressD.setVisibility(View.GONE);
                                   // Toast.makeText(getActivity(),"he" +products.get(j).getProduct_id(), Toast.LENGTH_SHORT).show();
                                   /* if (products.get(j).getProduct_id().equals(feedkey)){
                                    }
                                    else if (!(products.get(j).getProduct_id().equals(feedkey))){
                                        passbookView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
                                        adapter = new SameCategoryProductAdapter(getActivity(), products,products.get(j).getProduct_id());
                                        passbookView.setAdapter(adapter);
                                        progressD.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), products.get(j).getProduct_id(), Toast.LENGTH_SHORT).show();
                                    }*/
                                }
                                /*passbookView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                adapter = new SameCategoryProductAdapter(getActivity(), products);
                                passbookView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);*/
                            }
                            else {
                                Toast.makeText(getActivity(), "she"+categories.get(i).getCategory_id(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        /*if (categories.get(0).getCategory_id() == category_id){
                            Toast.makeText(getActivity(), "he"+categories.get(0).getCategory_id(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "she"+categories.get(0).getCategory_id(), Toast.LENGTH_SHORT).show();
                        }*/

                    }
                }
                else{
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    progressD.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressD.setVisibility(View.GONE);
            }
        });


        /*final RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.VENDOR_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONObject obj1 = jsonObject.getJSONObject("data");

                        // add products

                        productItemsList.clear();
                       // productView.removeAllViews();

                        JSONArray jsonArrayCat = obj1.getJSONArray("category");
                        for (int i = 0; i < jsonArrayCat.length(); i++) {
                            final JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);

                           // if(jsonObjectCat.getString("category_id").equals(feedkeyC)){
                             //   Toast.makeText(getActivity(), jsonObjectCat.getString("category_id"), Toast.LENGTH_SHORT).show();
                                ///Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();

                                JSONArray jsonArrayPro = jsonObjectCat.getJSONArray("products");
                                for (int j = 0; j < jsonArrayPro.length(); j++) {
                                    final JSONObject jsonObjectPro = jsonArrayPro.getJSONObject(j);

                                    productItemsList.add(new ProductItems(
                                            jsonObjectPro.getString("product_id"),
                                            jsonObjectPro.getString("name"),
                                            jsonObjectPro.getString("preview_image_path"),
                                            jsonObjectPro.getString("price"),
                                            jsonObjectPro.getString("category_id"),
                                            jsonObjectPro.getString("user_id")
                                            ));
                                   *//* if(!jsonObjectPro.getString("product_id").equals(feedkey)){
                                        productItemsList.add(new ProductItems(
                                                jsonObjectPro.getString("product_id"),
                                                jsonObjectPro.getString("name"),
                                                jsonObjectPro.getString("preview_image_path"),
                                                jsonObjectPro.getString("price"),
                                                jsonObjectPro.getString("category_id"),
                                                jsonObjectPro.getString("user_id")
                                        ));
                                    }*//*
                                }
                         //   }
                           *//* else {
                                Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
                            }*//*
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

         //       productView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
           //     sameAdapter = new SameCategoryProductAdapter(getActivity(), productItemsList, vendor);
             //   productView.setAdapter(sameAdapter);

                progressD.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressD.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> paramMap = new HashMap<String, String>();

                paramMap.put("userid", id);
                paramMap.put("usertoken", usertoken);
                paramMap.put("vendor_id", vendor);

                return paramMap;

            }
        };

        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue2.add(stringRequest2);
*/
        // Color Clicks

        blackIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SingleProduct();
                Bundle bundle = new Bundle();
                bundle.putString("checkColor", "black");
                bundle.putString("feedkey", feedkey);
                bundle.putString("feedkeyC", feedkeyC);
                bundle.putString("vendor", vendor);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        whiteIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SingleProduct();
                Bundle bundle = new Bundle();
                bundle.putString("checkColor", "white");
                bundle.putString("feedkey", feedkey);
                bundle.putString("feedkeyC", feedkeyC);
                bundle.putString("vendor", vendor);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        redIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SingleProduct();
                Bundle bundle = new Bundle();
                bundle.putString("checkColor", "red");
                bundle.putString("feedkey", feedkey);
                bundle.putString("feedkeyC", feedkeyC);
                bundle.putString("vendor", vendor);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        greenIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SingleProduct();
                Bundle bundle = new Bundle();
                bundle.putString("checkColor", "green");
                bundle.putString("feedkey", feedkey);
                bundle.putString("feedkeyC", feedkeyC);
                bundle.putString("vendor", vendor);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        blueIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SingleProduct();
                Bundle bundle = new Bundle();
                bundle.putString("checkColor", "blue");
                bundle.putString("feedkey", feedkey);
                bundle.putString("feedkeyC", feedkeyC);
                bundle.putString("vendor", vendor);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        vendorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // if(vendor!=null){

                    Fragment fragment = new VendorPage();
                    Bundle bundle = new Bundle();
                    bundle.putString("vendor_id", vendor_id);
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame_second, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

               // }

            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPorgress.setMessage("Adding...");
                mPorgress.show();

                final String val = String.valueOf(numberPicker.getValue());
                final String num = numPick.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_CART, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean error = jsonObject.getBoolean("error");

                            if(!error){
                                mPorgress.dismiss();
                                //String message = jsonObject.getString("message");
                                Toast.makeText(getActivity(), "Product added to cart successfully!", Toast.LENGTH_LONG).show();

                            } else {

                                String message = jsonObject.getString("message");

                                Snackbar snackbar = Snackbar.make(relativeLay,message,Snackbar.LENGTH_LONG)
                                        .setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                            }
                                        });
                                snackbar.setActionTextColor(Color.WHITE);
                                View sbview = snackbar.getView();
                                sbview.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                                TextView textView = sbview.findViewById(R.id.snackbar_text);
                                textView.setTextColor(Color.WHITE);
                                snackbar.show();

                                //Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                mPorgress.dismiss();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mPorgress.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mPorgress.dismiss();
                        Toast.makeText(getActivity(), "Some error occured. Please Try Again!", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> paramMap = new HashMap<String, String>();

                        paramMap.put("product_id", feedkey);
                        paramMap.put("userid", id);
                        paramMap.put("usertoken", usertoken);
                        if(check){
                            paramMap.put("quantity", num);
                        }else {
                            paramMap.put("quantity", val);
                        }
                        if(checkColor==null){
                            paramMap.put("order_color", "");
                        }else{
                            paramMap.put("order_color", checkColor);
                        }

                        return paramMap;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest);

            }
        });

        return view;
    }

}

