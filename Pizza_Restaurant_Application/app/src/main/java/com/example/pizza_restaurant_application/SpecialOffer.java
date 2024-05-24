package com.example.pizza_restaurant_application;

import java.io.Serializable;

public class SpecialOffer implements Serializable {
    private int specialOfferId;
    private String pizzaName;
    private String pizzaCategory;
    private String pizzaDescription;
    private String size;
    private double price;
    private String period;

    public SpecialOffer(int specialOfferId, String pizzaName, String pizzaCategory, String pizzaDescription, String size, double price, String period) {
        this.specialOfferId = specialOfferId;
        this.pizzaName = pizzaName;
        this.pizzaCategory = pizzaCategory;
        this.pizzaDescription = pizzaDescription;
        this.size = size;
        this.price = price;
        this.period = period;
    }

    public int getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(int specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public String getPizzaCategory() {
        return pizzaCategory;
    }

    public void setPizzaCategory(String pizzaCategory) {
        this.pizzaCategory = pizzaCategory;
    }

    public String getPizzaDescription() {
        return pizzaDescription;
    }

    public void setPizzaDescription(String pizzaDescription) {
        this.pizzaDescription = pizzaDescription;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
