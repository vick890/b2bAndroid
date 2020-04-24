package com.golink.ecommerceb2b.Models;

import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("product_id")
    private String product_id;
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("category_id")
    private String category_id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("preview_image_path")
    private String preview_image_path;

    public Products(String product_id, String id, String user_id, String category_id, String name, String price, String preview_image_path) {
        this.product_id = product_id;
        this.id = id;
        this.user_id = user_id;
        this.category_id = category_id;
        this.name = name;
        this.price = price;
        this.preview_image_path = preview_image_path;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPreview_image_path() {
        return preview_image_path;
    }

    public void setPreview_image_path(String preview_image_path) {
        this.preview_image_path = preview_image_path;
    }
}
