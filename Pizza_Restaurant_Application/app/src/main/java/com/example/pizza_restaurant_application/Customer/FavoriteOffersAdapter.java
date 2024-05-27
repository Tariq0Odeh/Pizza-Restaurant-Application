package com.example.pizza_restaurant_application.Customer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pizza_restaurant_application.R;
import com.example.pizza_restaurant_application.DataAPI.SpecialOffer;
import java.util.List;

public class FavoriteOffersAdapter extends RecyclerView.Adapter<FavoriteOffersAdapter.FavoritePizzaViewHolder> {
    private List<SpecialOffer> favoritePizzaList;
    private List<SpecialOffer> originalList;
    private OnFavoritePizzaClickListener listener;
    private List<SpecialOffer> filteredList;


    public interface OnFavoritePizzaClickListener {
        void onFavoritePizzaClick(SpecialOffer pizza);
    }

    // Constructor to initialize the adapter with the list of favorite pizzas and click listener
    public FavoriteOffersAdapter(List<SpecialOffer> favoritePizzaList, OnFavoritePizzaClickListener listener) {
        this.favoritePizzaList = favoritePizzaList;
        this.originalList = favoritePizzaList;
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
        notifyDataSetChanged();
    }


    // Method to reset the list to its original state
    public void resetList() {
        favoritePizzaList = originalList;
        notifyDataSetChanged();
    }

    // ViewHolder class for the RecyclerView
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

    // Method to remove a special offer from the list and notify the adapter
    public void removeSpecialOffer(SpecialOffer specialOffer) {
        favoritePizzaList.remove(specialOffer);
        notifyDataSetChanged();
    }

    // Method to filter the pizza list
    public void filterList(List<SpecialOffer> filteredList) {
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }
}
