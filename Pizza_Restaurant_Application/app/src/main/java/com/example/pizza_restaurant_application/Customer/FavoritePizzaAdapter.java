package com.example.pizza_restaurant_application.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza_restaurant_application.R;

import java.util.List;

public class FavoritePizzaAdapter extends RecyclerView.Adapter<FavoritePizzaAdapter.FavoritePizzaViewHolder> {
    private List<Pizza> favoritePizzaList;
    private List<Pizza> originalList; // To keep track of the original list
    private OnFavoritePizzaClickListener listener;
    private List<Pizza> filteredList; // New filtered list


    public interface OnFavoritePizzaClickListener {
        void onFavoritePizzaClick(Pizza pizza);
    }

    public FavoritePizzaAdapter(List<Pizza> favoritePizzaList, OnFavoritePizzaClickListener listener) {
        this.favoritePizzaList = favoritePizzaList;
        this.originalList = favoritePizzaList; // Initialize the original list
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoritePizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_pizza, parent, false);
        return new FavoritePizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritePizzaViewHolder holder, int position) {
        Pizza pizza = favoritePizzaList.get(position);
        holder.bind(pizza);
        holder.itemView.setOnClickListener(v -> listener.onFavoritePizzaClick(pizza));
    }

    @Override
    public int getItemCount() {
        return favoritePizzaList.size();
    }

    // Method to update the list with a new filtered list
    public void updateList(List<Pizza> filteredList) {
        favoritePizzaList = filteredList;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    // Method to reset the list to its original state
    public void resetList() {
        favoritePizzaList = originalList;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    class FavoritePizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public FavoritePizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void bind(Pizza pizza) {
            nameTextView.setText(pizza.getName());
            // Set image resource based on pizza's name
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
                // Add other cases as needed
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
