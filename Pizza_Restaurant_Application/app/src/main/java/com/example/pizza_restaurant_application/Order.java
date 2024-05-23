package com.example.pizza_restaurant_application;

public class Order {
    private int orderId;
    private int pizzaId;
    private String pizzaName;
    private String size;
    private int quantity;
    private double totalPrice;
    private String date;
    private String time;


    public Order(int orderId, int pizzaId, String pizzaName, String size, int quantity, double totalPrice, String date, String time) {
        this.orderId = orderId;
        this.pizzaId = pizzaId;
        this.pizzaName = pizzaName;
        this.size = size;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
        this.time = time;
    }

    // Getters and setters for all fields
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getPizzaId() { return pizzaId; }
    public void setPizzaId(int pizzaId) { this.pizzaId = pizzaId; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getPizzaName() { return pizzaName; }
    public void setPizzaName(String pizzaName) { this.pizzaName = pizzaName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public String getDate() { return date; }
    public void setDate(String size) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String size) { this.time = time; }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", pizzaId=" + pizzaId +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}

