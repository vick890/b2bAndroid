package com.golink.ecommerceb2b.Vendor;

public class ProductItems {

    private String id;
    private String name;
    private String image;
    private String price;
    private String category;

    public ProductItems(String id, String name, String image, String price, String category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
