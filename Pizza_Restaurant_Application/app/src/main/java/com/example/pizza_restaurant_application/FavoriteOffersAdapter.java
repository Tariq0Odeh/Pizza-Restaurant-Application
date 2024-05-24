package com.example.pizza_restaurant_application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoriteOffersAdapter extends RecyclerView.Adapter<FavoriteOffersAdapter.FavoritePizzaViewHolder> {
    private List<SpecialOffer> favoritePizzaList;
    private List<SpecialOffer> originalList; // To keep track of the original list
    private OnFavoritePizzaClickListener listener;
    private List<SpecialOffer> filteredList; // New filtered list


    public interface OnFavoritePizzaClickListener {
        void onFavoritePizzaClick(SpecialOffer pizza);
    }

    public FavoriteOffersAdapter(List<SpecialOffer> favoritePizzaList, OnFavoritePizzaClickListener listener) {
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
        SpecialOffer pizza = favoritePizzaList.get(position);
        holder.bind(pizza);
        holder.itemView.setOnClickListener(v -> listener.onFavoritePizzaClick(pizza));
    }

    @Override
    public int getItemCount() {
        return favoritePizzaList.size();
    }

    // Method to update the list with a new filtered list
    public void updateListOffers(List<SpecialOffer> filteredList) {
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

        public void bind(SpecialOffer pizza) {
            nameTextView.setText(pizza.getPizzaName());
            // Set image resource based on pizza's name
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
                // Add other cases as needed
                default:
                    imageView.setImageResource(R.drawable.pizza_icon);
                    break;
            }
        }
    }

    public void removeSpecialOffer(SpecialOffer specialOffer) {
        favoritePizzaList.remove(specialOffer);
        notifyDataSetChanged(); // Notify adapter of dataset change
    }

    // Method to filter the pizza list
    public void filterList(List<SpecialOffer> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged(); // Notify adapter that the dataset has changed
    }
}
