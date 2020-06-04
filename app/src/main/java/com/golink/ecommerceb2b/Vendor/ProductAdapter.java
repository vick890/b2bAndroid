package com.golink.ecommerceb2b.Vendor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.SameCategoryItems;
import com.golink.ecommerceb2b.Utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<ProductItems> homeItemsList;
    private String id;

    public ProductAdapter(Context mCtx, List<ProductItems> homeItemsList, String id) {
        this.mCtx = mCtx;
        this.homeItemsList = homeItemsList;
        this.id = id;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_items, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final ProductItems homeItems = homeItemsList.get(position);

         if (homeItems.getCategory().equals(id)){
                holder.prodName.setText(homeItems.getName());
                holder.prodPrice.setText("₹ " + homeItems.getPrice());
                holder.offerPrice.setText("₹ " + homeItems.getOffer_price());
                holder.offerPercentage.setText(homeItems.getOffer_percentage()+"% OFF");
                if (homeItems.getOut_of_stock().equals("1")){
                    holder.outOfStock.setText("OUT OF STOCK");
                }
                else {
                    holder.outOfStock.setVisibility(View.INVISIBLE);
                }

                Picasso.get().load(Constants.IMAGE_URL + homeItems.getImage())
                        .centerInside().fit()
                        .networkPolicy(NetworkPolicy.OFFLINE).into(holder.prodImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {

                        Picasso.get().load(Constants.IMAGE_URL + homeItems.getImage()).centerInside()
                                .fit().into(holder.prodImage);
                    }

                });
            }else {
                holder.linLay.setVisibility(View.GONE);
            }

      //  }

        holder.linLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SingleProduct();
                //Fragment fragment = new SameCategoryItems();
                Bundle bundle = new Bundle();
                bundle.putString("vendor", id);
                bundle.putString("feedkey", homeItems.getId());
                bundle.putString("feedkeyC", homeItems.getCategory());
                bundle.putString("vendor_id",homeItems.getUser_id());
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_second, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return homeItemsList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView prodName;
        private TextView prodPrice,offerPrice,offerPercentage,outOfStock;
        private CardView linLay;
        private ImageView prodImage;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            prodName = itemView.findViewById(R.id.prodName);
            offerPrice = itemView.findViewById(R.id.offerPrice);
            offerPercentage = itemView.findViewById(R.id.offerPercentage);
            outOfStock = itemView.findViewById(R.id.outOfStock);
            linLay = itemView.findViewById(R.id.linLay);
            prodImage = itemView.findViewById(R.id.prodImage);
            prodPrice = itemView.findViewById(R.id.prodPrice);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
