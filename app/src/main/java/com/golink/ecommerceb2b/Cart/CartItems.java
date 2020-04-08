package com.golink.ecommerceb2b.Cart;

public class CartItems {
    private String id;
    private String name;
    private String price;
    private String quantity;
    private String image;
    private String product_id;
    private String refund;
    private String unit_id;
    private String price1;
    private String price2;
    private String price3;
    private String price4;
    private String price_type;

    public CartItems(String id, String name, String price, String image, String quantity,
                     String product_id, String refund, String unit_id, String price1, String price2, String price3,
                     String price4, String price_type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.product_id = product_id;
        this.refund = refund;
        this.unit_id = unit_id;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.price4 = price4;
        this.price_type = price_type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getRefund() {
        return refund;
    }


    public String getUnit_id() {
        return unit_id;
    }

    public String getPrice1() {
        return price1;
    }

    public String getPrice2() {
        return price2;
    }

    public String getPrice3() {
        return price3;
    }

    public String getPrice4() {
        return price4;
    }

    public String getPrice_type() {
        return price_type;
    }
}
