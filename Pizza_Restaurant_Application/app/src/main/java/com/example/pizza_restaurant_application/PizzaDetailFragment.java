package com.example.pizza_restaurant_application;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class PizzaDetailFragment extends Fragment {
    private static final String ARG_PIZZA = "pizza";
    private Pizza pizza;
    private boolean isFavorite = false;
    private DataBaseHelper dbHelper;

    public static PizzaDetailFragment newInstance(Pizza pizza) {
        PizzaDetailFragment fragment = new PizzaDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PIZZA, pizza);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_detail, container, false);

        dbHelper = new DataBaseHelper(getContext());

        if (getArguments() != null) {
            pizza = (Pizza) getArguments().getSerializable(ARG_PIZZA);
        }

        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView categoryTextView = view.findViewById(R.id.categoryTextView);
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        TextView smallPriceTextView = view.findViewById(R.id.smallPriceTextView);
        TextView mediumPriceTextView = view.findViewById(R.id.mediumPriceTextView);
        TextView largePriceTextView = view.findViewById(R.id.largePriceTextView);
        ImageView imageView = view.findViewById(R.id.imageView);
        Button addToFavoritesButton = view.findViewById(R.id.addToFavoritesButton);

        if (pizza != null) {
            nameTextView.setText(pizza.getName());
            categoryTextView.setText(pizza.getCategory());
            descriptionTextView.setText(pizza.getDescription());
            smallPriceTextView.setText(String.format("Small: $%.2f", pizza.getSmallPrice()));
            mediumPriceTextView.setText(String.format("Medium: $%.2f", pizza.getMediumPrice()));
            largePriceTextView.setText(String.format("Large: $%.2f", pizza.getLargePrice()));

            // Load image based on pizza name
            loadImage(pizza.getName(), imageView);

            // Check if pizza is already in favorites
            isFavorite = checkIfPizzaIsFavorite();

            addToFavoritesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavorite) {
                        removePizzaFromFavorites();
                        showToast("Removed from favorites");
                    } else {
                        addPizzaToFavorites();
                        showToast("Added to favorites");
                    }
                    isFavorite = !isFavorite;
                    updateFavoritesButton();
                }
            });
        }

        return view;
    }

    private boolean checkIfPizzaIsFavorite() {
        List<Pizza> favoritePizzas = dbHelper.getFavoritePizzas();
        for (Pizza favPizza : favoritePizzas) {
            if (favPizza.getName().equals(pizza.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addPizzaToFavorites() {
        List<Pizza> favoritePizzas = dbHelper.getFavoritePizzas();
        if (!favoritePizzas.contains(pizza)) {
            boolean result = dbHelper.addFavoritePizza(dbHelper.getPizzaIdByName(pizza.getName()));
            if (!result) {
                showToast("Failed to add to favorites");
            }
        }
    }

    private void removePizzaFromFavorites() {
        List<Pizza> favoritePizzas = dbHelper.getFavoritePizzas();
        if (favoritePizzas.contains(pizza)) {
            boolean result = dbHelper.removeFavoritePizza(dbHelper.getPizzaIdByName(pizza.getName()));
            if (!result) {
                showToast("Failed to remove from favorites");
            }
        }
    }

    private void updateFavoritesButton() {
        Button addToFavoritesButton = getView().findViewById(R.id.addToFavoritesButton);
        if (isFavorite) {
            addToFavoritesButton.setBackgroundResource(R.drawable.heart_filled);
        } else {
            addToFavoritesButton.setBackgroundResource(R.drawable.heart_shape);
        }
    }

    private void showToast(String message) {
        Context context = getContext();
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImage(String pizzaName, ImageView imageView) {
        // Load image based on pizza name
        switch (pizzaName) {
            case "Margherita Pizza":
                imageView.setImageResource(R.drawable.margarita);
                break;
            // Add cases for other pizza names and their respective images
            default:
                imageView.setImageResource(R.drawable.pizza_icon);
                break;
        }
    }
}
