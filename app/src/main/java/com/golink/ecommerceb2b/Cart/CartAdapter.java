package com.golink.ecommerceb2b.Cart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.golink.ecommerceb2b.MainActivity;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<CartItems> carItemsList;
    private ProgressDialog mProgress;
    private String id, usertoken;
    MainActivity mainActivity;
    private String quantity;
    private String intPrice;


    public CartAdapter(Context mCtx, List<CartItems> carItemsList) {
        this.mCtx = mCtx;
        this.carItemsList = carItemsList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.cart_items, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final CartItems carItems = carItemsList.get(position);

        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        mProgress = new ProgressDialog(mCtx);

        holder.cartName.setText(carItems.getName());
        holder.cartPrice2.setText(("₹ " + carItems.getPrice1()));
        holder.cartColor.setText(("Color: " + carItems.getPrice_type()));


        holder.cartPrice.setText(("₹ " + carItems.getPrice()));
        holder.cartQuantity.setText(("Qty: " + carItems.getQuantity()));


        holder.numberPicker.setValue(Integer.valueOf(carItems.getQuantity()));


        Picasso.get().load((Constants.IMAGE_URL + carItems.getImage()))
                .centerInside().resize(500, 500)
                .networkPolicy(NetworkPolicy.OFFLINE).into(holder.cartImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load((Constants.IMAGE_URL + carItems.getImage())).centerInside()
                        .resize(500, 500).into(holder.cartImage);

            }
        });


        holder.cartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mProgress.setMessage("Deleting Item...");
                mProgress.show();


                RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_CART, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        try {
                            final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                            boolean error = jsonObject.getBoolean("error");

                            if(!error){

                                mProgress.dismiss();
                                Toast.makeText(mCtx, "Item deleted successfully!", Toast.LENGTH_SHORT).show();

                                Activity activity = (Activity)mCtx;
                                Intent intent = new Intent(mCtx, MainActivity.class);
                                intent.putExtra("page", "cart_delete");
                                mCtx.startActivity(intent);
                                activity.finish();


                            } else {

                                String message = jsonObject.getString("message");

                                Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mCtx, "Some error occured. Please Try Again!", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> paramMap = new HashMap<String, String>();

                        paramMap.put("userid", id);
                        paramMap.put("usertoken", usertoken);
                        paramMap.put("product_id", carItems.getProduct_id());


                        return paramMap;

                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(stringRequest);

            }
        });





        holder.cartWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgress.setMessage("Adding to Wishlist...");
                mProgress.show();


                JSONObject jsonObjectC = new JSONObject();

                try {

                    jsonObjectC.put("userid", id);
                    jsonObjectC.put("usertoken", usertoken);
                    jsonObjectC.put("product_id", carItems.getProduct_id());
                    jsonObjectC.put("quantity", carItems.getQuantity());

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                RequestQueue queue = Volley.newRequestQueue(mCtx);
                JsonObjectRequest jobReq = new JsonObjectRequest(Request.Method.POST, Constants.ADD_WISHLIST, jsonObjectC,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {


                                try {

                                    boolean error = jsonObject.getBoolean("error");

                                    if(!error){

                                        mProgress.dismiss();
                                        Toast.makeText(mCtx, "Item added successfully!", Toast.LENGTH_SHORT).show();


                                    } else {

                                        String message = jsonObject.getString("message");

                                        Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                mProgress.dismiss();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                mProgress.dismiss();
                                Toast.makeText(mCtx, "Some error occured. Please Try Again!", Toast.LENGTH_SHORT).show();

                            }
                        });

                jobReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(jobReq);


            }
        });

    }

    @Override
    public int getItemCount() {
        return carItemsList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView cartName;
        private TextView cartPrice;
        private ImageView cartImage;
        private ImageButton cartRemove;
        private TextView cartWishlist;
        private TextView cartAdd;
        private TextView cartQuantity;
        private TextView cartPrice2;
        private TextView cartColor;
        private NumberPicker numberPicker;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            cartName = itemView.findViewById(R.id.cartName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartRemove = itemView.findViewById(R.id.cartRemove);
            cartWishlist = itemView.findViewById(R.id.cartWishlist);
            cartAdd = itemView.findViewById(R.id.cartAdd);
            numberPicker = itemView.findViewById(R.id.numberPicker);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            cartPrice2 = itemView.findViewById(R.id.cartPrice2);
            cartColor = itemView.findViewById(R.id.cartColor);


        }
    }

}
