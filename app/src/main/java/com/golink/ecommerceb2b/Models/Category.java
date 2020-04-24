package com.golink.ecommerceb2b.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("category_id")
    private String category_id;
    @SerializedName("products")
    private List<Products> products;

    public Category(String category_id, List<Products> products) {
        this.category_id = category_id;
        this.products = products;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
