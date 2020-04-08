package com.golink.ecommerceb2b.MyOrders;

public class OrderItems {

    private String image;
    private String orderId;
    private String time;
    private String id;
    private String price;
    private String address;
    private String status;

    public OrderItems(String image, String orderId, String time, String id,
                      String price, String address, String status) {
        this.image = image;
        this.time = time;
        this.id = id;
        this.price = price;
        this.address = address;
        this.status = status;
        this.orderId = orderId;

    }

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

}