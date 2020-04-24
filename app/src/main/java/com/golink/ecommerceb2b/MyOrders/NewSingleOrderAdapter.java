package com.golink.ecommerceb2b.MyOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.golink.ecommerceb2b.Models.Data;
import com.golink.ecommerceb2b.R;
import com.golink.ecommerceb2b.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewSingleOrderAdapter extends RecyclerView.Adapter<NewSingleOrderAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<Data> homeItemsList;

    public NewSingleOrderAdapter(Context mCtx, List<Data> homeItemsList) {
        this.mCtx = mCtx;
        this.homeItemsList = homeItemsList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.all_passbook, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final Data homeItems = homeItemsList.get(position);

        Picasso.get()
                .load(Constants.IMAGE_URL + homeItems.getPreview_image_path())
                .into(holder.imageView);

        holder.name.setText(homeItems.getName());
        holder.price2.setText("₹ "+homeItems.getPrice());
        holder.qty.setText("Qty: "+homeItems.getQuantity());

        int val = Integer.valueOf(homeItems.getPrice());
        int qtyval = Integer.valueOf(homeItems.getQuantity());

        holder.price.setText(("₹ " + (val / qtyval)));

        /*Picasso.with(getActivity())
                .load("http://plugify.in/iotx/public\\/uploads\\/user-image\\/15\\/"+image1)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgView);*/
    }

    @Override
    public int getItemCount() {
        return homeItemsList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView name,price,price2,qty;
        private CardView cardView;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            price2 = itemView.findViewById(R.id.price2);
            qty = itemView.findViewById(R.id.qty);

        }
    }
}

