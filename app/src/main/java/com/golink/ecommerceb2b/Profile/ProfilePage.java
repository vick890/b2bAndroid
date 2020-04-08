package com.golink.ecommerceb2b.Profile;

import android.app.ProgressDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Registration.FillDetails;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfilePage extends Fragment {

    private TextView profName, profMail, profMobile, profAlt, profBusinss, profAddress;
    private CircleImageView profImage;
    private ProgressBar progressD;
    private String id, usertoken;
    private ProgressDialog mProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_page, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle("");

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");


        profName = view.findViewById(R.id.profName);
        profMail = view.findViewById(R.id.profMail);
        profMobile = view.findViewById(R.id.profPhone);
        profAlt = view.findViewById(R.id.profAlt);
        profBusinss = view.findViewById(R.id.profBusinss);
        profAddress = view.findViewById(R.id.profAddress);
        profImage = view.findViewById(R.id.profImage);

        progressD = view.findViewById(R.id.progressD);
        progressD.setVisibility(View.VISIBLE);
        mProgress = new ProgressDialog(getActivity());

        Button logButton = view.findViewById(R.id.logButton);


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                try {
                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONObject obj1 = jsonObject.getJSONObject("data");
                        String name = obj1.getString("name");
                        String email = obj1.getString("email");
                        String mobile = obj1.getString("mobile");
                        String address = obj1.getString("address");
                        String business_name = obj1.getString("business_name");
                        String mobile2 = obj1.getString("mobile2");
                        final String image = obj1.getString("image_path");

                        profName.setText(name);
                        profMail.setText(email);
                        profMobile.setText(mobile);
                        profBusinss.setText("Business Name: " + business_name);
                        profAlt.setText("Alt. Mobile: " + mobile2);
                        profAddress.setText("Address: " + address);

                        if(!image.equals("null")){

                            Picasso.get().load((Constants.IMAGE_URL + image))
                                    .centerInside().fit()
                                    .networkPolicy(NetworkPolicy.OFFLINE).into(profImage, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load((Constants.IMAGE_URL + image)).centerInside()
                                            .fit().into(profImage);

                                }
                            });

                        }else{
                            Picasso.get().load(R.drawable.image_prof)
                                    .centerInside().fit()
                                    .networkPolicy(NetworkPolicy.OFFLINE).into(profImage, new Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(R.drawable.image_prof).centerInside()
                                            .fit().into(profImage);

                                }
                            });
                        }



                    } else {

                        String message = jsonObject.getString("message");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
                progressD.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                progressD.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Some error occured. Please Try Again!", Toast.LENGTH_SHORT).show();

            }
        }) {
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




        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FillDetails.class);
                intent.putExtra("page", "edit");
                startActivity(intent);

            }
        });




        return view;

    }



}
