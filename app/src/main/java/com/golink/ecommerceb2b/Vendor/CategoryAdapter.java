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
    ProductAdapter productAdapter;


    public CategoryAdapter(Context mCtx, List<CategoryItems> homeItemsList) {
        this.mCtx = mCtx;
        this.homeItemsList = homeItemsList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.category_items, parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        final CategoryItems homeItems = homeItemsList.get(position);

        String name = homeItems.getName();
        String Name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        holder.catName.setText(Name);
        String category_id = homeItems.getId();

        productAdapter = new ProductAdapter(mCtx, homeItems.getProductItemsList(), homeItems.getId());
        holder.productView.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
        holder.productView.setAdapter(productAdapter);
        holder.productView.setHasFixedSize(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String category_id = homeItems.getId();
               /* holder.productView.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
                ProductAdapter productAdapter = new ProductAdapter(mCtx, homeItems.getProductItemsList(), homeItems.getVendor());
                holder.productView.setAdapter(productAdapter);*/

                /*productAdapter = new ProductAdapter(mCtx, homeItems.getProductItemsList(), homeItems.getId());
                holder.productView.setLayoutManager(new LinearLayoutManager(mCtx, LinearLayoutManager.HORIZONTAL, false));
                holder.productView.setAdapter(productAdapter);
                holder.productView.setHasFixedSize(true);*/
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
           // notifyDataSetChanged();

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
