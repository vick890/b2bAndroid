package com.golink.ecommerceb2b.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.golink.ecommerceb2b.Chat.PersonalChat;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Utils.Constants;
import com.golink.ecommerceb2b.Vendor.SingleProduct;
import com.golink.ecommerceb2b.Vendor.VendorPage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<FeaturedItems> homeItemsList;
    private String check;


    public FeaturedAdapter(Context mCtx, List<FeaturedItems> homeItemsList, String check) {
        this.mCtx = mCtx;
        this.homeItemsList = homeItemsList;
        this.check = check;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.featured_items, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final FeaturedItems homeItems = homeItemsList.get(position);

        holder.featureName.setText(homeItems.getName());
        //Toast.makeText(mCtx, homeItems.getUser_id(), Toast.LENGTH_SHORT).show();

        if(check.equals("chat")){

            holder.featureLoc.setText("");
        }else {

            holder.featureLoc.setText(homeItems.getLocation());
        }

        Picasso.get().load(Constants.IMAGE_URL + homeItems.getImage())
                .centerInside().fit()
                .networkPolicy(NetworkPolicy.OFFLINE).into(holder.featureImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {

                Picasso.get().load(Constants.IMAGE_URL + homeItems.getImage()).centerInside()
                        .fit().into(holder.featureImage);
            }

        });


        holder.linLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (check) {
                    case "vendor": {
                        Fragment fragment = new VendorPage();
                        Bundle bundle = new Bundle();
                        bundle.putString("vendor_id", homeItems.getId());
                        Toast.makeText(mCtx, homeItems.getId(), Toast.LENGTH_SHORT).show();
                        fragment.setArguments(bundle);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame_second, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        break;
                    }

                    case "product": {
                        String vendor_id = homeItems.getUser_id();
                       // Toast.makeText(mCtx, vendor_id, Toast.LENGTH_SHORT).show();
                        Fragment fragment = new SingleProduct();
                        Bundle bundle = new Bundle();
                        bundle.putString("vendor_id", vendor_id);
                        bundle.putString("feedkey", homeItems.getId());
                        bundle.putString("feedkeyC", homeItems.getCategory_id());
                        fragment.setArguments(bundle);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame_second, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    }

                    case "chat":

                        Intent intent = new Intent(mCtx, PersonalChat.class);
                        intent.putExtra("uid", homeItems.getLocation());
                        mCtx.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeItemsList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView featureName;
        private TextView featureLoc;
        private LinearLayout linLay;
        private CircleImageView featureImage;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            featureName = itemView.findViewById(R.id.featureName);
            linLay = itemView.findViewById(R.id.linLay);
            featureImage = itemView.findViewById(R.id.featureImage);
            featureLoc = itemView.findViewById(R.id.featureCity);


        }
    }
}
