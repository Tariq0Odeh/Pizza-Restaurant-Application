package com.example.pizza_restaurant_application.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import com.example.pizza_restaurant_application.R;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<Pizza> pizzaList;
    private List<Pizza> filteredList; // New filtered list
    private OnPizzaClickListener listener;

    public interface OnPizzaClickListener {
        void onPizzaClick(Pizza pizza);
    }

    public PizzaAdapter(List<Pizza> pizzaList, OnPizzaClickListener listener) {
        this.pizzaList = pizzaList;
        this.filteredList = new ArrayList<>(pizzaList); // Initialize filteredList with all pizzas
        this.listener = listener;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = filteredList.get(position); // Use filtered list instead of original list
        holder.bind(pizza);
        holder.itemView.setOnClickListener(v -> listener.onPizzaClick(pizza));
    }

    @Override
    public int getItemCount() {
        return filteredList.size(); // Return size of filtered list
    }

    class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(Pizza pizza) {
            nameTextView.setText(pizza.getName());
            // Set image resource based on pizza's name
            // Note: You may consider using pizza.getImageResourceId() if you have image resource IDs in the Pizza model
            switch (pizza.getName()) {
                case "Margherita Pizza":
                    imageView.setImageResource(R.drawable.margarita);
                    break;
                case "Neapolitan Pizza":
                    imageView.setImageResource(R.drawable.neapolitan);
                    break;
                case "Hawaiian Pizza":
                    imageView.setImageResource(R.drawable.hawaiian);
                    break;
                case "Pepperoni Pizza":
                    imageView.setImageResource(R.drawable.pepperoni);
                    break;
                case "New York Style Pizza":
                    imageView.setImageResource(R.drawable.new_york_style);
                    break;
                case "Calzone":
                    imageView.setImageResource(R.drawable.calzone);
                    break;
                case "Tandoori Chicken Pizza":
                    imageView.setImageResource(R.drawable.tandoori_chicken);
                    break;
                case "BBQ Chicken Pizza":
                    imageView.setImageResource(R.drawable.bbq_chicken);
                    break;
                case "Seafood Pizza":
                    imageView.setImageResource(R.drawable.seafood);
                    break;
                case "Vegetarian Pizza":
                    imageView.setImageResource(R.drawable.vegetarian);
                    break;
                case "Buffalo Chicken Pizza":
                    imageView.setImageResource(R.drawable.buffalo_chicken);
                    break;
                case "Mushroom Truffle Pizza":
                    imageView.setImageResource(R.drawable.mushroom_truffle);
                    break;
                case "Pesto Chicken Pizza":
                    imageView.setImageResource(R.drawable.pesto_chicken);
                    break;
                default:
                    imageView.setImageResource(R.drawable.pizza_icon);
                    break;
            }
        }
    }

    // Method to filter the pizza list
    public void filterList(List<Pizza> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged(); // Notify adapter that the dataset has changed
    }
}
