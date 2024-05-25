package com.example.pizza_restaurant_application.SpecialOffers;

public class SpecialOfferOrder {
    private int orderId;
    private int specialOfferId;
    private String userEmail;
    private String pizzaName;
    private String size;
    private double totalPrice;
    private String orderDate;
    private String orderTime;

    // Constructor
    public SpecialOfferOrder(int orderId, int specialOfferId, String userEmail, String pizzaName, String size, double totalPrice, String orderDate, String orderTime) {
        this.orderId = orderId;
        this.specialOfferId = specialOfferId;
        this.userEmail = userEmail;
        this.pizzaName = pizzaName;
        this.size = size;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(int specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    // Optional: toString method for easier debugging
    @Override
    public String toString() {
        return "SpecialOfferOrder{" +
                "orderId=" + orderId +
                ", specialOfferId=" + specialOfferId +
                ", userEmail='" + userEmail + '\'' +
                ", pizzaName='" + pizzaName + '\'' +
                ", size='" + size + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
