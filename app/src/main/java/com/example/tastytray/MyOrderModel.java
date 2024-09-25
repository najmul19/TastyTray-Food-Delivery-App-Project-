
package com.example.tastytray;

public class MyOrderModel {
    private int orderImage;
    private String soldItemName;
    private double price;
    private String quantity;

    public MyOrderModel() {
    }

    public MyOrderModel(int orderImage, String soldItemName, double price, String quantity) {
        this.orderImage = orderImage;
        this.soldItemName = soldItemName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(int orderImage) {
        this.orderImage = orderImage;
    }

    public String getSoldItemName() {
        return soldItemName;
    }

    public void setSoldItemName(String soldItemName) {
        this.soldItemName = soldItemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) { // Changed from String to double
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
