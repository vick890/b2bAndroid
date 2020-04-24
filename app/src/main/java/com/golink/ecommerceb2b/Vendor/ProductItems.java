package com.golink.ecommerceb2b.Vendor;

public class ProductItems {

    private String id;
    private String name;
    private String image;
    private String price;
    private String category;
    private String user_id;


    public ProductItems(String id, String name, String image, String price, String category,String user_id) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.category = category;
        this.user_id = user_id;
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

    public String getUser_id() {
        return user_id;
    }
}
