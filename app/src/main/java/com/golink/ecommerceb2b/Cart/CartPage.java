package com.golink.ecommerceb2b.Cart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.golink.ecommerceb2b.MainActivity;
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

public class CartPage extends Fragment {

    private List<CartItems> cartItemsList = new ArrayList<>();
    CartAdapter cartAdapter;
    private String id, usertoken;
    private ProgressBar progressD;
    private TextView cartPrice, txtCartEmpty;
    private int numFinal = 0;
    private ProgressDialog mProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart_page, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("CART");

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        progressD = view.findViewById(R.id.progressD);
        progressD.setVisibility(View.VISIBLE);
        mProgress = new ProgressDialog(getActivity());

        final RecyclerView cartView = view.findViewById(R.id.cartView);
        cartView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartPrice = view.findViewById(R.id.cartPrice);
        txtCartEmpty = view.findViewById(R.id.txtCardEmpty);


        Button cartGoButton = view.findViewById(R.id.cartGo);


        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                cartItemsList.clear();
                cartView.removeAllViews();
                numFinal = 0;


                try {
                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            cartItemsList.add(new CartItems(
                                    jsonObject2.getString("cart_id"),
                                    (jsonObject2.getString("name")),
                                    jsonObject2.getString("total_price"),
                                    jsonObject2.getString("preview_image_path"),
                                    jsonObject2.getString("quantity"),
                                    jsonObject2.getString("product_id"),
                                    "",
                                    jsonObject2.getString("unit_id"),
                                    jsonObject2.getString("price"),
                                    null,
                                    null,
                                    null,
                                    jsonObject2.getString("order_color")

                            ));


                            numFinal = numFinal + Integer.valueOf(jsonObject2.getString("total_price"));


                        }

                        cartPrice.setText("Total:\n₹ " + numFinal);
                        txtCartEmpty.setVisibility(View.GONE);

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                progressD.setVisibility(View.GONE);
                cartAdapter = new CartAdapter(getActivity(), cartItemsList);
                cartView.setAdapter(cartAdapter);



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





        cartGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(numFinal != 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you want to place order?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, final int idty) {


                                    mProgress.setMessage("Placing Order...");
                                    mProgress.setCanceledOnTouchOutside(false);
                                    mProgress.show();

                                    if(getActivity()!=null){
                                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PAYMENT, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(final String response) {

                                                try {
                                                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                                    boolean error = jsonObject.getBoolean("error");

                                                    if(!error){

                                                        mProgress.dismiss();
                                                        Toast.makeText(getActivity(), "Order placed successfully!", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                                        intent.putExtra("page", "main");
                                                        startActivity(intent);


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
                                                paramMap.put("total_amount", String.valueOf(numFinal));


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
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int idty) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }










            }
        });


        return view;
    }
}
