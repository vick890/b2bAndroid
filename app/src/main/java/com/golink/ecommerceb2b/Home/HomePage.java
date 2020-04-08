package com.golink.ecommerceb2b.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.golink.ecommerceb2b.Chat.ChatBoard;
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

public class HomePage extends Fragment {


    private List<FeaturedItems> homeItemsList = new ArrayList<>();
    private List<FeaturedItems> newItemsList = new ArrayList<>();
    FeaturedAdapter featuredAdapter;
    private RecyclerView categoryView;
    private ViewPager viewPager;
    private int current_position = 0;
    private String id, usertoken;
    private ProgressBar progressD;
    private ArrayList<String> businessName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_home_page, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("");

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        categoryView = view.findViewById(R.id.featuredView);
        categoryView.setHasFixedSize(true);

        TextView vendorSearch = view.findViewById(R.id.vendorSearch);
        TextView productSearch = view.findViewById(R.id.productSearch);
        TextView locationSearch = view.findViewById(R.id.locationSearch);

        progressD = view.findViewById(R.id.progressD);
        progressD.setVisibility(View.VISIBLE);


//        RequestQueue requestQueue00 = Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest00 = new StringRequest(Request.Method.POST, Constants.BLOCK_CHECK, new Response.Listener<String>() {
//            @Override
//            public void onResponse(final String response) {
//
//                try {
//                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
//
//                    boolean delete = jsonObject.getBoolean("delete");
//                    String data = jsonObject.getString("message");
//
//                    if(delete){
//
//                        Toast.makeText(getActivity(), "Your account has been blocked! Please contact the support team.", Toast.LENGTH_LONG).show();
//
//                        Intent intent = new Intent(getActivity(), LogIn.class);
//                        intent.putExtra("loggedOut", true);
//                        startActivity(intent);
//                        getActivity().finish();
//                    }
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> paramMap = new HashMap<String, String>();
//
//                paramMap.put("userid", id);
//                paramMap.put("usertoken", usertoken);
//
//                return paramMap;
//
//            }
//        };
//
//        stringRequest00.setRetryPolicy(new DefaultRetryPolicy(10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        requestQueue00.add(stringRequest00);


        String url = "http://cartprod.ml/ecom/api/productByAllUsersVendor?userid=76&usertoken=aac5514deb3a7fd4122941a0875e6f91";
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REQUEST_CHECK, new Response.Listener<String>() {
        //StringRequest stringRequest = new StringRequest(Request.Method.POST,Constants.REQUEST_CHECK,new Res)
            @Override
            public void onResponse(String response) {

                try {

                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        homeItemsList.clear();
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                         //   businessName.add(jsonArray.getJSONObject(i).getString("business_name"));

                            if(jsonObject2.getString("status").equals("1")){
                                homeItemsList.add(new FeaturedItems(
                                        jsonObject2.getString("vendor_id"),
                                        jsonObject2.getString("business_name"),
                                        jsonObject2.getString("vendor_address"),
                                        jsonObject2.getString("vendor_image_path")

                                ));

                               /* newItemsList.add(new FeaturedItems(
                                        jsonObject2.getString("vendor_id"),
                                        jsonObject2.getString("business_name"),
                                        jsonObject2.getString("vendor_address"),
                                        jsonObject2.getString("vendor_image_path")
                                ));*/

                            }

                        }
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                categoryView.setLayoutManager(new LinearLayoutManager(getActivity()));
                featuredAdapter = new FeaturedAdapter(getActivity(), homeItemsList, "vendor");
                categoryView.setAdapter(featuredAdapter);

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



        vendorSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SearchPage();
                Bundle bundle = new Bundle();
                bundle.putString("search", "vendor");
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        productSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SearchPage();
                Bundle bundle = new Bundle();
                bundle.putString("search", "product");
                //bundle.putStringArrayList("businessName",businessName);
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });


        locationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SearchPage();
                Bundle bundle = new Bundle();
                bundle.putString("search", "location");
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
