package com.golink.ecommerceb2b.Vendor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextClock;
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
import com.golink.ecommerceb2b.Home.SearchPage;
import com.golink.ecommerceb2b.Home.VendorSearchPage;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class VendorPage extends Fragment {

    private CircleImageView vendorImage;
    private TextView vendorName;
    private EditText vendorSearch;
    private EditText search;
    private RecyclerView categoryView;
    private List<CategoryItems> homeItemsList = new ArrayList<>();
    private List<ProductItems> productItemsList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private String id, usertoken, feedkey;
    private ProgressBar progressD;
    private RelativeLayout sendView;
    private TextView sendReq;
    private ProgressDialog mProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_vendor_page, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("");


        Bundle b = this.getArguments();
        if(b!=null)
        feedkey = b.getString("feedkey");

        TextView productSearch = view.findViewById(R.id.productSearch);
        ImageButton searchBtn = view.findViewById(R.id.searchBtn);


        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        categoryView = view.findViewById(R.id.categoryView);
        categoryView.setHasFixedSize(true);

        vendorSearch = view.findViewById(R.id.vendorSearch);
        vendorImage = view.findViewById(R.id.vendorImage);
        vendorName = view.findViewById(R.id.vendorName);
        sendView = view.findViewById(R.id.sendView);
        sendReq = view.findViewById(R.id.sendReq);


        progressD = view.findViewById(R.id.progressD);
        progressD.setVisibility(View.VISIBLE);


        mProgress = new ProgressDialog(getActivity());

        final RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.CHECK_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    String request = jsonObject.getString("request");

                    switch (request) {
                        case "0":
                            sendView.setVisibility(View.VISIBLE);
                            sendReq.setVisibility(View.GONE);
                            categoryView.setVisibility(View.GONE);
                            break;
                        case "1":
                            sendView.setVisibility(View.GONE);
                            sendReq.setVisibility(View.VISIBLE);
                            sendReq.setText("Request Pending...");
                            categoryView.setVisibility(View.GONE);
                            break;
                        case "2":
                            sendView.setVisibility(View.GONE);
                            sendReq.setVisibility(View.VISIBLE);
                            sendReq.setText("ACCEPTED");
                            categoryView.setVisibility(View.VISIBLE);
                            break;
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }



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
                paramMap.put("vendor_id", feedkey);

                return paramMap;

            }
        };

        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue2.add(stringRequest2);









        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.VENDOR_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONObject obj1 = jsonObject.getJSONObject("data");
                        //final String name = obj1.getString("name");
                        final String bname = obj1.getString("business_name");
                        final String image = obj1.getString("user_image_path");

                        vendorName.setText(bname);

                        Picasso.get().load(Constants.IMAGE_URL + image)
                                .centerInside().fit()
                                .networkPolicy(NetworkPolicy.OFFLINE).into(vendorImage, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError(Exception e) {

                                Picasso.get().load(Constants.IMAGE_URL + image).centerInside()
                                        .fit().into(vendorImage);
                            }

                        });


                        // add products

                        productItemsList.clear();
                        homeItemsList.clear();


                        JSONArray jsonArrayCat = obj1.getJSONArray("category");
                        for (int i = 0; i < jsonArrayCat.length(); i++) {
                            final JSONObject jsonObjectCat = jsonArrayCat.getJSONObject(i);

                            String idCat = jsonObjectCat.getString("category_id");
                            String nameCat = jsonObjectCat.getString("name");

                            JSONArray jsonArrayPro = jsonObjectCat.getJSONArray("products");
                            for (int j = 0; j < jsonArrayPro.length(); j++) {
                                final JSONObject jsonObjectPro = jsonArrayPro.getJSONObject(j);

                                    productItemsList.add(new ProductItems(
                                            jsonObjectPro.getString("product_id"),
                                            jsonObjectPro.getString("name"),
                                            jsonObjectPro.getString("preview_image_path"),
                                            jsonObjectPro.getString("price"),
                                            jsonObjectPro.getString("category_id")
                                    ));

                            }

                            homeItemsList.add(new CategoryItems(
                                    idCat,
                                    nameCat,
                                    feedkey,
                                    productItemsList
                            ));


                        }




                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


                categoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
                categoryAdapter = new CategoryAdapter(getActivity(), homeItemsList);
                categoryView.setAdapter(categoryAdapter);

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
                paramMap.put("vendor_id", feedkey);

                return paramMap;

            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);




        sendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme)
                        .setTitle("Send Request")
                        .setMessage("Are you sure?")
                        .setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mProgress.setMessage("Sending Request...");
                                mProgress.show();


                                if(getActivity()!= null){
                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SEND_REQUEST, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(final String response) {

                                            try {
                                                final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                                boolean error = jsonObject.getBoolean("error");

                                                if(!error){

                                                    mProgress.dismiss();
                                                    Toast.makeText(getActivity(), "Request sent successfully!", Toast.LENGTH_LONG).show();



                                                } else {

                                                    String message = jsonObject.getString("message");

                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                    mProgress.dismiss();

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                mProgress.dismiss();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {


                                            mProgress.dismiss();
                                            Toast.makeText(getActivity(), "Some error occured. Please Try Again!", Toast.LENGTH_SHORT).show();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> paramMap = new HashMap<String, String>();

                                            paramMap.put("userid", id);
                                            paramMap.put("usertoken", usertoken);
                                            paramMap.put("vendor_id", feedkey);


                                            return paramMap;

                                        }
                                    };

                                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                    requestQueue.add(stringRequest);

                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();







            }
        });

        productSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new VendorSearchPage();
                Bundle bundle = new Bundle();
                bundle.putString("search", "product");
                bundle.putString("vendor_id", feedkey);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        return view;
    }
}
