package com.golink.ecommerceb2b;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golink.ecommerceb2b.Models.Category;
import com.golink.ecommerceb2b.Models.Data;
import com.golink.ecommerceb2b.Models.NewData;
import com.golink.ecommerceb2b.Models.Products;
import com.golink.ecommerceb2b.Models.Profile;
import com.golink.ecommerceb2b.MyOrders.NewSingleOrderAdapter;
import com.golink.ecommerceb2b.Network.GetUserData;
import com.golink.ecommerceb2b.Network.RetrofitClient;
import com.golink.ecommerceb2b.Registration.LogIn;
import com.golink.ecommerceb2b.Vendor.SameCategoryProductAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SameCategoryItems extends Fragment {

    String userid,usertoken,vendor_id,category_id,product_id;
    private RecyclerView passbookView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private SameCategoryProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View v = inflater.inflate(R.layout.same_category_items,viewGroup,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LogIn.login, Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("id", null);
        usertoken = sharedPreferences.getString("usertoken", null);

        Bundle b = this.getArguments();
        if (b!=null){
            vendor_id = b.getString("vendor_id");
            category_id = b.getString("feedkeyC");
            product_id = b.getString("feedkey");
        }

       //Toast.makeText(getActivity(), product_id, Toast.LENGTH_SHORT).show();
       // Toast.makeText(getActivity(), usertoken, Toast.LENGTH_SHORT).show();*/

        passbookView = v.findViewById(R.id.singleOrderView);
        passbookView.setHasFixedSize(true);
        progressBar = v.findViewById(R.id.progressD);
        progressBar.setVisibility(View.VISIBLE);

        GetUserData getUserData = RetrofitClient.getRetrofit().create(GetUserData.class);
        Call<Profile> userCall = getUserData.getVendorDetails(userid,usertoken,vendor_id);
        userCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()){
                    Profile user = response.body();
                    if (user.isError() == true){
                        Toast.makeText(getActivity(), user.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else if (user.isError() == false){
                        Data data = user.getData();
                        List<Category> categories = data.getCategory();
                        for (int i=0;i<categories.size();i++){
                            if (categories.get(i).getCategory_id().equals(category_id)){
                                Toast.makeText(getActivity(), "he"+categories.get(i).getCategory_id(), Toast.LENGTH_SHORT).show();
                                List<Products> products = categories.get(i).getProducts();
                                for (int j=0;j<products.size();j++){
                                    if (products.get(j).getProduct_id().equals(product_id)){
                                    }
                                    else if (!(products.get(j).getProduct_id().equals(product_id))){
                                        passbookView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
                                        adapter = new SameCategoryProductAdapter(getActivity(), products,products.get(j).getProduct_id());
                                        passbookView.setAdapter(adapter);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), products.get(j).getProduct_id(), Toast.LENGTH_SHORT).show();
                                    }
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
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        return v;
    }
}
