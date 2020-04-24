package com.golink.ecommerceb2b.MyOrders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golink.ecommerceb2b.Models.Data;
import com.golink.ecommerceb2b.Models.NewData;
import com.golink.ecommerceb2b.Network.GetUserData;
import com.golink.ecommerceb2b.Network.RetrofitClient;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Registration.LogIn;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewSingleOrder extends Fragment {

    String userid,usertoken,orderPaymentId;
    private RecyclerView passbookView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private NewSingleOrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View v = inflater.inflate(R.layout.new_single_order_fragment,viewGroup,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LogIn.login, Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("id", null);
        usertoken = sharedPreferences.getString("usertoken", null);

        Bundle b = this.getArguments();
        if (b!=null){
            orderPaymentId = b.getString("orderid");
        }

       /* Toast.makeText(getActivity(), userid, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), usertoken, Toast.LENGTH_SHORT).show();*/

        passbookView = v.findViewById(R.id.singleOrderView);
        passbookView.setHasFixedSize(true);
        progressBar = v.findViewById(R.id.progressD);
        progressBar.setVisibility(View.VISIBLE);

        GetUserData getUserData = RetrofitClient.getRetrofit().create(GetUserData.class);
        Call<NewData> userCall = getUserData.getPassbook(userid,usertoken,orderPaymentId);
        userCall.enqueue(new Callback<NewData>() {
            @Override
            public void onResponse(Call<NewData> call, Response<NewData> response) {
                if (response.isSuccessful()){
                    NewData user = response.body();
                    if (user.isError() == true){
                        Toast.makeText(getActivity(), user.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    else if (user.isError() == false){
                        List<Data> data = user.getData();
                        passbookView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new NewSingleOrderAdapter(getActivity(), data);
                        passbookView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else{
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NewData> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        return v;
    }
}
