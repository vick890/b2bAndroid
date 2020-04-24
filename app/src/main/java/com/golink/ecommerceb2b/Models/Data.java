package com.golink.ecommerceb2b.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("name")
    private String name;
    @SerializedName("package_quantity")
    private String package_quantity;
    @SerializedName("unit_name")
    private String unit_name;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("preview_image")
    private String preview_image;
    @SerializedName("id")
    private String id;
    @SerializedName("order_payment_id")
    private String order_payment_id;
    @SerializedName("product_id")
    private String product_id;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("price")
    private String price;
    @SerializedName("order_color")
    private String order_color;
    @SerializedName("indate")
    private String indate;
    @SerializedName("updated")
    private String updated;
    @SerializedName("status")
    private String status;
    @SerializedName("business_name")
    private String business_name;
    @SerializedName("preview_image_path")
    private String preview_image_path;
    @SerializedName("images")
    private String images;
    @SerializedName("category")
    private List<Category> category;

    public Data(String name, String package_quantity, String unit_name, String category_name, String preview_image, String id, String order_payment_id, String product_id, String quantity, String price, String order_color, String indate, String updated, String status, String business_name, String preview_image_path, String images, List<Category> category) {
        this.name = name;
        this.package_quantity = package_quantity;
        this.unit_name = unit_name;
        this.category_name = category_name;
        this.preview_image = preview_image;
        this.id = id;
        this.order_payment_id = order_payment_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.order_color = order_color;
        this.indate = indate;
        this.updated = updated;
        this.status = status;
        this.business_name = business_name;
        this.preview_image_path = preview_image_path;
        this.images = images;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackage_quantity() {
        return package_quantity;
    }

    public void setPackage_quantity(String package_quantity) {
        this.package_quantity = package_quantity;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPreview_image() {
        return preview_image;
    }

    public void setPreview_image(String preview_image) {
        this.preview_image = preview_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_payment_id() {
        return order_payment_id;
    }

    public void setOrder_payment_id(String order_payment_id) {
        this.order_payment_id = order_payment_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_color() {
        return order_color;
    }

    public void setOrder_color(String order_color) {
        this.order_color = order_color;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getPreview_image_path() {
        return preview_image_path;
    }

    public void setPreview_image_path(String preview_image_path) {
        this.preview_image_path = preview_image_path;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
