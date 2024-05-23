package com.example.pizza_restaurant_application;

public class OrderWithCustomerName extends Order {
    private String firstName;
    private String lastName;

    public OrderWithCustomerName(int orderId, int pizzaId, String pizzaName, String size, int quantity, double totalPrice, String date, String time, String firstName, String lastName) {
        super(orderId, pizzaId, pizzaName, size, quantity, totalPrice, date, time);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Method to get the full customer name
    public String getCustomerName() {
        return firstName + " " + lastName;
    }

    // Override toString() method
    @Override
    public String toString() {
        return ", Customer Name: " + getCustomerName()+
                "Order ID: " + getOrderId() +
                ", Pizza ID: " + getPizzaId() +
                ", Pizza Name: " + getPizzaName() +
                ", Size: " + getSize() +
                ", Quantity: " + getQuantity() +
                ", Total Price: " + getTotalPrice() +
                ", Date: " + getDate() +
                ", Time: " + getTime();
    }
}
