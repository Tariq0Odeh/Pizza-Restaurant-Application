package com.example.pizza_restaurant_application.Admin;

import com.example.pizza_restaurant_application.SpecialOffers.SpecialOfferOrder;

public class SpecialOrderWithCustomerName extends SpecialOfferOrder {
    private String firstName;
    private String lastName;

    // Constructor
    public SpecialOrderWithCustomerName(int orderId, int specialOfferId, String userEmail, String pizzaName, String size, double totalPrice, String orderDate, String orderTime, String firstName, String lastName) {
        super(orderId, specialOfferId, userEmail, pizzaName, size, totalPrice, orderDate, orderTime);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters for first name and last name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerName() {
        return firstName + " " + lastName;
    }

    // Optional: override the toString method to include the new fields
    @Override
    public String toString() {
        return "SpecialOrderWithCustomerName{" +
                "orderId=" + getOrderId() +
                ", specialOfferId=" + getSpecialOfferId() +
                ", userEmail='" + getUserEmail() + '\'' +
                ", pizzaName='" + getPizzaName() + '\'' +
                ", size='" + getSize() + '\'' +
                ", totalPrice=" + getTotalPrice() +
                ", orderDate='" + getOrderDate() + '\'' +
                ", orderTime='" + getOrderTime() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
