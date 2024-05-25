package com.example.pizza_restaurant_application.DataAPI;

import com.example.pizza_restaurant_application.Customer.Pizza;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class PizzaJsonParser {
    public static List<Pizza> parsePizzaData(String json) {
        List<Pizza> pizzas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject pizzaObject = jsonArray.getJSONObject(i);
                Pizza pizza = new Pizza();
                pizza.setName(pizzaObject.getString("name"));
                pizza.setCategory(pizzaObject.getString("category"));
                JSONObject sizesObject = pizzaObject.getJSONObject("sizes");
                pizza.setSmallPrice(sizesObject.getDouble("Small"));
                pizza.setMediumPrice(sizesObject.getDouble("Medium"));
                pizza.setLargePrice(sizesObject.getDouble("Large"));
                pizza.setDescription(pizzaObject.getString("description"));
                pizzas.add(pizza);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pizzas;
    }
}
