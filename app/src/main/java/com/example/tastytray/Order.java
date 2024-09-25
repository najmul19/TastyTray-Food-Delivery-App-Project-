package com.example.tastytray;

public class Order {
    private int id;
    private int productId;
    private String customerName;
    private String phoneNumber;
    private int quantity;

    public Order() {
        // Default constructor
    }

    public Order(int id, int productId, String customerName, String phoneNumber, int quantity) {
        this.id = id;
        this.productId = productId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

