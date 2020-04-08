package com.golink.ecommerceb2b.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SearchPage extends Fragment {

    private String search;
    private EditText vendorSearch;
    private RecyclerView searchView;
    private List<FeaturedItems> homeItemsList = new ArrayList<>();
    private FeaturedAdapter featuredAdapter;
    private String id, usertoken,vendor_id;
    private ProgressBar progressD;
    private ArrayList<String> businessName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_search_page, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("");


        Bundle b = this.getArguments();
        if(b!=null){
            search = b.getString("search");
            //businessName = b.getStringArrayList("businessName");
            //vendor_id = b.getString("vendor_id");
        }

        vendorSearch = view.findViewById(R.id.vendorSearch);
        ImageButton searchBtn = view.findViewById(R.id.searchBtn);

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");


        searchView = view.findViewById(R.id.searchView);
        searchView.setHasFixedSize(true);

        progressD = view.findViewById(R.id.progressD);

        switch (search) {
            case "vendor":
                vendorSearch.setHint("Vendor Search");
                break;
            case "product":
                vendorSearch.setHint("Product Search");
                break;
            case "location":
                vendorSearch.setHint("Location Search");
                break;
        }


        vendorSearch.post(new Runnable() {
            @Override
            public void run() {
                vendorSearch.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(vendorSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        });



        vendorSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((i & EditorInfo.IME_ACTION_DONE) != 0) {

                    homeItemsList.clear();
                    searchView.removeAllViews();

                    final String searchText = vendorSearch.getText().toString().toLowerCase();

                    switch (search) {
                        case "vendor":
                            setSearchVen(searchText);
                            break;
                        case "product":
                            setSearchPro(searchText);
                            break;
                        case "location":
                            setSearchLoc(searchText);
                            break;
                    }




                    InputMethodManager inputManager =
                            (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(
                            getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }
                else {
                    return false;
                }
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                homeItemsList.clear();
                searchView.removeAllViews();

                final String searchText = vendorSearch.getText().toString().toLowerCase();

                switch (search) {
                    case "vendor":
                        setSearchVen(searchText);
                        break;
                    case "product":
                        setSearchPro(searchText);
                        break;
                    case "location":
                        setSearchLoc(searchText);
                        break;
                }

                InputMethodManager inputManager =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });

        return view;
    }

    private void setSearchVen(final String searchText) {

        if(!TextUtils.isEmpty(searchText)){

            progressD.setVisibility(View.VISIBLE);

            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ALL_VENDORS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                        boolean error = jsonObject.getBoolean("error");

                        if(!error){

                            homeItemsList.clear();
                            searchView.removeAllViews();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                if(jsonObject2.getString("business_name").toLowerCase().contains(searchText)){
                                    homeItemsList.add(new FeaturedItems(
                                            jsonObject2.getString("id"),
                                            jsonObject2.getString("business_name"),
                                            jsonObject2.getString("address"),
                                            jsonObject2.getString("user_image_path")

                                    ));
                                }




                            }
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    searchView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    featuredAdapter = new FeaturedAdapter(getActivity(), homeItemsList, "vendor");
                    searchView.setAdapter(featuredAdapter);

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
                   // paramMap.put("vendor_id",vendor_id);

                    return paramMap;

                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        }
    }

    private void setSearchPro(final String searchText) {

        if(!TextUtils.isEmpty(searchText)){

            progressD.setVisibility(View.VISIBLE);

            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            //Constants.ALL_PRODUCTS
           // String url = "http://cartprod.ml/ecommerce/api/productByVendorId?userid=73&usertoken=aac5514deb3a7fd4122941a0875e6f91&vendor_id=36";

            StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.ALL_PRODUCTS_BY_ALL_USER_VENDOR, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                        boolean error = jsonObject.getBoolean("error");

                        if(!error){

                            homeItemsList.clear();
                            searchView.removeAllViews();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                if(jsonObject2.getString("name").toLowerCase().contains(searchText)){
                                    homeItemsList.add(new FeaturedItems(
                                            jsonObject2.getString("product_id"),
                                            jsonObject2.getString("name"),
                                            ("₹ " + jsonObject2.getString("price")),
                                            jsonObject2.getString("preview_image_path")

                                    ));
                                }

                             /*   if(jsonObject2.getString("category_id").toLowerCase().equals(1)){
                                   *//* homeItemsList.add(new FeaturedItems(
                                            jsonObject2.getString("id"),
                                            jsonObject2.getString("business_name"),
                                            jsonObject2.getString("address"),
                                            jsonObject2.getString("user_image_path")
                                    ));*//*

                                  *//*  if(jsonObject2.getString("name").toLowerCase().contains(searchText)
                                        *//**//*|| jsonObject2.getString("business_name").toLowerCase().contains("brolabs")*//**//*){
                                        homeItemsList.add(new FeaturedItems(
                                                jsonObject2.getString("product_id"),
                                                jsonObject2.getString("name"),
                                                ("₹ " + jsonObject2.getString("price")),
                                                jsonObject2.getString("preview_image_path")

                                        ));
                                    }*//*

                                }*/

/*
                                if(jsonObject2.getString("name").toLowerCase().contains(searchText)
                                        || jsonObject2.getString("vendor_name").toLowerCase().contains("brolabs")
                                        */
/*|| jsonObject2.getString("business_name").toLowerCase().contains("brolabs")*//*
){
                                    homeItemsList.add(new FeaturedItems(
                                            jsonObject2.getString("product_id"),
                                            jsonObject2.getString("name"),
                                            ("₹ " + jsonObject2.getString("price")),
                                            jsonObject2.getString("preview_image_path")

                                    ));
                                }
*/
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                    searchView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    featuredAdapter = new FeaturedAdapter(getActivity(), homeItemsList, "product");
                    searchView.setAdapter(featuredAdapter);

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
                    //paramMap.put("vendor_id",vendor_id);
                    return paramMap;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        }
    }


    private void setSearchLoc(final String searchText) {

        if(!TextUtils.isEmpty(searchText)){

            progressD.setVisibility(View.VISIBLE);

            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ALL_VENDORS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                        boolean error = jsonObject.getBoolean("error");

                        if(!error){

                            homeItemsList.clear();
                            searchView.removeAllViews();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                if(jsonObject2.getString("city").toLowerCase().contains(searchText)
                                || jsonObject2.getString("state").toLowerCase().contains(searchText)){
                                    homeItemsList.add(new FeaturedItems(
                                            jsonObject2.getString("id"),
                                            jsonObject2.getString("business_name"),
                                            jsonObject2.getString("address"),
                                            jsonObject2.getString("user_image_path")

                                    ));
                                }

                            }
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                    searchView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    featuredAdapter = new FeaturedAdapter(getActivity(), homeItemsList, "vendor");
                    searchView.setAdapter(featuredAdapter);

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

                    return paramMap;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        }
    }
}
