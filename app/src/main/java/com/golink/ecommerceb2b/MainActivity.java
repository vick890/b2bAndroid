package com.golink.ecommerceb2b;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.golink.ecommerceb2b.Cart.CartPage;
import com.golink.ecommerceb2b.Chat.ChatBoard;
import com.golink.ecommerceb2b.Home.HomePage;
import com.golink.ecommerceb2b.MyOrders.OrderPage;
import com.golink.ecommerceb2b.Profile.ProfilePage;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    Context context;
    private String page = "main";
    FragmentTransaction fragmentTransaction;
    private static final String FRAG_CHECK = "Add";
    private String id, usertoken, product;
    private FirebaseAuth mAuth;
    private CircleImageView profImage;
    private TextView profName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View navigHeaderView = navigationView.getHeaderView(0);

        final RelativeLayout addProductLay = findViewById(R.id.addProductLay);
        final RelativeLayout subscriptionLay = findViewById(R.id.subscriptionLay);
        final RelativeLayout walletLay = findViewById(R.id.walletLay);
        final RelativeLayout supportLay = findViewById(R.id.supportLay);
        ImageView logIcon = findViewById(R.id.logIcon);
        profName = navigHeaderView.findViewById(R.id.profName);
        profImage = navigHeaderView.findViewById(R.id.imageView);

        final ImageView addImage = findViewById(R.id.addImage);
        final TextView addImageText = findViewById(R.id.addImageText);

        final ImageView subscriptionImage = findViewById(R.id.subscriptionImage);
        final TextView subscriptionImageText = findViewById(R.id.subscriptionImageText);

        final ImageView walletImage = findViewById(R.id.walletImage);
        final TextView walletImageText = findViewById(R.id.walletImageText);

        final ImageView supportImage = findViewById(R.id.supportImage);
        final TextView supportImageText = findViewById(R.id.supportImageText);


        SharedPreferences sharedPreferences2 = getSharedPreferences(LogIn.login, MODE_PRIVATE);
        id = sharedPreferences2.getString("id", "0");
        usertoken = sharedPreferences2.getString("usertoken", "0");

        mAuth = FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();
        if(b!=null){
            page = b.getString("page");

        }

        assert page != null;
        switch (page) {
            case "cart":


                addProductLay.setBackgroundResource(R.color.transparent);
                addImage.setImageResource(R.drawable.home_grey_bottom);
                addImageText.setTextColor(Color.parseColor("#555555"));

                subscriptionLay.setBackgroundResource(R.color.transparent);
                subscriptionImage.setImageResource(R.drawable.order_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#555555"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.drawable.menu_back);
                supportImage.setImageResource(R.drawable.cart_red_bottom);
                supportImageText.setTextColor(Color.parseColor("#C61706"));

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new CartPage()).addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case "main":


                addProductLay.setBackgroundResource(R.drawable.menu_back);
                addImage.setImageResource(R.drawable.home_bottom);
                addImageText.setTextColor(Color.parseColor("#C61706"));

                subscriptionLay.setBackgroundResource(R.color.transparent);
                subscriptionImage.setImageResource(R.drawable.order_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#555555"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.color.transparent);
                supportImage.setImageResource(R.drawable.cart_bottom);
                supportImageText.setTextColor(Color.parseColor("#555555"));

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new HomePage(), FRAG_CHECK);
                fragmentTransaction.commit();
                break;
            case "cart_delete":


                addProductLay.setBackgroundResource(R.color.transparent);
                addImage.setImageResource(R.drawable.home_grey_bottom);
                addImageText.setTextColor(Color.parseColor("#555555"));

                subscriptionLay.setBackgroundResource(R.color.transparent);
                subscriptionImage.setImageResource(R.drawable.order_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#555555"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.drawable.menu_back);
                supportImage.setImageResource(R.drawable.cart_red_bottom);
                supportImageText.setTextColor(Color.parseColor("#C61706"));

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new CartPage());
                fragmentTransaction.commit();
                break;
            case "order":

                addProductLay.setBackgroundResource(R.color.transparent);
                addImage.setImageResource(R.drawable.home_grey_bottom);
                addImageText.setTextColor(Color.parseColor("#555555"));

                subscriptionLay.setBackgroundResource(R.drawable.menu_back);
                subscriptionImage.setImageResource(R.drawable.order_red_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#C61706"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.color.transparent);
                supportImage.setImageResource(R.drawable.cart_bottom);
                supportImageText.setTextColor(Color.parseColor("#555555"));


                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new OrderPage()).addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }

        GetProfile();

        logIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });


        addProductLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProductLay.setBackgroundResource(R.drawable.menu_back);
                addImage.setImageResource(R.drawable.home_bottom);
                addImageText.setTextColor(Color.parseColor("#C61706"));

                subscriptionLay.setBackgroundResource(R.color.transparent);
                subscriptionImage.setImageResource(R.drawable.order_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#555555"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.color.transparent);
                supportImage.setImageResource(R.drawable.cart_bottom);
                supportImageText.setTextColor(Color.parseColor("#555555"));


                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new HomePage(), FRAG_CHECK);
                fragmentTransaction.commit();
            }
        });

        subscriptionLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                addProductLay.setBackgroundResource(R.color.transparent);
                addImage.setImageResource(R.drawable.home_grey_bottom);
                addImageText.setTextColor(Color.parseColor("#555555"));

                subscriptionLay.setBackgroundResource(R.drawable.menu_back);
                subscriptionImage.setImageResource(R.drawable.order_red_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#C61706"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.color.transparent);
                supportImage.setImageResource(R.drawable.cart_bottom);
                supportImageText.setTextColor(Color.parseColor("#555555"));


                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new OrderPage()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        walletLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProductLay.setBackgroundResource(R.color.transparent);
                addImage.setImageResource(R.drawable.home_grey_bottom);
                addImageText.setTextColor(Color.parseColor("#555555"));

                subscriptionLay.setBackgroundResource(R.color.transparent);
                subscriptionImage.setImageResource(R.drawable.order_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#555555"));

                walletLay.setBackgroundResource(R.drawable.menu_back);
                walletImage.setImageResource(R.drawable.chat_red_bottom);
                walletImageText.setTextColor(Color.parseColor("#C61706"));

                supportLay.setBackgroundResource(R.color.transparent);
                supportImage.setImageResource(R.drawable.cart_bottom);
                supportImageText.setTextColor(Color.parseColor("#555555"));

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new ChatBoard()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        supportLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addProductLay.setBackgroundResource(R.color.transparent);
                addImage.setImageResource(R.drawable.home_grey_bottom);
                addImageText.setTextColor(Color.parseColor("#555555"));

                subscriptionLay.setBackgroundResource(R.color.transparent);
                subscriptionImage.setImageResource(R.drawable.order_bottom);
                subscriptionImageText.setTextColor(Color.parseColor("#555555"));

                walletLay.setBackgroundResource(R.color.transparent);
                walletImage.setImageResource(R.drawable.chat_bottom);
                walletImageText.setTextColor(Color.parseColor("#555555"));

                supportLay.setBackgroundResource(R.drawable.menu_back);
                supportImage.setImageResource(R.drawable.cart_red_bottom);
                supportImageText.setTextColor(Color.parseColor("#C61706"));


                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, new CartPage()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_second, new HomePage(), FRAG_CHECK);
            fragmentTransaction.commit();


        }else if (id == R.id.nav_order) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_second, new OrderPage()).addToBackStack(null);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_cart) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_second, new CartPage()).addToBackStack(null);
            fragmentTransaction.commit();

        }else if (id == R.id.nav_profile) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_second, new ProfilePage()).addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_chat) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame_second, new ChatBoard()).addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_log) {

            Logout();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void Logout() {

        new AlertDialog.Builder(MainActivity.this, R.style.MyDialogTheme)
                .setTitle("Log Out")
                .setMessage("Are you sure?")
                .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mAuth.signOut();

                        Intent intent = new Intent(MainActivity.this, LogIn.class);
                        intent.putExtra("loggedOut", true);
                        startActivity(intent);
                        finish();

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




    @Override
    public void onBackPressed() {

        if(page.equals("cart_delete")){
            Intent intent = getIntent();
            intent.putExtra("page", "main");
            startActivity(intent);
        }
        else{
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {

                HomePage addVegetables = (HomePage) getSupportFragmentManager().findFragmentByTag(FRAG_CHECK);
                if(addVegetables != null && addVegetables.isVisible()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Are you sure you want to exit?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //YourActivity.super.onBackPressed;

                                    Intent intent = new Intent(Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();



                }else{

                    super.onBackPressed();
                }


            }
        }


    }


    private void GetProfile () {

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.USER_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                try {
                    final JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jsonObject.getBoolean("error");

                    if(!error){

                        JSONObject obj1 = jsonObject.getJSONObject("data");
                        String name = obj1.getString("name");
                        final String image = obj1.getString("image_path");

                        profName.setText(name);

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


                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



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



    }


}
