package com.golink.ecommerceb2b.Vendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.golink.ecommerceb2b.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder> {

    private Context mCtx;
    private List<CategoryItems> homeItemsList;


    public CategoryAdapter(Context mCtx, List<CategoryItems> homeItemsList) {
        this.mCtx = mCtx;
        this.homeItemsList = homeItemsList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_items, null);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final CategoryItems homeItems = homeItemsList.get(position);

        holder.catName.setText(homeItems.getName());
        String category_id = homeItems.getId();

        holder.productView.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
        ProductAdapter productAdapter = new ProductAdapter(mCtx, homeItems.getProductItemsList(), homeItems.getId());
        holder.productView.setAdapter(productAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String category_id = homeItems.getId();
               /* holder.productView.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
                ProductAdapter productAdapter = new ProductAdapter(mCtx, homeItems.getProductItemsList(), homeItems.getVendor());
                holder.productView.setAdapter(productAdapter);*/

            }
        });



    }

    @Override
    public int getItemCount() {
        return homeItemsList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView catName;
        private RecyclerView productView;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            catName = itemView.findViewById(R.id.catName);
            productView = itemView.findViewById(R.id.productView);


        }
    }
}
