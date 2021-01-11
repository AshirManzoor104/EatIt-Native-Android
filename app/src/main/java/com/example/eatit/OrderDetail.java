package com.example.eatit;

import com.google.gson.annotations.SerializedName;

public class OrderDetail {
    @SerializedName("order_id")
    private int orderId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("order_time")
    private String orderTime;
    @SerializedName("order_status")
    private String orderStatus;
    @SerializedName("total_amount")
    private int totalAmount;
    @SerializedName("delivery_address")
    private String deliveryAddress;
    @SerializedName("city")
    private String city;
    @SerializedName("contact_number")
    private String contactNumber;
    @SerializedName("payment_method")
    private String paymentMethod;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, int userId, String orderTime, String orderStatus, int totalAmount, String deliveryAddress, String city, String contactNumber, String paymentMethod) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
        this.city = city;
        this.contactNumber = contactNumber;
        this.paymentMethod = paymentMethod;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

