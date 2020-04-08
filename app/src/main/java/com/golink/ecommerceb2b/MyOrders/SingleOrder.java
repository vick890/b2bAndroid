package com.golink.ecommerceb2b.MyOrders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SingleOrder extends Fragment {

    private List<SubscribeItems> subscribeItemsList = new ArrayList<>();
    SingleOrderAdapter subscribeAdapter;
    private String id, usertoken, orderid;
    private ProgressBar progressD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_single_order, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("ORDER");

        Bundle b = this.getArguments();
        if(b!=null){
            orderid = (String) b.get("orderid");
        }


        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        progressD = view.findViewById(R.id.progressD);
        progressD.setVisibility(View.VISIBLE);

        final RecyclerView subscribeView = view.findViewById(R.id.singleOrderView);
        subscribeView.setLayoutManager(new LinearLayoutManager(getActivity()));




        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ORDER_PRODUCTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                subscribeItemsList.clear();
                subscribeView.removeAllViews();

                try {
                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            subscribeItemsList.add(new SubscribeItems(
                                    null,
                                    jsonObject2.getString("name"),
                                    jsonObject2.getString("price"),
                                    jsonObject2.getString("quantity"),
                                    jsonObject2.getString("preview_image_path"),
                                    jsonObject2.getString("product_id"),
                                    jsonObject2.getString("order_color"),
                                    jsonObject2.getString("category_id"),
                                    jsonObject2.getString("vendor_id")
                            ));




                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                progressD.setVisibility(View.GONE);
                subscribeAdapter = new SingleOrderAdapter(getActivity(), subscribeItemsList);
                subscribeView.setAdapter(subscribeAdapter);



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
                paramMap.put("order_payment_id", orderid);

                return paramMap;

            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);




        return view;

    }
}
