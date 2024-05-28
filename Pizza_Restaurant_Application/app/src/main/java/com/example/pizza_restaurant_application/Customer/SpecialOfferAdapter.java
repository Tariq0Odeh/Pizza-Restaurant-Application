package com.example.pizza_restaurant_application.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pizza_restaurant_application.DataAPI.SpecialOffer;
import com.example.pizza_restaurant_application.R;

import java.util.ArrayList;
import java.util.List;


public class SpecialOfferAdapter extends RecyclerView.Adapter<SpecialOfferAdapter.PizzaViewHolder> {
    private List<SpecialOffer> pizzaList;
    private List<SpecialOffer> filteredList;
    private OnPizzaClickListener listener;

    // Interface for handling click events on special offers
    public interface OnPizzaClickListener {
        void onPizzaClick(SpecialOffer pizza);
    }

    // Constructor to initialize the adapter with the list of special offers and click listener
    public SpecialOfferAdapter(List<SpecialOffer> pizzaList, OnPizzaClickListener listener) {
        this.pizzaList = pizzaList;
        this.filteredList = new ArrayList<>(pizzaList);
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
        SpecialOffer pizza = filteredList.get(position);
        holder.bind(pizza);
        holder.itemView.setOnClickListener(v -> listener.onPizzaClick(pizza));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        // ViewHolder class for the RecyclerView
        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        // Method to bind data to the ViewHolder
        public void bind(SpecialOffer pizza) {
            nameTextView.setText(pizza.getPizzaName());
            switch (pizza.getPizzaName()) {
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
    public void filterList(List<SpecialOffer> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }
}
